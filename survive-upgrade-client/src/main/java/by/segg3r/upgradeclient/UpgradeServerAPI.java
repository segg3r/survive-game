package by.segg3r.upgradeclient;

import by.segg3r.Application;
import by.segg3r.exceptions.APIException;
import by.segg3r.http.entities.UpgradeInfo;

public interface UpgradeServerAPI {

	UpgradeInfo getApplicationUpgradeInfo(Application application, String upgradeVersion) throws APIException;
	
	byte[] getFileContent(String upgradeVersion, String fileRelativePathFromRoot) throws APIException;

}
