package by.segg3r.upgradeclient;

import by.segg3r.exceptions.APIException;
import by.segg3r.http.entities.UpgradeInfo;

public interface UpgradeServerAPI {

	UpgradeInfo getUpgradeClientUpgradeInfo(String upgradeClientVersion) throws APIException;

	UpgradeInfo getClientUpgradeInfo(String clientVersion) throws APIException;

	byte[] getFileContent(String upgradeVersion, String fileRelativePathFromRoot) throws APIException;

}
