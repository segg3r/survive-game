package by.segg3r.upgradeclient.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import by.segg3r.upgradeclient.UpgradeClient;
import by.segg3r.upgradeclient.UpgradeRunner;

@Component
public class UpgradeRunnerImpl implements UpgradeRunner {

	@Autowired
	private UpgradeClient upgradeClient;
	
	@Override
	public int runUpgrade(String rootPath) {
		try {
			boolean clientUpgradePerformed = upgradeClient.executeUpgrade(rootPath);
			return clientUpgradePerformed ? 0 : 2;
		} catch (Exception e) {
			return 1;
		}
	}

}
