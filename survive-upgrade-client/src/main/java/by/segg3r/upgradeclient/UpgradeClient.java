package by.segg3r.upgradeclient;

import by.segg3r.Application;
import by.segg3r.exceptions.UpgradeException;
import by.segg3r.http.entities.UpgradeFileInfo;
import by.segg3r.http.entities.UpgradeInfo;

public interface UpgradeClient {

	UpgradeResult executeUpgrade(String rootPath) throws UpgradeException;

	UpgradeResult upgradeApplication(String rootPath, Application application)
			throws UpgradeException;

	void upgradeFile(String rootPath, Application application,
			UpgradeInfo upgradeInfo, UpgradeFileInfo fileInfo)
			throws UpgradeException;

	void updateFile(String rootPath, Application application,
			UpgradeInfo upgradeInfo, UpgradeFileInfo fileInfo)
			throws UpgradeException;
	
	void removeFile(String rootPath, Application application,
			UpgradeInfo upgradeInfo, UpgradeFileInfo fileInfo)
			throws UpgradeException;

}