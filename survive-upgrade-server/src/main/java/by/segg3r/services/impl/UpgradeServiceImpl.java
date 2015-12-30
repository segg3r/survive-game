package by.segg3r.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import by.segg3r.ApplicationVersion;
import by.segg3r.dao.UpgradeDAO;
import by.segg3r.exceptions.UpgradeException;
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
	public UpgradeInfo getUpgradeInfo(ApplicationVersion applicationVersion) throws UpgradeException {
		return upgradeCache.getUpgradeInfo(applicationVersion);
	}

	@Override
	public byte[] getFileContent(String version, String path)
			throws UpgradeException {
		return upgradeDAO.getFileContent(version, path);
	}
	
}
