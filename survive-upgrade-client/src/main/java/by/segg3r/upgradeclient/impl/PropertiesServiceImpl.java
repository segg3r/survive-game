package by.segg3r.upgradeclient.impl;

import java.io.IOException;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import by.segg3r.constants.FileSystem;
import by.segg3r.upgradeclient.PropertiesDAO;
import by.segg3r.upgradeclient.PropertiesService;

@Component
public class PropertiesServiceImpl implements PropertiesService {

	private final static String VERSION_PROPERTY = "version";
	private final static String DEFAULT_VERSION = "0";

	private final static String RESOURCES_FOLDER = "resources";
	private final static String RELATIVE_CLIENT_PROPERTIES_PATH = RESOURCES_FOLDER
			+ FileSystem.FILE_SPLITTER + "client.properties";
	private final static String RELATIVE_UPGRADE_CLIENT_PROPERTIES_PATH = RESOURCES_FOLDER
			+ FileSystem.FILE_SPLITTER + "upgrade-client.properties";

	@Autowired
	private PropertiesDAO propertiesDAO;

	@Override
	public String getUpgradeClientVersion(String rootPath) throws IOException {
		String upgradeClientPropertiesFilePath = FileSystem
				.getUpgradeClientPath(rootPath)
				+ FileSystem.FILE_SPLITTER
				+ RELATIVE_UPGRADE_CLIENT_PROPERTIES_PATH;
		return getVersionFromPropertiesFile(upgradeClientPropertiesFilePath);
	}

	@Override
	public String getClientVersion(String rootPath) throws IOException {
		String clientPropertiesFilePath = FileSystem.getClientPath(rootPath)
				+ FileSystem.FILE_SPLITTER + RELATIVE_CLIENT_PROPERTIES_PATH;
		return getVersionFromPropertiesFile(clientPropertiesFilePath);
	}

	@Override
	public void updateClientVersion(String rootPath, String upgradeVersion)
			throws IOException {
		String clientPropertiesFilePath = FileSystem.getClientPath(rootPath)
				+ FileSystem.FILE_SPLITTER + RELATIVE_CLIENT_PROPERTIES_PATH;
		updateVersionProperty(upgradeVersion, clientPropertiesFilePath);
	}

	@Override
	public void updateUpgradeClientVersion(String rootPath,
			String upgradeVersion) throws IOException {
		String upgradeClientPropertiesFilePath = FileSystem
				.getUpgradeClientPath(rootPath)
				+ FileSystem.FILE_SPLITTER
				+ RELATIVE_UPGRADE_CLIENT_PROPERTIES_PATH;
		updateVersionProperty(upgradeVersion, upgradeClientPropertiesFilePath);
	}

	private void updateVersionProperty(String upgradeVersion,
			String upgradeClientPropertiesFilePath) throws IOException {
		Properties properties = propertiesDAO
				.getProperties(upgradeClientPropertiesFilePath);
		properties.setProperty(VERSION_PROPERTY, upgradeVersion);
		propertiesDAO.saveProperties(upgradeClientPropertiesFilePath,
				properties);
	}

	private String getVersionFromPropertiesFile(String filePath)
			throws IOException {
		return propertiesDAO.getProperty(filePath, VERSION_PROPERTY,
				DEFAULT_VERSION);
	}

}
