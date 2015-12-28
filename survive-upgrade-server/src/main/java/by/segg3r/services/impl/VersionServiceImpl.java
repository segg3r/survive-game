package by.segg3r.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import by.segg3r.dao.UpgradeDAO;
import by.segg3r.exceptions.UpgradeException;
import by.segg3r.services.VersionService;

@Component
public class VersionServiceImpl implements VersionService {

	private static final VersionComparator VERSION_COMPARATOR = new VersionComparator();
	
	@Autowired
	private UpgradeDAO upgradeDAO;
	
	@Override
	public String getNewerVersion(String version, String path) throws UpgradeException {
		List<String> availableVersions = upgradeDAO.getAvailableVersions(path);
		if (availableVersions.isEmpty()) {
			return version;
		}
		
		//increasing versions
		availableVersions.sort(VERSION_COMPARATOR);
		
		String latestAvailableVersion = availableVersions.get(availableVersions.size() - 1);
		if (VERSION_COMPARATOR.compare(version, latestAvailableVersion) >= 0) {
			return version;
		}
		
		for (String availableVersion : availableVersions) {
			if (VERSION_COMPARATOR.compare(availableVersion, version) > 0) {
				return availableVersion;
			}
		}
		
		return version;
	}
	
}
