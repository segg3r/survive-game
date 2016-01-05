package by.segg3r.upgradeclient;

import java.io.IOException;
import java.util.Properties;

import by.segg3r.Application;

public interface PropertiesService {

	Properties getProperties(String rootPath, Application application) throws IOException;
	
	String getApplicationVersion(String rootPath, Application application)
			throws IOException;

	void updateApplicationVersion(String rootPath, Application application, String upgradeVersion)
			throws IOException;

	void updateProperties(String rootPath, Application application,
			Properties clientProperties) throws IOException;

}
