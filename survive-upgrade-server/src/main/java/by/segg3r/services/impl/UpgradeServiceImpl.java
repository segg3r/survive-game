package by.segg3r.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import by.segg3r.Application;
import by.segg3r.dao.UpgradeDAO;
import by.segg3r.exceptions.UpgradeException;
import by.segg3r.http.entities.FileInfo;
import by.segg3r.http.entities.UpgradeInfo;
import by.segg3r.services.UpgradeService;
import by.segg3r.services.VersionService;

@Component
public class UpgradeServiceImpl implements UpgradeService {

	@Autowired
	private UpgradeDAO upgradeDAO;
	@Autowired
	private VersionService versionService;
	
	@Override
	public UpgradeInfo getUpgradeInfo(String version, Application application) throws UpgradeException {
		String newerVersion = versionService.getNewerVersion(version, application);
		if (newerVersion.equals(version)) {
			return UpgradeInfo.noUpgradeRequired(application.getPath(), version);
		}
			
		List<FileInfo> fileInfos = upgradeDAO.getFileInfos(newerVersion, application);
		return UpgradeInfo.withFileInfos(application.getPath(), version, newerVersion, fileInfos);
	}

	@Override
	public byte[] getFileContent(String version, String path)
			throws UpgradeException {
		return upgradeDAO.getFileContent(version, path);
	}
	
}
