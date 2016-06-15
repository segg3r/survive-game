package by.segg3r.services.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import by.segg3r.Application;
import by.segg3r.ApplicationVersion;
import by.segg3r.dao.UpgradeDAO;
import by.segg3r.exceptions.UpgradeException;
import by.segg3r.git.GitRepository;
import by.segg3r.http.entities.FileInfo;
import by.segg3r.http.entities.UpgradeFileInfo;
import by.segg3r.http.entities.UpgradeInfo;
import by.segg3r.services.UpgradeCache;
import by.segg3r.services.VersionFileTreeService;

@Component
public class UpgradeCacheImpl implements UpgradeCache {

	private static final VersionComparator VERSION_COMPARATOR = new VersionComparator();

	@Autowired
	private GitRepository repository;
	@Autowired
	private UpgradeDAO upgradeDAO;
	@Autowired
	private VersionFileTreeService versionFileTreeService;

	private Map<ApplicationVersion, UpgradeInfo> upgradeCacheByClientVersion = 
			new HashMap<ApplicationVersion, UpgradeInfo>();
	private Map<ApplicationVersion, UpgradeInfo> upgradeCacheByUpgradeVersion =
			new HashMap<ApplicationVersion, UpgradeInfo>();

	@PostConstruct
	public void populateApplicationVersions() throws UpgradeException {
		try {
			repository.initialize();
			
			List<String> repositoryVersions = repository.getVersions();
			List<String> populatedVersions = upgradeDAO.getAvailableVersions();
			List<String> buildVersions = repositoryVersions.stream()
				.filter(repositoryVersion -> {
					return !populatedVersions.contains(repositoryVersion);
				})
				.collect(Collectors.toList());
			
			for (String buildVersion : buildVersions) {
				repository.buildVersion(buildVersion);
				
				for (Application application : Application.values()) {
					ApplicationVersion applicationVersion = ApplicationVersion
							.of(application, buildVersion);
					String sourceApplicationDirectory = repository
							.getDeployDirectory(application);
					upgradeDAO
							.populateApplicationVersion(applicationVersion,
									sourceApplicationDirectory);
				}
			}
		} catch (Exception e) {
			throw new UpgradeException("Could not populate application versions", e);
		} finally {
			repository.close();
		}
	}
	
	@PostConstruct
	public void initializeCache() throws UpgradeException {
		for (Application application : Application.values()) {
			List<ApplicationVersion> availableVersions = upgradeDAO
					.getAvailableApplicationVersions(application);
			if (availableVersions.isEmpty()) {
				UpgradeInfo zeroToZeroUpgradeInfo = UpgradeInfo
						.noUpgradeRequired(application.getPath(),
								ApplicationVersion.ZERO_VERSION);
				upgradeCacheByClientVersion.put(
						ApplicationVersion.zeroOf(application),
						zeroToZeroUpgradeInfo);
			}

			availableVersions.sort(VERSION_COMPARATOR);

			ApplicationVersion latestVersion = populateVersionUpgradesEntries(
					application, availableVersions);

			UpgradeInfo latest = UpgradeInfo.noUpgradeRequired(
					application.getPath(), latestVersion.getVersion());
			upgradeCacheByClientVersion.put(latestVersion, latest);
		}
	}

	/**
	 * 
	 * @param application
	 * @param availableVersions
	 * @return latest found application version
	 * @throws UpgradeException
	 */
	private ApplicationVersion populateVersionUpgradesEntries(
			Application application, List<ApplicationVersion> availableVersions)
			throws UpgradeException {
		Iterator<ApplicationVersion> versionIterator = availableVersions
				.iterator();
		ApplicationVersion previous = ApplicationVersion.zeroOf(application);
		List<FileInfo> previousFileInfos = UpgradeInfo.VERSION_ZERO_FILE_INFOS;
		while (versionIterator.hasNext()) {
			ApplicationVersion current = versionIterator.next();
			List<FileInfo> currentFileInfos = upgradeDAO.getFileInfos(current);

			List<UpgradeFileInfo> upgradeFileInfos = versionFileTreeService
					.compare(previousFileInfos, currentFileInfos);
			UpgradeInfo upgradeInfo = UpgradeInfo.withFileInfos(
					application.getPath(), previous.getVersion(),
					current.getVersion(), upgradeFileInfos);
			upgradeCacheByClientVersion.put(previous, upgradeInfo);
			upgradeCacheByUpgradeVersion.put(current, upgradeInfo);

			previous = current;
			previousFileInfos = currentFileInfos;
		}
		return previous;
	}

	@Override
	public UpgradeInfo getUpgradeInfoByClientVersion(
			ApplicationVersion applicationVersion) throws UpgradeException {
		UpgradeInfo result = upgradeCacheByClientVersion
				.get(applicationVersion);
		handleNullUpgradeInfo(applicationVersion, result);
		return result;
	}

	@Override
	public UpgradeInfo getUpgradeInfoByUpgradeVersion(
			ApplicationVersion applicationVersion) throws UpgradeException {
		UpgradeInfo result = upgradeCacheByUpgradeVersion
				.get(applicationVersion);
		handleNullUpgradeInfo(applicationVersion, result);
		return result;
	}

	private void handleNullUpgradeInfo(ApplicationVersion applicationVersion,
			UpgradeInfo result) throws UpgradeException {
		if (result == null) {
			throw new UpgradeException("Application "
					+ applicationVersion.getApplication().getPath()
					+ " does not have version "
					+ applicationVersion.getVersion());
		}
	}

}
