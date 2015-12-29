package by.segg3r.upgradeclient;

import by.segg3r.exceptions.UpgradeException;
import by.segg3r.http.entities.UpgradeInfo;

public interface UpgradeClient {

	boolean executeUpgrade(String rootPath) throws UpgradeException;

	void upgradeUpgradeClient(String rootPath, UpgradeInfo upgradeInfo) throws UpgradeException;

	void upgradeClient(String rootPath, UpgradeInfo clientUpgradeInfo) throws UpgradeException;
	
}
