package by.segg3r.services.impl;

import static org.testng.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import by.segg3r.Application;
import by.segg3r.dao.UpgradeDAO;
import by.segg3r.exceptions.UpgradeException;

public class VersionServiceTest {

	@Mock
	private UpgradeDAO upgradeDAO;
	@InjectMocks
	private VersionServiceImpl service;

	@BeforeClass
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}

	@AfterMethod
	public void resetMocks() {
		reset(upgradeDAO);
	}

	@Test(description = "should return same version if there are no updates available")
	public void testGetNewerVersionNoUpdates() throws UpgradeException {
		String version = "0.0.1";
		Application client = Application.CLIENT;

		List<String> availableVersions = Collections.emptyList();
		when(upgradeDAO.getAvailableVersions(eq(client))).thenReturn(availableVersions);

		String newerVersion = service.getNewerVersion(version, client);
		assertEquals(newerVersion, version);
	}

	@Test(description = "should return same version if it's the latest")
	public void testGetNewerVersionLatest() throws UpgradeException {
		String version = "1.0.5";
		Application client = Application.CLIENT;

		List<String> availableVersions = Arrays.asList("0.0.1", "0.0.2",
				"0.0.5", "0.0.6", "1.0.5");
		when(upgradeDAO.getAvailableVersions(eq(client))).thenReturn(availableVersions);

		String newerVersion = service.getNewerVersion(version, client);
		assertEquals(newerVersion, version);
	}

	@Test(description = "should return same version if it's higher than latest")
	public void testGetNewerVersionHigher() throws UpgradeException {
		String version = "1.6.5";
		Application client = Application.CLIENT;

		List<String> availableVersions = Arrays.asList("0.0.1", "0.0.2",
				"0.0.5", "0.0.6", "1.0.5");
		when(upgradeDAO.getAvailableVersions(eq(client))).thenReturn(availableVersions);

		String newerVersion = service.getNewerVersion(version, client);
		assertEquals(newerVersion, version);
	}

	@Test(description = "should return newer version when there is one")
	public void testGetNewerVersionPositive() throws UpgradeException {
		Application client = Application.CLIENT;
		List<String> availableVersions = Arrays.asList("2.0.1", "1.0.5", "0.0.1", "0.0.2.3",
				"0.0.5", "0.0.6", "1.4.5", "1.4.7");
		when(upgradeDAO.getAvailableVersions(eq(client))).thenReturn(availableVersions);

		assertEquals(service.getNewerVersion("1", client), "1.0.5");
		assertEquals(service.getNewerVersion("0.0", client), "0.0.1");
		assertEquals(service.getNewerVersion("0.0.0", client), "0.0.1");
		assertEquals(service.getNewerVersion("0.0.2", client), "0.0.2.3");
		assertEquals(service.getNewerVersion("0.0.1.5", client), "0.0.2.3");
		assertEquals(service.getNewerVersion("0.1.2", client), "1.0.5");
		assertEquals(service.getNewerVersion("0.0.6", client), "1.0.5");
		assertEquals(service.getNewerVersion("1.0.5", client), "1.4.5");
		assertEquals(service.getNewerVersion("1.4.5", client), "1.4.7");
		assertEquals(service.getNewerVersion("1.4.6", client), "1.4.7");
		assertEquals(service.getNewerVersion("1.4.7", client), "2.0.1");
		assertEquals(service.getNewerVersion("1.6.0", client), "2.0.1");
	}

}
