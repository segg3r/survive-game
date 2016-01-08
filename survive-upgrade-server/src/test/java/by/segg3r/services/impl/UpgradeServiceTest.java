package by.segg3r.services.impl;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.Arrays;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import by.segg3r.Application;
import by.segg3r.ApplicationVersion;
import by.segg3r.dao.UpgradeDAO;
import by.segg3r.exceptions.UpgradeException;
import by.segg3r.http.entities.FileUpgradeMode;
import by.segg3r.http.entities.UpgradeFileInfo;
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
		ApplicationVersion applicationVersion = ApplicationVersion.of(
				Application.CLIENT, "0.0.1");
		UpgradeInfo upgradeInfo = mock(UpgradeInfo.class);

		when(upgradeCache.getUpgradeInfoByClientVersion(applicationVersion))
				.thenReturn(upgradeInfo);

		AssertJUnit.assertEquals(service.getUpgradeInfo(applicationVersion),
				upgradeInfo);
	}

	@Test(description = "should get file content")
	public void testGetFileContent() throws UpgradeException {
		ApplicationVersion version = ApplicationVersion.of(Application.CLIENT,
				"1.3.4");
		String filePath = "test/test.txt";

		byte[] fileData = new byte[] { 1, 2, 3 };
		when(upgradeDAO.getFileContent(eq(version), eq(filePath))).thenReturn(
				fileData);

		byte[] actualFileData = service.getFileContent(version, filePath);
		AssertJUnit.assertEquals(actualFileData, fileData);
	}

	@Test(description = "file exists should return true if file exists with add mode")
	public void testFileExistsAddMode() throws UpgradeException {
		ApplicationVersion version = ApplicationVersion.of(Application.CLIENT,
				"0.0.2");
		String path = "test.txt";

		UpgradeFileInfo fileInfo = new UpgradeFileInfo(path, 100, "aBc",
				FileUpgradeMode.ADD);
		UpgradeInfo upgradeInfo = UpgradeInfo.withFileInfos(
				Application.CLIENT.getPath(), "0.0.1", version.getVersion(),
				Arrays.asList(fileInfo));
		when(upgradeCache.getUpgradeInfoByUpgradeVersion(eq(version)))
				.thenReturn(upgradeInfo);

		assertTrue(service.fileExists(version, path));
	}

	@Test(description = "file exists should return true if file exists with update mode")
	public void testFileExistsUpdateMode() throws UpgradeException {
		ApplicationVersion version = ApplicationVersion.of(Application.CLIENT,
				"0.0.2");
		String path = "test.txt";

		UpgradeFileInfo fileInfo = new UpgradeFileInfo(path, 100, "aBc",
				FileUpgradeMode.UPDATE);
		UpgradeInfo upgradeInfo = UpgradeInfo.withFileInfos(
				Application.CLIENT.getPath(), "0.0.1", version.getVersion(),
				Arrays.asList(fileInfo));
		when(upgradeCache.getUpgradeInfoByUpgradeVersion(eq(version)))
				.thenReturn(upgradeInfo);

		assertTrue(service.fileExists(version, path));
	}

	@Test(description = "file exists should return false if file exists with remove mode")
	public void testFileExistsRemoveMode() throws UpgradeException {
		ApplicationVersion version = ApplicationVersion.of(Application.CLIENT,
				"0.0.2");
		String path = "test.txt";

		UpgradeFileInfo fileInfo = new UpgradeFileInfo(path, 100, "aBc",
				FileUpgradeMode.REMOVE);
		UpgradeInfo upgradeInfo = UpgradeInfo.withFileInfos(
				Application.CLIENT.getPath(), "0.0.1", version.getVersion(),
				Arrays.asList(fileInfo));
		when(upgradeCache.getUpgradeInfoByUpgradeVersion(eq(version)))
				.thenReturn(upgradeInfo);

		assertFalse(service.fileExists(version, path));
	}

	@Test(description = "file exists should return false if file does not exist in upgrade")
	public void testFileDoesNotExist() throws UpgradeException {
		ApplicationVersion version = ApplicationVersion.of(Application.CLIENT,
				"0.0.2");
		String path = "test.txt";

		UpgradeFileInfo fileInfo = new UpgradeFileInfo("anotherFile.txt", 100,
				"aBc", FileUpgradeMode.REMOVE);
		UpgradeInfo upgradeInfo = UpgradeInfo.withFileInfos(
				Application.CLIENT.getPath(), "0.0.1", version.getVersion(),
				Arrays.asList(fileInfo));
		when(upgradeCache.getUpgradeInfoByUpgradeVersion(eq(version)))
				.thenReturn(upgradeInfo);

		assertFalse(service.fileExists(version, path));
	}

	@Test(description = "file exists should return false if upgrade info does not exist")
	public void testFileUpgradeInfoDoesNotExist() throws UpgradeException {
		ApplicationVersion version = ApplicationVersion.of(Application.CLIENT,
				"0.0.2");
		String path = "test.txt";

		when(upgradeCache.getUpgradeInfoByUpgradeVersion(eq(version)))
				.thenThrow(new UpgradeException());

		assertFalse(service.fileExists(version, path));
	}

}
