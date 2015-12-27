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
	public UpgradeInfo getUpgradeInfo(String version) throws UpgradeException {
		String newerVersion = versionService.getNewerVersion(version);
		if (newerVersion.equals(version)) {
			return UpgradeInfo.noUpgradeRequired(version);
		}
			
		List<FileInfo> fileInfos = upgradeDAO.getFileInfos(newerVersion);
		return UpgradeInfo.withFileInfos(version, newerVersion, fileInfos);
	}
	
}
