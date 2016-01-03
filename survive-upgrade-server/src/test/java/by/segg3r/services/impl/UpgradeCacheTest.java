package by.segg3r.services.impl;

import static org.mockito.AdditionalMatchers.not;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

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
import by.segg3r.ApplicationVersion;
import by.segg3r.dao.UpgradeDAO;
import by.segg3r.exceptions.UpgradeException;
import by.segg3r.http.entities.FileInfo;
import by.segg3r.http.entities.FileUpgradeMode;
import by.segg3r.http.entities.UpgradeFileInfo;
import by.segg3r.http.entities.UpgradeInfo;
import by.segg3r.services.VersionFileTreeService;

public class UpgradeCacheTest {

	@Mock
	private UpgradeDAO upgradeDAO;
	@Mock
	private VersionFileTreeService versionFileTreeService;
	@InjectMocks
	private UpgradeCacheImpl cache;

	@BeforeClass
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}

	@AfterMethod
	public void resetMocks() {
		reset(upgradeDAO, versionFileTreeService);
	}

	@Test(description = "should correctly populate upgrade infos in cache")
	public void testCacheInitializing() throws UpgradeException {
		ApplicationVersion clientVersion1 = ApplicationVersion.of(
				Application.CLIENT, "0.0.1");
		ApplicationVersion clientVersion2 = ApplicationVersion.of(
				Application.CLIENT, "0.0.2");

		when(upgradeDAO.getAvailableVersions(eq(Application.CLIENT)))
				.thenReturn(Arrays.asList(clientVersion1, clientVersion2));
		when(upgradeDAO.getAvailableVersions(not(eq(Application.CLIENT))))
				.thenReturn(Collections.emptyList());

		FileInfo textFile1 = new FileInfo("text.txt", 1, "someDigest");
		FileInfo textFile2 = new FileInfo("text2.txt", 1, "someDigest");

		List<FileInfo> clientVersion1FileInfos = Arrays.asList(textFile1);
		when(upgradeDAO.getFileInfos(eq(clientVersion1))).thenReturn(
				clientVersion1FileInfos);
		List<FileInfo> clientVersion2FileInfos = Arrays.asList(textFile2);
		when(upgradeDAO.getFileInfos(eq(clientVersion2))).thenReturn(
				clientVersion2FileInfos);

		List<UpgradeFileInfo> zeroToOneUpgrade = Arrays
				.asList(new UpgradeFileInfo(textFile1, FileUpgradeMode.UPDATE));
		when(
				versionFileTreeService.compare(
						eq(UpgradeInfo.VERSION_ZERO_FILE_INFOS),
						eq(clientVersion1FileInfos))).thenReturn(
				zeroToOneUpgrade);
		List<UpgradeFileInfo> oneToTwoUpgrade = Arrays
				.asList(new UpgradeFileInfo(textFile2, FileUpgradeMode.UPDATE));
		when(
				versionFileTreeService.compare(eq(clientVersion1FileInfos),
						eq(clientVersion2FileInfos))).thenReturn(
				oneToTwoUpgrade);

		cache.initializeCache();

		UpgradeInfo clientZeroToOne = cache.getUpgradeInfo(ApplicationVersion
				.zeroOf(Application.CLIENT));
		assertTrue(clientZeroToOne.isUpgradeRequired());
		assertEquals(clientZeroToOne.getClientVersion(),
				ApplicationVersion.ZERO_VERSION);
		assertEquals(clientZeroToOne.getUpgradeVersion(),
				clientVersion1.getVersion());
		assertEquals(clientZeroToOne.getFileInfos(), zeroToOneUpgrade);

		UpgradeInfo clientOneToTwo = cache.getUpgradeInfo(clientVersion1);
		assertTrue(clientZeroToOne.isUpgradeRequired());
		assertEquals(clientOneToTwo.getClientVersion(), clientVersion1.getVersion());
		assertEquals(clientOneToTwo.getUpgradeVersion(), clientVersion2.getVersion());
		assertEquals(clientOneToTwo.getFileInfos(), oneToTwoUpgrade);
		
		UpgradeInfo clientTwoNoUpgrade = cache.getUpgradeInfo(clientVersion2);
		assertFalse(clientTwoNoUpgrade.isUpgradeRequired());
		assertEquals(clientTwoNoUpgrade.getClientVersion(), clientVersion2.getVersion());
		assertEquals(clientTwoNoUpgrade.getUpgradeVersion(), clientVersion2.getVersion());
		
		UpgradeInfo upgradeClientZeroNoUpgrade = cache.getUpgradeInfo(ApplicationVersion.zeroOf(Application.UPGRADE_CLIENT));
		assertFalse(upgradeClientZeroNoUpgrade.isUpgradeRequired());
		assertEquals(upgradeClientZeroNoUpgrade.getClientVersion(), ApplicationVersion.ZERO_VERSION);
		assertEquals(upgradeClientZeroNoUpgrade.getUpgradeVersion(), ApplicationVersion.ZERO_VERSION);
	}

}
