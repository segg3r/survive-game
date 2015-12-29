package by.segg3r.upgradeclient;

import java.io.IOException;
import java.util.Properties;

public interface PropertiesDAO {

	String getProperty(String filePath, String propertyName, String defaultValue) throws IOException;

	Properties getProperties(String clientPropertiesFilePath) throws IOException;

	void saveProperties(String clientPropertiesFilePath, Properties properties) throws IOException;
	
}
