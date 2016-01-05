package by.segg3r.upgradeclient;

import java.io.IOException;
import java.util.Properties;

public interface PropertiesDAO {

	String getProperty(String filePath, String propertyName, String defaultValue) throws IOException;

	Properties getProperties(String filePath) throws IOException;

	void saveProperties(String filePath, Properties properties) throws IOException;
	
}
