package by.segg3r.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
	public UpgradeInfo getUpgradeInfo(String version, String path) throws UpgradeException {
		String newerVersion = versionService.getNewerVersion(version, path);
		if (newerVersion.equals(version)) {
			return UpgradeInfo.noUpgradeRequired(path, version);
		}
			
		List<FileInfo> fileInfos = upgradeDAO.getFileInfos(newerVersion, path);
		return UpgradeInfo.withFileInfos(path, version, newerVersion, fileInfos);
	}

	@Override
	public byte[] getFileContent(String version, String path)
			throws UpgradeException {
		return upgradeDAO.getFileContent(version, path);
	}
	
}
