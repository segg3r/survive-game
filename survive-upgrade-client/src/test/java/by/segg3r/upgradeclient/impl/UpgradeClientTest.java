package by.segg3r.upgradeclient.impl;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import by.segg3r.Application;
import by.segg3r.constants.FileSystem;
import by.segg3r.exceptions.APIException;
import by.segg3r.exceptions.UpgradeException;
import by.segg3r.http.entities.FileUpgradeMode;
import by.segg3r.http.entities.UpgradeFileInfo;
import by.segg3r.http.entities.UpgradeInfo;
import by.segg3r.upgradeclient.FileSystemService;
import by.segg3r.upgradeclient.PropertiesService;
import by.segg3r.upgradeclient.UpgradeComponent;
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

	private List<UpgradeComponent> upgradeComponents;

	@BeforeClass
	public void initMocks() {
		MockitoAnnotations.initMocks(this);

		upgradeComponents = Arrays.asList(mock(UpgradeComponent.class),
				mock(UpgradeComponent.class));
		client.setUpgradeComponents(upgradeComponents);

		client = spy(client);
	}

	@AfterMethod
	public void resetMocks() {
		reset(propertiesService, upgradeServerAPI, fileSystemService, client);
		for (UpgradeComponent upgradeComponentMock : upgradeComponents) {
			reset(upgradeComponentMock);
		}
	}

	@Test(description = "upgrade execution should return 'upgraded' if upgrade-client was upgraded")
	public void testExecuteUpgradeUpradeClientUpgraded()
			throws UpgradeException {
		String rootPath = "rootPath";
		doReturn(UpgradeResult.NO_UPGRADE).when(client).upgradeApplication(
				eq(rootPath), any(Application.class));
		doReturn(UpgradeResult.UPGRADED).when(client).upgradeApplication(
				eq(rootPath), eq(Application.UPGRADE_CLIENT));

		assertEquals(client.executeUpgrade(rootPath), UpgradeResult.UPGRADED);
	}

	@Test(description = "upgrade execution should return 'upgraded' if upgrade-client was not upgraded, but client was")
	public void testExecuteUpgradeClientUpgraded() throws UpgradeException {
		String rootPath = "rootPath";
		doReturn(UpgradeResult.NO_UPGRADE).when(client).upgradeApplication(
				eq(rootPath), any(Application.class));
		doReturn(UpgradeResult.NO_UPGRADE).when(client).upgradeApplication(
				eq(rootPath), eq(Application.UPGRADE_CLIENT));
		doReturn(UpgradeResult.UPGRADED).when(client).upgradeApplication(
				eq(rootPath), eq(Application.CLIENT));

		assertEquals(client.executeUpgrade(rootPath), UpgradeResult.UPGRADED);
	}

	@Test(description = "upgrade execution should return 'no-upgrade' if none of application were upgraded")
	public void testExecuteUpgradeNoUpgrade() throws UpgradeException {
		String rootPath = "rootPath";
		doReturn(UpgradeResult.NO_UPGRADE).when(client).upgradeApplication(
				eq(rootPath), any(Application.class));

		assertEquals(client.executeUpgrade(rootPath), UpgradeResult.NO_UPGRADE);
	}

	@Test(description = "application upgrade should return 'no-upgrade' if upgrade info does not require upgrade")
	public void testUpgradeApplicationNoUpgrade() throws UpgradeException,
			APIException, IOException {
		String rootPath = "D:/survive-game";
		String version = "0.0.2";
		UpgradeInfo upgradeInfo = UpgradeInfo.noUpgradeRequired(
				Application.CLIENT.getPath(), version);

		when(
				propertiesService.getApplicationVersion(eq(rootPath),
						eq(Application.CLIENT))).thenReturn(version);
		when(
				upgradeServerAPI.getApplicationUpgradeInfo(
						eq(Application.CLIENT), eq(version))).thenReturn(
				upgradeInfo);
		doNothing().when(client).upgradeFile(anyString(),
				any(Application.class), any(UpgradeInfo.class),
				any(UpgradeFileInfo.class));

		UpgradeResult upgradeResult = client.upgradeApplication(rootPath,
				Application.CLIENT);
		assertEquals(upgradeResult, UpgradeResult.NO_UPGRADE);

		verify(fileSystemService, never()).removeTemporaryFolder();
		verify(fileSystemService, never()).createTemporaryFolder();
		verify(client, never()).upgradeFile(anyString(),
				any(Application.class), any(UpgradeInfo.class),
				any(UpgradeFileInfo.class));
		verify(fileSystemService, never()).copyFromTemporaryFolderTo(
				anyString(), anyString());
		for (UpgradeComponent mockUpgradeComponent : upgradeComponents) {
			verify(mockUpgradeComponent, never())
					.beforeApplicationUpgrade(anyString(),
							any(Application.class), any(UpgradeInfo.class));
			verify(mockUpgradeComponent, never())
					.afterApplicationUpgrade(anyString(),
							any(Application.class), any(UpgradeInfo.class));
		}
	}

	@Test(description = "should upgrade application and return 'upgraded'")
	public void testUpgradeApplication() throws Exception {
		String rootPath = "D:/survive-game";
		String clientVersion = "0.0.1";
		String upgradeVersion = "0.0.2";

		// files
		UpgradeFileInfo file1 = mock(UpgradeFileInfo.class);
		UpgradeFileInfo file2 = mock(UpgradeFileInfo.class);
		List<UpgradeFileInfo> fileInfos = Arrays.asList(file1, file2);

		// upgrade info
		UpgradeInfo upgradeInfo = UpgradeInfo.withFileInfos(
				Application.CLIENT.getPath(), clientVersion, upgradeVersion,
				fileInfos);
		when(
				propertiesService.getApplicationVersion(eq(rootPath),
						eq(Application.CLIENT))).thenReturn(clientVersion);
		when(
				upgradeServerAPI.getApplicationUpgradeInfo(
						eq(Application.CLIENT), eq(clientVersion))).thenReturn(
				upgradeInfo);
		doNothing().when(client).upgradeFile(anyString(),
				any(Application.class), any(UpgradeInfo.class),
				any(UpgradeFileInfo.class));

		InOrder order = inOrder(fileSystemService, upgradeServerAPI,
				propertiesService, client, upgradeComponents.get(0),
				upgradeComponents.get(1));

		client.upgradeApplication(rootPath, Application.CLIENT);

		for (UpgradeComponent mockUpgradeComponent : upgradeComponents) {
			order.verify(mockUpgradeComponent).beforeApplicationUpgrade(
					eq(rootPath), eq(Application.CLIENT), eq(upgradeInfo));
		}
		order.verify(fileSystemService).removeTemporaryFolder();
		order.verify(fileSystemService).createTemporaryFolder();
		for (UpgradeFileInfo fileInfo : fileInfos) {
			order.verify(client).upgradeFile(eq(rootPath),
					eq(Application.CLIENT), eq(upgradeInfo), eq(fileInfo));
		}
		order.verify(fileSystemService).copyFromTemporaryFolderTo(
				eq(Application.CLIENT.getPath()),
				eq(rootPath + FileSystem.FILE_SPLITTER
						+ Application.CLIENT.getPath()));
		order.verify(fileSystemService).removeTemporaryFolder();
		for (UpgradeComponent mockUpgradeComponent : upgradeComponents) {
			order.verify(mockUpgradeComponent).afterApplicationUpgrade(
					eq(rootPath), eq(Application.CLIENT), eq(upgradeInfo));
		}
		order.verify(propertiesService).updateApplicationVersion(eq(rootPath),
				eq(Application.CLIENT), eq(upgradeVersion));
	}

	@Test(description = "verify add file flow")
	public void testUpgradeFileAdd() throws UpgradeException {
		String rootPath = "rootPath";
		Application application = Application.CLIENT;
		UpgradeInfo upgradeInfo = mock(UpgradeInfo.class);
		UpgradeFileInfo fileInfo = mock(UpgradeFileInfo.class);
		when(fileInfo.getFileUpgradeMode()).thenReturn(FileUpgradeMode.ADD);

		doNothing().when(client).updateFile(anyString(),
				any(Application.class), any(UpgradeInfo.class),
				any(UpgradeFileInfo.class));

		InOrder order = inOrder(client, upgradeComponents.get(0),
				upgradeComponents.get(1));

		client.upgradeFile(rootPath, application, upgradeInfo, fileInfo);

		for (UpgradeComponent mockUpgradeComponent : upgradeComponents) {
			order.verify(mockUpgradeComponent).beforeFile(eq(rootPath),
					eq(application), eq(fileInfo));
		}
		order.verify(client).updateFile(rootPath, application, upgradeInfo,
				fileInfo);
		for (UpgradeComponent mockUpgradeComponent : upgradeComponents) {
			order.verify(mockUpgradeComponent).afterFile(eq(rootPath),
					eq(application), eq(fileInfo));
		}
	}

	@Test(description = "verify update file flow")
	public void testUpgradeFileUpdate() throws UpgradeException {
		String rootPath = "rootPath";
		Application application = Application.CLIENT;
		UpgradeInfo upgradeInfo = mock(UpgradeInfo.class);
		UpgradeFileInfo fileInfo = mock(UpgradeFileInfo.class);
		when(fileInfo.getFileUpgradeMode()).thenReturn(FileUpgradeMode.UPDATE);

		doNothing().when(client).updateFile(anyString(),
				any(Application.class), any(UpgradeInfo.class),
				any(UpgradeFileInfo.class));

		InOrder order = inOrder(client, upgradeComponents.get(0),
				upgradeComponents.get(1));

		client.upgradeFile(rootPath, application, upgradeInfo, fileInfo);

		for (UpgradeComponent mockUpgradeComponent : upgradeComponents) {
			order.verify(mockUpgradeComponent).beforeFile(eq(rootPath),
					eq(application), eq(fileInfo));
		}
		order.verify(client).updateFile(rootPath, application, upgradeInfo,
				fileInfo);
		for (UpgradeComponent mockUpgradeComponent : upgradeComponents) {
			order.verify(mockUpgradeComponent).afterFile(eq(rootPath),
					eq(application), eq(fileInfo));
		}
	}

	@Test(description = "verify remove file flow")
	public void testUpgradeFileRemove() throws UpgradeException {
		String rootPath = "rootPath";
		Application application = Application.CLIENT;
		UpgradeInfo upgradeInfo = mock(UpgradeInfo.class);
		UpgradeFileInfo fileInfo = mock(UpgradeFileInfo.class);
		when(fileInfo.getFileUpgradeMode()).thenReturn(FileUpgradeMode.REMOVE);

		doNothing().when(client).updateFile(anyString(),
				any(Application.class), any(UpgradeInfo.class),
				any(UpgradeFileInfo.class));

		InOrder order = inOrder(client, upgradeComponents.get(0),
				upgradeComponents.get(1));

		client.upgradeFile(rootPath, application, upgradeInfo, fileInfo);

		for (UpgradeComponent mockUpgradeComponent : upgradeComponents) {
			order.verify(mockUpgradeComponent).beforeFile(eq(rootPath),
					eq(application), eq(fileInfo));
		}
		order.verify(client).removeFile(rootPath, application, upgradeInfo,
				fileInfo);
		for (UpgradeComponent mockUpgradeComponent : upgradeComponents) {
			order.verify(mockUpgradeComponent).afterFile(eq(rootPath),
					eq(application), eq(fileInfo));
		}
	}

	@Test(description = "should update file")
	public void testUpdateFile() throws Exception {
		String root = "D:/survive-game";
		String upgradeVersion = "0.0.2";
		String filePath = "update.txt";
		byte[] fileContent = new byte[] { 1, 2, 3 };

		UpgradeInfo upgradeInfo = mock(UpgradeInfo.class);
		when(upgradeInfo.getPath()).thenReturn(Application.CLIENT.getPath());
		when(upgradeInfo.getUpgradeVersion()).thenReturn(upgradeVersion);

		UpgradeFileInfo fileInfo = mock(UpgradeFileInfo.class);
		when(fileInfo.getPath()).thenReturn(filePath);

		when(
				upgradeServerAPI.getFileContent(eq(upgradeVersion),
						eq(Application.CLIENT.getPath()
								+ FileSystem.FILE_SPLITTER + filePath)))
				.thenReturn(fileContent);

		client.updateFile(root, Application.CLIENT, upgradeInfo, fileInfo);

		verify(fileSystemService).writeTemporaryFile(
				eq(Application.CLIENT.getPath() + FileSystem.FILE_SPLITTER
						+ filePath), eq(fileContent));
	}

	@Test(description = "should remove file")
	public void testRemoveFile() throws Exception {
		String root = "D:/survive-game";
		String filePath = "update.txt";

		UpgradeInfo upgradeInfo = mock(UpgradeInfo.class);

		UpgradeFileInfo fileInfo = mock(UpgradeFileInfo.class);
		when(fileInfo.getPath()).thenReturn(filePath);

		client.removeFile(root, Application.CLIENT, upgradeInfo, fileInfo);

		verify(fileSystemService).removeFile(
				eq(root + FileSystem.FILE_SPLITTER
						+ Application.CLIENT.getPath()
						+ FileSystem.FILE_SPLITTER + filePath));
	}

}
