package by.segg3r.services.impl;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.Arrays;
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
import by.segg3r.http.entities.FileInfo;
import by.segg3r.http.entities.UpgradeInfo;
import by.segg3r.services.VersionService;

public class UpgradeServiceTest {

	@Mock
	private UpgradeDAO upgradeDAO;
	@Mock
	private VersionService versionService;
	@InjectMocks
	private UpgradeServiceImpl service;

	@BeforeClass
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}

	@AfterMethod
	public void resetMocks() {
		reset(versionService, upgradeDAO);
	}

	@Test(description = "should return 'no upgrade required' if client has latest version")
	public void testGetUpgradeInfoNoUpgradeRequired() throws UpgradeException {
		String version = "0.0.1";
		Application client = Application.CLIENT;
		String latestVersion = version;
		when(versionService.getNewerVersion(eq(version), eq(client))).thenReturn(
				latestVersion);

		UpgradeInfo upgradeInfo = service.getUpgradeInfo(version, client);
		assertEquals(upgradeInfo.getClientVersion(), version);
		assertEquals(upgradeInfo.getUpgradeVersion(), version);
		assertFalse(upgradeInfo.isUpgradeRequired());
		assertTrue(upgradeInfo.getFileInfos().isEmpty());
		assertEquals(upgradeInfo.getPath(), client.getPath());
	}

	@Test(description = "should return list of file infos if client has outdated version")
	public void testGetUpgradeInfoUpgradeRequired() throws UpgradeException {
		String version = "0.0.1";
		String latestVersion = "0.0.2";
		Application client = Application.CLIENT;
		FileInfo fileInfo = new FileInfo("lib/spring.jar", 5000000L);
		List<FileInfo> fileInfos = Arrays.asList(fileInfo);

		when(versionService.getNewerVersion(eq(version), eq(client))).thenReturn(
				latestVersion);
		when(upgradeDAO.getFileInfos(eq(latestVersion), eq(client))).thenReturn(fileInfos);

		UpgradeInfo upgradeInfo = service.getUpgradeInfo(version, client);
		assertEquals(upgradeInfo.getClientVersion(), version);
		assertEquals(upgradeInfo.getUpgradeVersion(), latestVersion);
		assertTrue(upgradeInfo.isUpgradeRequired());
		assertEquals(fileInfos, upgradeInfo.getFileInfos());
		assertEquals(upgradeInfo.getPath(), client.getPath());
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
