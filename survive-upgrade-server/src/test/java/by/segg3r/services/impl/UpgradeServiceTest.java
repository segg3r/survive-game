package by.segg3r.services.impl;

import static org.testng.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

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
	public void testGetUpgradeInfoNegative() throws UpgradeException {
		String version = "0.0.1";
		String latestVersion = version;
		when(versionService.getNewerVersion(eq(version))).thenReturn(latestVersion);
		
		UpgradeInfo upgradeInfo = service.getUpgradeInfo(version);
		assertEquals(upgradeInfo.getClientVersion(), version);
		assertEquals(upgradeInfo.getUpgradeVersion(), version);
		assertFalse(upgradeInfo.isUpgradeRequired());
		assertTrue(upgradeInfo.getFileInfos().isEmpty());
	}
	
	@Test(description = "should return list of file infos if client has outdated version")
	public void testGetUpgradeInfoPositive() throws UpgradeException {
		String version = "0.0.1";
		String latestVersion = "0.0.2";
		FileInfo fileInfo = new FileInfo("lib/spring.jar", 5000000L);
		List<FileInfo> fileInfos = Arrays.asList(fileInfo);
		
		when(versionService.getNewerVersion(eq(version))).thenReturn(latestVersion);
		when(upgradeDAO.getFileInfos(eq(latestVersion))).thenReturn(fileInfos);
		
		UpgradeInfo upgradeInfo = service.getUpgradeInfo(version);
		assertEquals(upgradeInfo.getClientVersion(), version);
		assertEquals(upgradeInfo.getUpgradeVersion(), latestVersion);
		assertTrue(upgradeInfo.isUpgradeRequired());
		assertEquals(fileInfos, upgradeInfo.getFileInfos());
	}
	
}
