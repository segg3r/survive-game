package by.segg3r.upgradeclient.upradecomponents;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import by.segg3r.Application;
import by.segg3r.exceptions.UpgradeException;
import by.segg3r.http.entities.UpgradeInfo;
import by.segg3r.upgradeclient.PropertiesService;
import by.segg3r.upgradeclient.UpgradeComponent;

@Component
public class ClientPropertiesUpgradeComponent implements UpgradeComponent {

	private static final Logger LOG = LogManager.getLogger(ClientPropertiesUpgradeComponent.class);
	
	@Autowired
	private PropertiesService propertiesService;

	private Properties clientProperties;

	@Override
	public void beforeApplicationUpgrade(String rootPath,
			Application application, UpgradeInfo upgradeInfo)
			throws UpgradeException {
		try {
			if (application == Application.CLIENT) {
				clientProperties = propertiesService.getProperties(rootPath,
						application);
			}
		} catch (IOException e) {
			clientProperties = null;
			LOG.warn("Have not found properties file", e);
		}
	}

	@Override
	public void afterApplicationUpgrade(String rootPath,
			Application application, UpgradeInfo upgradeInfo)
			throws UpgradeException {
		try {
			if (application == Application.CLIENT) {
				if (clientProperties == null) {
					LOG.warn("Skip updating properties");
					return;
				}
				
				Properties updatedProperties = propertiesService.getProperties(
						rootPath, application);
				for (Object key : clientProperties.keySet()) {
					String oldKey = (String) key;
					String oldProperty = clientProperties.getProperty(oldKey);
					updatedProperties.setProperty(oldKey, oldProperty);
				}

				propertiesService.updateProperties(rootPath, application,
						updatedProperties);
			}
		} catch (IOException e) {
			throw new UpgradeException("Error overriding client properties", e);
		}
	}

	public Properties getClientProperties() {
		return clientProperties;
	}

	public void setClientProperties(Properties clientProperties) {
		this.clientProperties = clientProperties;
	}

}
