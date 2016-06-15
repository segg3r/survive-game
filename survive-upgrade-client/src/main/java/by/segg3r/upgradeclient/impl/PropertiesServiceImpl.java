package by.segg3r.upgradeclient.impl;

import java.io.IOException;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import by.segg3r.Application;
import by.segg3r.ApplicationVersion;
import by.segg3r.FileSystem;
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
		Properties properties = getProperties(rootPath, application);
		properties.setProperty(VERSION_PROPERTY, upgradeVersion);
		updateProperties(rootPath, application, properties);
	}

	@Override
	public Properties getProperties(String rootPath, Application application) throws IOException {
		return propertiesDAO.getProperties(FileSystem
				.getApplicationPropertiesFilePath(rootPath, application));
	}

	@Override
	public void updateProperties(String rootPath, Application application,
			Properties properties) throws IOException {
		String applicationPropertiesFilePath = FileSystem
				.getApplicationPropertiesFilePath(rootPath, application);
		propertiesDAO.saveProperties(applicationPropertiesFilePath, properties);
	}

}
