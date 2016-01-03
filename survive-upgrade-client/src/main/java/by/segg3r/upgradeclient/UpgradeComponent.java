package by.segg3r.upgradeclient;

import by.segg3r.Application;
import by.segg3r.http.entities.UpgradeFileInfo;
import by.segg3r.http.entities.UpgradeInfo;

public interface UpgradeComponent {

	default void beforeApplicationUpgrade(Application application, UpgradeInfo upgradeInfo) {};
	default void afterApplicationUpgrade(Application application, UpgradeInfo upgradeInfo) {};
	default void beforeFile(Application application, UpgradeFileInfo upgradeFileInfo) {};
	default void afterFile(Application application, UpgradeFileInfo upgradeFileInfo) {};
	
}
