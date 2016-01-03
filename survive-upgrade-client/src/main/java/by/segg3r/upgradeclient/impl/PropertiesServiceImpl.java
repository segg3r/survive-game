package by.segg3r.upgradeclient.impl;

import java.io.IOException;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import by.segg3r.Application;
import by.segg3r.ApplicationVersion;
import by.segg3r.constants.FileSystem;
import by.segg3r.upgradeclient.PropertiesDAO;
import by.segg3r.upgradeclient.PropertiesService;

@Component
public class PropertiesServiceImpl implements PropertiesService {

	private final static String VERSION_PROPERTY = "version";

	@Autowired
	private PropertiesDAO propertiesDAO;

	@Override
	public String getApplicationVersion(String rootPath, Application application)
			throws IOException {
		return propertiesDAO.getProperty(FileSystem
				.getApplicationPropertiesFilePath(rootPath, application),
				VERSION_PROPERTY, ApplicationVersion.ZERO_VERSION);
	}

	@Override
	public void updateApplicationVersion(String rootPath,
			Application application, String upgradeVersion) throws IOException {
		String applicationPropertiesFilePath = FileSystem
				.getApplicationPropertiesFilePath(rootPath, application);

		Properties properties = propertiesDAO
				.getProperties(applicationPropertiesFilePath);
		properties.setProperty(VERSION_PROPERTY, upgradeVersion);
		propertiesDAO.saveProperties(applicationPropertiesFilePath, properties);
	}

}
