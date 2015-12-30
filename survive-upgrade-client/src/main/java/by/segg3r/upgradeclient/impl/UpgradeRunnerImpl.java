package by.segg3r.upgradeclient.impl;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import by.segg3r.upgradeclient.UpgradeClient;
import by.segg3r.upgradeclient.UpgradeResult;
import by.segg3r.upgradeclient.UpgradeRunner;

@Component
public class UpgradeRunnerImpl implements UpgradeRunner {

	private static final Logger LOG = LogManager.getLogger(UpgradeRunnerImpl.class);
	
	@Autowired
	private UpgradeClient upgradeClient;
	
	@Override
	public int runUpgrade(String rootPath) {
		try {
			UpgradeResult upgradeResult = upgradeClient.executeUpgrade(rootPath);
			LOG.info("Upgrade is successfull. Exit code is " + upgradeResult.getResultCode());
			return upgradeResult.getResultCode();
		} catch (Exception e) {
			LOG.error("Error running upgrade.", e);
			return UpgradeResult.UPGRADE_FAILED.getResultCode();
		}
	}

}
