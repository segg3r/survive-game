package by.segg3r.upgradeclient;

import java.io.IOException;

public interface PropertiesService {

	String getUpgradeClientVersion(String rootPath) throws IOException;

	String getClientVersion(String rootPath) throws IOException;

	void updateUpgradeClientVersion(String rootPath, String upgradeVersion)
			throws IOException;

	void updateClientVersion(String rootPath, String upgradeVersion)
			throws IOException;

}
