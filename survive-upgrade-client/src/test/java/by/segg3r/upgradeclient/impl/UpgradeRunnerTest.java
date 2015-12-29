package by.segg3r.upgradeclient.impl;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import by.segg3r.exceptions.UpgradeException;
import by.segg3r.upgradeclient.UpgradeClient;

public class UpgradeRunnerTest {

	@Mock
	private UpgradeClient upgradeClient;
	@InjectMocks
	private UpgradeRunnerImpl upgradeRunner;

	@BeforeClass
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}

	@AfterMethod
	public void resetMocks() {
		reset(upgradeClient);
	}

	@Test(description = "should return code '0' if client is upgraded")
	public void testRunUpgradeSuccessful() throws UpgradeException {
		String rootPath = "D:/survive-game";

		when(upgradeClient.executeUpgrade(eq(rootPath))).thenReturn(true);

		assertEquals(upgradeRunner.runUpgrade(rootPath), 0);
	}

	@Test(description = "should return code '2' if upgrader is upgraded")
	public void testRunUpgradeNotNeeded() throws UpgradeException {
		String rootPath = "D:/survive-game";

		when(upgradeClient.executeUpgrade(eq(rootPath))).thenReturn(false);

		assertEquals(upgradeRunner.runUpgrade(rootPath), 2);
	}

	@Test(description = "should return code '1' if failed the upgrade")
	public void testRunUpgradeFailed() throws UpgradeException {
		String rootPath = "D:/survive-game";

		when(upgradeClient.executeUpgrade(eq(rootPath))).thenThrow(
				new UpgradeException("Upgrade is failed"));

		assertEquals(upgradeRunner.runUpgrade(rootPath), 1);
	}

}
