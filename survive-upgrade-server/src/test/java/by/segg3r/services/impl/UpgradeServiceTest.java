package by.segg3r.services.impl;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;
import static org.testng.Assert.assertEquals;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import by.segg3r.Application;
import by.segg3r.ApplicationVersion;
import by.segg3r.dao.UpgradeDAO;
import by.segg3r.exceptions.UpgradeException;
import by.segg3r.http.entities.UpgradeInfo;
import by.segg3r.services.UpgradeCache;

public class UpgradeServiceTest {

	@Mock
	private UpgradeCache upgradeCache;
	@Mock
	private UpgradeDAO upgradeDAO;
	@InjectMocks
	private UpgradeServiceImpl service;

	@BeforeClass
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}

	@AfterMethod
	public void resetMocks() {
		reset(upgradeCache, upgradeDAO);
	}

	@Test(description = "should get upgrade info from cache")
	public void testGetUpgradeInfoFromCache() throws UpgradeException {
		ApplicationVersion applicationVersion = ApplicationVersion.of(Application.CLIENT, "0.0.1");
		UpgradeInfo upgradeInfo = mock(UpgradeInfo.class);
		
		when(upgradeCache.getUpgradeInfo(applicationVersion)).thenReturn(upgradeInfo);
	
		assertEquals(service.getUpgradeInfo(applicationVersion), upgradeInfo);
	}
	
	@Test(description = "should get file content")
	public void testGetFileContent() throws UpgradeException {
		String version = "1.3.4";
		String filePath = "test/test.txt";

		byte[] fileData = new byte[] { 1, 2, 3 };
		when(upgradeDAO.getFileContent(eq(version), eq(filePath))).thenReturn(
				fileData);

		byte[] actualFileData = service.getFileContent(version, filePath);
		assertEquals(actualFileData, fileData);
	}

}
