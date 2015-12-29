package by.segg3r.upgradeclient.impl;

import org.springframework.beans.factory.annotation.Autowired;

import by.segg3r.constants.FileSystem;
import by.segg3r.exceptions.APIException;
import by.segg3r.http.entities.UpgradeInfo;
import by.segg3r.upgradeclient.UpgradeServerAPI;
import by.segg3r.upgradeclient.http.impl.JsonHttpClient;
import by.segg3r.upgradeclient.http.impl.SimpleHttpClient;

public class UpgradeServerAPIImpl implements UpgradeServerAPI {

	private static final String HTTP_SPLITTER = "/";

	@Autowired
	private SimpleHttpClient simpleHttpClient;
	@Autowired
	private JsonHttpClient jsonHttpClient;

	private String upgradeServerHost;
	private int upgradeServerPort;

	@Override
	public UpgradeInfo getUpgradeClientUpgradeInfo(String upgradeClientVersion)
			throws APIException {
		return getUpgradeInfo(upgradeClientVersion,
				FileSystem.UPGRADE_CLIENT_FOLDER);
	}

	@Override
	public UpgradeInfo getClientUpgradeInfo(String clientVersion)
			throws APIException {
		return getUpgradeInfo(clientVersion, FileSystem.CLIENT_FOLDER);
	}

	private UpgradeInfo getUpgradeInfo(String version, String folder)
			throws APIException {
		String requestPath = buildPath("upgrade" + HTTP_SPLITTER + version
				+ HTTP_SPLITTER + folder);
		return jsonHttpClient.get(requestPath, UpgradeInfo.class);
	}

	@Override
	public byte[] getFileContent(String upgradeVersion,
			String fileRelativePathFromRoot) throws APIException {
		String requestPath = buildPath("file" + HTTP_SPLITTER + upgradeVersion
				+ HTTP_SPLITTER + fileRelativePathFromRoot);
		return simpleHttpClient.get(requestPath);
	}

	private String buildPath(String relativeUrl) {
		return "http://" + upgradeServerHost + ":" + upgradeServerPort
				+ "/api/" + relativeUrl;
	}

	public void setUpgradeServerHost(String upgradeServerHost) {
		this.upgradeServerHost = upgradeServerHost;
	}

	public void setUpgradeServerPort(int upgradeServerPort) {
		this.upgradeServerPort = upgradeServerPort;
	}

}