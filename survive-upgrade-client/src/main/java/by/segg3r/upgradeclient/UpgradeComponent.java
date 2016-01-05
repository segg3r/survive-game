package by.segg3r.upgradeclient;

import by.segg3r.Application;
import by.segg3r.exceptions.UpgradeException;
import by.segg3r.http.entities.UpgradeFileInfo;
import by.segg3r.http.entities.UpgradeInfo;

public interface UpgradeComponent {

	default void beforeApplicationUpgrade(String rootPath,
			Application application, UpgradeInfo upgradeInfo)
			throws UpgradeException {
	};

	default void afterApplicationUpgrade(String rootPath,
			Application application, UpgradeInfo upgradeInfo)
			throws UpgradeException {
	};

	default void beforeFile(String rootPath, Application application,
			UpgradeFileInfo upgradeFileInfo) throws UpgradeException {
	};

	default void afterFile(String rootPath, Application application,
			UpgradeFileInfo upgradeFileInfo) throws UpgradeException {
	};

}
