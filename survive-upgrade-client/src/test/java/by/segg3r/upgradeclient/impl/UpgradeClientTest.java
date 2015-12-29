package by.segg3r.upgradeclient.impl;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import by.segg3r.constants.FileSystem;
import by.segg3r.exceptions.APIException;
import by.segg3r.exceptions.UpgradeException;
import by.segg3r.http.entities.FileInfo;
import by.segg3r.http.entities.UpgradeInfo;
import by.segg3r.upgradeclient.FileSystemService;
import by.segg3r.upgradeclient.PropertiesService;
import by.segg3r.upgradeclient.UpgradeResult;
import by.segg3r.upgradeclient.UpgradeServerAPI;

public class UpgradeClientTest {

	@Mock
	private PropertiesService propertiesService;
	@Mock
	private UpgradeServerAPI upgradeServerAPI;
	@Mock
	private FileSystemService fileSystemService;
	@InjectMocks
	private UpgradeClientImpl client;

	@BeforeClass
	public void initMocks() {
		MockitoAnnotations.initMocks(this);

		client = spy(client);
	}

	@AfterMethod
	public void resetMocks() {
		reset(propertiesService, upgradeServerAPI, fileSystemService, client);
	}

	@Test(description = "executeUpgrade should return 'UPGRADER_UPGRADED' if upgrade-client upgrade was performed")
	public void testExecuteUpgradeUpgradeClientUpgradePerformed()
			throws UpgradeException, APIException, IOException {
		String upgradeClientVersion = "0.0.1";
		String upgradeClientUpgradeVersion = "0.0.2";
		String rootPath = "D:/survive-game";
		String path = "upgrade-client";
		UpgradeInfo upgradeInfo = UpgradeInfo.withFileInfos(path,
				upgradeClientVersion, upgradeClientUpgradeVersion,
				Collections.emptyList());

		when(propertiesService.getUpgradeClientVersion(eq(rootPath)))
				.thenReturn(upgradeClientVersion);
		when(
				upgradeServerAPI
						.getUpgradeClientUpgradeInfo(eq(upgradeClientVersion)))
				.thenReturn(upgradeInfo);
		doNothing().when(client).upgradeUpgradeClient(eq(rootPath),
				eq(upgradeInfo));

		UpgradeResult upgradeResult = client.executeUpgrade(rootPath);
		assertEquals(upgradeResult, UpgradeResult.UPGRADER_UPGRADED);
		verify(client, times(1)).upgradeUpgradeClient(eq(rootPath),
				eq(upgradeInfo));
	}

	@Test(description = "executeUpgrade should execute client upgrade and return 'CLIENT_UPGRADED'"
			+ "if upgread-client upgrade is not needed and client upgrade is needed")
	public void testExecuteUpgradeClientUpgradePerformed()
			throws UpgradeException, APIException, IOException {
		String rootPath = "D:/survive-game";

		String upgradeClientVersion = "0.0.2";
		String upgradeClientPath = "upgrade-client";
		UpgradeInfo upgradeClientUpgradeInfo = UpgradeInfo.noUpgradeRequired(
				upgradeClientPath, upgradeClientVersion);
		when(propertiesService.getUpgradeClientVersion(eq(rootPath)))
				.thenReturn(upgradeClientVersion);
		when(
				upgradeServerAPI
						.getUpgradeClientUpgradeInfo(eq(upgradeClientVersion)))
				.thenReturn(upgradeClientUpgradeInfo);

		String clientVersion = "0.0.1";
		String clientUpgradeVersion = "0.0.2";
		String clientPath = "client";
		UpgradeInfo clientUpgradeInfo = UpgradeInfo.withFileInfos(clientPath,
				clientVersion, clientUpgradeVersion, Collections.emptyList());
		when(propertiesService.getClientVersion(eq(rootPath))).thenReturn(
				clientVersion);
		when(upgradeServerAPI.getClientUpgradeInfo(eq(clientVersion)))
				.thenReturn(clientUpgradeInfo);

		doNothing().when(client).upgradeClient(eq(rootPath),
				eq(clientUpgradeInfo));
		doNothing().when(client).upgradeUpgradeClient(eq(rootPath),
				eq(upgradeClientUpgradeInfo));

		UpgradeResult upgradeResult = client.executeUpgrade(rootPath);
		assertEquals(upgradeResult, UpgradeResult.CLIENT_UPGRADED);
		verify(client, never()).upgradeUpgradeClient(eq(rootPath),
				any(UpgradeInfo.class));
		verify(client, times(1)).upgradeClient(eq(rootPath),
				eq(clientUpgradeInfo));
	}

	@Test(description = "should not execute any upgrades if not needed and return 'true'")
	public void testExecuteUpgradeNoUpgradesNeeded() throws APIException,
			UpgradeException, IOException {
		String rootPath = "D:/survive-game";

		String upgradeClientVersion = "0.0.2";
		String upgradeClientPath = "upgrade-client";
		UpgradeInfo upgradeClientUpgradeInfo = UpgradeInfo.noUpgradeRequired(
				upgradeClientPath, upgradeClientVersion);
		when(propertiesService.getUpgradeClientVersion(eq(rootPath)))
				.thenReturn(upgradeClientVersion);
		when(
				upgradeServerAPI
						.getUpgradeClientUpgradeInfo(eq(upgradeClientVersion)))
				.thenReturn(upgradeClientUpgradeInfo);

		String clientVersion = "0.0.2";
		String clientPath = "client";
		UpgradeInfo clientUpgradeInfo = UpgradeInfo.noUpgradeRequired(
				clientPath, clientVersion);
		when(propertiesService.getClientVersion(eq(rootPath))).thenReturn(
				clientVersion);
		when(upgradeServerAPI.getClientUpgradeInfo(eq(clientVersion)))
				.thenReturn(clientUpgradeInfo);

		doNothing().when(client).upgradeClient(eq(rootPath),
				eq(clientUpgradeInfo));
		doNothing().when(client).upgradeUpgradeClient(eq(rootPath),
				eq(upgradeClientUpgradeInfo));

		UpgradeResult upgradeResult = client.executeUpgrade(rootPath);
		assertEquals(upgradeResult, UpgradeResult.NO_UPGRADE);
		verify(client, never()).upgradeUpgradeClient(eq(rootPath),
				any(UpgradeInfo.class));
		verify(client, never()).upgradeClient(eq(rootPath),
				any(UpgradeInfo.class));
	}

	@Test(description = "should upgrade upgrade-client")
	public void testUpgradeUpgradeClient() throws Exception {
		String rootPath = "D:/survive-game";
		String path = "upgrade-client";
		String clientVersion = "0.0.1";
		String upgradeVersion = "0.0.2";
		FileInfo imageInfo = new FileInfo("resources/images/image.png", 200);
		FileInfo jarInfo = new FileInfo("lib/spring.jar", 500);
		List<FileInfo> fileInfos = Arrays.asList(imageInfo, jarInfo);
		UpgradeInfo upgradeInfo = UpgradeInfo.withFileInfos(path,
				clientVersion, upgradeVersion, fileInfos);

		byte[] image = new byte[] { 1, 2, 3, 4, 5 };
		byte[] jar = new byte[] { 5, 4, 3, 2, 1 };
		when(
				upgradeServerAPI.getFileContent(eq(upgradeVersion), eq(path
						+ FileSystem.FILE_SPLITTER + imageInfo.getPath())))
				.thenReturn(image);
		when(
				upgradeServerAPI.getFileContent(eq(upgradeVersion), eq(path
						+ FileSystem.FILE_SPLITTER + jarInfo.getPath())))
				.thenReturn(jar);

		InOrder order = inOrder(fileSystemService, upgradeServerAPI,
				propertiesService);

		client.upgradeUpgradeClient(rootPath, upgradeInfo);

		order.verify(fileSystemService).removeTemporaryFolder();
		order.verify(fileSystemService).createTemporaryFolder();
		order.verify(fileSystemService).writeTemporaryFile(
				eq(path + FileSystem.FILE_SPLITTER + imageInfo.getPath()),
				eq(image));
		order.verify(fileSystemService).writeTemporaryFile(
				eq(path + FileSystem.FILE_SPLITTER + jarInfo.getPath()),
				eq(jar));
		order.verify(fileSystemService).copyFromTemporaryFolderTo(eq(path),
				eq(rootPath + FileSystem.FILE_SPLITTER + path));
		order.verify(fileSystemService).removeTemporaryFolder();
		order.verify(propertiesService).updateUpgradeClientVersion(
				eq(rootPath), eq(upgradeVersion));
	}

	@Test(description = "should upgrade client")
	public void testUpgradeClient() throws Exception {
		String rootPath = "D:/survive-game";
		String path = "client";
		String clientVersion = "0.0.1";
		String upgradeVersion = "0.0.2";
		FileInfo imageInfo = new FileInfo("resources/images/image.png", 200);
		FileInfo jarInfo = new FileInfo("lib/spring.jar", 500);
		List<FileInfo> fileInfos = Arrays.asList(imageInfo, jarInfo);
		UpgradeInfo upgradeInfo = UpgradeInfo.withFileInfos(path,
				clientVersion, upgradeVersion, fileInfos);

		byte[] image = new byte[] { 1, 2, 3, 4, 5 };
		byte[] jar = new byte[] { 5, 4, 3, 2, 1 };
		when(
				upgradeServerAPI.getFileContent(eq(upgradeVersion), eq(path
						+ FileSystem.FILE_SPLITTER + imageInfo.getPath())))
				.thenReturn(image);
		when(
				upgradeServerAPI.getFileContent(eq(upgradeVersion), eq(path
						+ FileSystem.FILE_SPLITTER + jarInfo.getPath())))
				.thenReturn(jar);

		InOrder order = inOrder(fileSystemService, upgradeServerAPI,
				propertiesService);

		client.upgradeClient(rootPath, upgradeInfo);

		order.verify(fileSystemService).removeTemporaryFolder();
		order.verify(fileSystemService).createTemporaryFolder();
		order.verify(fileSystemService).writeTemporaryFile(
				eq(path + FileSystem.FILE_SPLITTER + imageInfo.getPath()),
				eq(image));
		order.verify(fileSystemService).writeTemporaryFile(
				eq(path + FileSystem.FILE_SPLITTER + jarInfo.getPath()),
				eq(jar));
		order.verify(fileSystemService).copyFromTemporaryFolderTo(eq(path),
				eq(rootPath + FileSystem.FILE_SPLITTER + path));
		order.verify(fileSystemService).removeTemporaryFolder();
		order.verify(propertiesService).updateClientVersion(eq(rootPath),
				eq(upgradeVersion));
	}

}
