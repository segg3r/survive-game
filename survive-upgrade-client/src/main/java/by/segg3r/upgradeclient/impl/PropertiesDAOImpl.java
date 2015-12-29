package by.segg3r.upgradeclient.impl;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.stereotype.Component;

import by.segg3r.upgradeclient.PropertiesDAO;

@Component
public class PropertiesDAOImpl implements PropertiesDAO {

	@Override
	public String getProperty(String filePath, String propertyName,
			String defaultValue) throws IOException {
		try {
			Properties properties = getProperties(filePath);
			String versionProperty = properties.getProperty(propertyName);
			return versionProperty;
		} catch (IOException e) {
			return defaultValue;
		}
	}

	@Override
	public Properties getProperties(String filePath) throws IOException {
		Properties properties = new Properties();

		InputStream in = null;
		try {
			in = new FileInputStream(filePath);
			properties.load(in);
			return properties;
		} finally {
			if (in != null) {
				in.close();
			}
		}
	}

	@Override
	public void saveProperties(String filePath, Properties properties)
			throws IOException {
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(filePath);
			properties.store(out, null);
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

}
