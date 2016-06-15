package by.segg3r.services.impl;

import static org.mockito.AdditionalMatchers.not;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import by.segg3r.Application;
import by.segg3r.ApplicationVersion;
import by.segg3r.dao.UpgradeDAO;
import by.segg3r.exceptions.UpgradeException;
import by.segg3r.git.GitRepository;
import by.segg3r.http.entities.FileInfo;
import by.segg3r.http.entities.FileUpgradeMode;
import by.segg3r.http.entities.UpgradeFileInfo;
import by.segg3r.http.entities.UpgradeInfo;
import by.segg3r.services.VersionFileTreeService;

public class UpgradeCacheTest {

	@Mock
	private GitRepository repository;
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
		reset(upgradeDAO, versionFileTreeService, repository);
	}
	
	@Test(description = "should correctly populate application versions")
	public void testPopulateApplicationVersions() throws Exception {
		when(repository.getVersions())
			.thenReturn(Arrays.asList("0.0.1", "0.0.2"));
		when(upgradeDAO.getAvailableVersions())
			.thenReturn(Arrays.asList("0.0.1"));
		when(repository.getDeployDirectory(any(Application.class)))
			.then(new Answer<String>() {
				@Override
				public String answer(InvocationOnMock invocation)
						throws Throwable {
					Application application = invocation.getArgumentAt(0, Application.class);
					return "D:/deploy/" + application.getPath();
				}
			});
			
		
		InOrder order = inOrder(repository, upgradeDAO);
		cache.populateApplicationVersions();
		
		order.verify(repository).initialize();
		order.verify(repository).buildVersion(eq("0.0.2"));
		verify(upgradeDAO).populateApplicationVersion(
				eq(ApplicationVersion.of(Application.UPGRADE_CLIENT, "0.0.2")), eq("D:/deploy/upgrade-client"));
		verify(upgradeDAO).populateApplicationVersion(
				eq(ApplicationVersion.of(Application.CLIENT, "0.0.2")), eq("D:/deploy/client"));
		order.verify(repository).close();
		
		verify(repository, never()).buildVersion(eq("0.0.1"));
		verify(upgradeDAO, never()).populateApplicationVersion(
				eq(ApplicationVersion.of(Application.CLIENT, "0.0.1")), anyString());
		verify(upgradeDAO, never()).populateApplicationVersion(
				eq(ApplicationVersion.of(Application.UPGRADE_CLIENT, "0.0.1")), anyString());
	}

	@Test(description = "should correctly populate upgrade infos in cache")
	public void testCacheInitializing() throws UpgradeException {
		ApplicationVersion clientVersion1 = ApplicationVersion.of(
				Application.CLIENT, "0.0.1");
		ApplicationVersion clientVersion2 = ApplicationVersion.of(
				Application.CLIENT, "0.0.2");

		when(upgradeDAO.getAvailableApplicationVersions(eq(Application.CLIENT)))
				.thenReturn(Arrays.asList(clientVersion1, clientVersion2));
		when(upgradeDAO.getAvailableApplicationVersions(not(eq(Application.CLIENT))))
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

		UpgradeInfo clientZeroToOne = cache.getUpgradeInfoByClientVersion(ApplicationVersion
				.zeroOf(Application.CLIENT));
		assertTrue(clientZeroToOne.isUpgradeRequired());
		assertEquals(clientZeroToOne.getClientVersion(),
				ApplicationVersion.ZERO_VERSION);
		assertEquals(clientZeroToOne.getUpgradeVersion(),
				clientVersion1.getVersion());
		assertEquals(clientZeroToOne.getFileInfos(), zeroToOneUpgrade);

		UpgradeInfo clientOneToTwo = cache.getUpgradeInfoByClientVersion(clientVersion1);
		assertTrue(clientZeroToOne.isUpgradeRequired());
		assertEquals(clientOneToTwo.getClientVersion(), clientVersion1.getVersion());
		assertEquals(clientOneToTwo.getUpgradeVersion(), clientVersion2.getVersion());
		assertEquals(clientOneToTwo.getFileInfos(), oneToTwoUpgrade);
		
		UpgradeInfo clientTwoNoUpgrade = cache.getUpgradeInfoByClientVersion(clientVersion2);
		assertFalse(clientTwoNoUpgrade.isUpgradeRequired());
		assertEquals(clientTwoNoUpgrade.getClientVersion(), clientVersion2.getVersion());
		assertEquals(clientTwoNoUpgrade.getUpgradeVersion(), clientVersion2.getVersion());
		
		UpgradeInfo upgradeClientZeroNoUpgrade = cache.getUpgradeInfoByClientVersion(ApplicationVersion.zeroOf(Application.UPGRADE_CLIENT));
		assertFalse(upgradeClientZeroNoUpgrade.isUpgradeRequired());
		assertEquals(upgradeClientZeroNoUpgrade.getClientVersion(), ApplicationVersion.ZERO_VERSION);
		assertEquals(upgradeClientZeroNoUpgrade.getUpgradeVersion(), ApplicationVersion.ZERO_VERSION);
		
		assertEquals(cache.getUpgradeInfoByUpgradeVersion(clientVersion1), clientZeroToOne);
		assertEquals(cache.getUpgradeInfoByUpgradeVersion(clientVersion2), clientOneToTwo);
	}

}
