package by.segg3r.upgradeclient;

import java.io.IOException;

import by.segg3r.Application;

public interface PropertiesService {

	String getApplicationVersion(String rootPath, Application application)
			throws IOException;

	void updateApplicationVersion(String rootPath, Application application, String upgradeVersion)
			throws IOException;

}
