package by.segg3r.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import by.segg3r.ApplicationVersion;
import by.segg3r.dao.UpgradeDAO;
import by.segg3r.exceptions.UpgradeException;
import by.segg3r.http.entities.FileUpgradeMode;
import by.segg3r.http.entities.UpgradeFileInfo;
import by.segg3r.http.entities.UpgradeInfo;
import by.segg3r.services.UpgradeCache;
import by.segg3r.services.UpgradeService;

@Component
public class UpgradeServiceImpl implements UpgradeService {

	@Autowired
	private UpgradeCache upgradeCache;
	@Autowired
	private UpgradeDAO upgradeDAO;

	@Override
	public UpgradeInfo getUpgradeInfo(ApplicationVersion applicationVersion)
			throws UpgradeException {
		return upgradeCache.getUpgradeInfoByClientVersion(applicationVersion);
	}

	@Override
	public byte[] getFileContent(ApplicationVersion applicationVersion,
			String path) throws UpgradeException {
		return upgradeDAO.getFileContent(applicationVersion, path);
	}

	@Override
	public boolean fileExists(ApplicationVersion applicationVersion, String path) {
		try {
			UpgradeInfo upgradeInfo = upgradeCache
					.getUpgradeInfoByUpgradeVersion(applicationVersion);

			List<UpgradeFileInfo> fileInfos = upgradeInfo.getFileInfos();
			for (UpgradeFileInfo fileInfo : fileInfos) {
				if (path.equals(fileInfo.getPath())
						&& FileUpgradeMode.REMOVE != fileInfo
								.getFileUpgradeMode()) {
					return true;
				}
			}
			return false;
		} catch (UpgradeException e) {
			return false;
		}
	}

}
