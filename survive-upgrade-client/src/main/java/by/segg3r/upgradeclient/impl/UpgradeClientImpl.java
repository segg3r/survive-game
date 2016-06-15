package by.segg3r.upgradeclient.impl;

import java.io.IOException;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import by.segg3r.Application;
import by.segg3r.FileSystem;
import by.segg3r.exceptions.APIException;
import by.segg3r.exceptions.UpgradeException;
import by.segg3r.http.entities.FileUpgradeMode;
import by.segg3r.http.entities.UpgradeFileInfo;
import by.segg3r.http.entities.UpgradeInfo;
import by.segg3r.upgradeclient.FileSystemService;
import by.segg3r.upgradeclient.PropertiesService;
import by.segg3r.upgradeclient.UpgradeClient;
import by.segg3r.upgradeclient.UpgradeComponent;
import by.segg3r.upgradeclient.UpgradeResult;
import by.segg3r.upgradeclient.UpgradeServerAPI;

@Component
public class UpgradeClientImpl implements UpgradeClient {

	private static final double ONE_HUNDRED_PERCENT = 100.;

	@Autowired
	private PropertiesService propertiesService;
	@Autowired
	private UpgradeServerAPI upgradeServerAPI;
	@Autowired
	private FileSystemService fileSystemService;
	@Autowired
	private Collection<UpgradeComponent> upgradeComponents;

	@Override
	public UpgradeResult executeUpgrade(String rootPath)
			throws UpgradeException {
		for (Application application : Application.values()) {
			UpgradeResult upgradeResult = upgradeApplication(rootPath,
					application);
			if (upgradeResult == UpgradeResult.UPGRADED) {
				return UpgradeResult.UPGRADED;
			}
		}

		return UpgradeResult.NO_UPGRADE;
	}

	@Override
	public UpgradeResult upgradeApplication(String rootPath,
			Application application) throws UpgradeException {
		try {
			String applicationVersion = propertiesService
					.getApplicationVersion(rootPath, application);
			UpgradeInfo upgradeInfo = upgradeServerAPI
					.getApplicationUpgradeInfo(application, applicationVersion);

			if (upgradeInfo.isUpgradeRequired()) {
				performApplicationUpgrade(rootPath, application, upgradeInfo);

				return UpgradeResult.UPGRADED;
			} else {
				System.out.println("No upgrade for " + application.getPath()
						+ " is needed");
				return UpgradeResult.NO_UPGRADE;
			}
		} catch (APIException | IOException e) {
			throw new UpgradeException("Error upgrading "
					+ application.getPath(), e);
		}
	}

	private void performApplicationUpgrade(String rootPath,
			Application application, UpgradeInfo upgradeInfo)
			throws IOException, UpgradeException {
		System.out.println("Upgrading " + application.getPath() + ": "
				+ upgradeInfo.getClientVersion() + " >>> "
				+ upgradeInfo.getUpgradeVersion());

		for (UpgradeComponent upgradeComponent : upgradeComponents) {
			upgradeComponent.beforeApplicationUpgrade(rootPath, application, upgradeInfo);
		}

		long totalSize = upgradeInfo.getTotalSize();
		long upgraded = 0;

		fileSystemService.removeTemporaryFolder();
		fileSystemService.createTemporaryFolder();
		for (UpgradeFileInfo fileInfo : upgradeInfo.getFileInfos()) {
			upgradeFile(rootPath, application, upgradeInfo, fileInfo);

			upgraded += fileInfo.getSize();
			int percent = (int) (upgraded * ONE_HUNDRED_PERCENT / totalSize);
			System.out.println(">>> " + percent + "% <<< " + upgraded + "/"
					+ totalSize);
		}

		System.out.println("Copying...");
		fileSystemService.copyFromTemporaryFolderTo(upgradeInfo.getPath(),
				FileSystem.getApplicationPath(rootPath, application));
		fileSystemService.removeTemporaryFolder();

		for (UpgradeComponent upgradeComponent : upgradeComponents) {
			upgradeComponent.afterApplicationUpgrade(rootPath, application, upgradeInfo);
		}
		
		propertiesService.updateApplicationVersion(rootPath, application,
				upgradeInfo.getUpgradeVersion());
	}

	@Override
	public void upgradeFile(String rootPath, Application application,
			UpgradeInfo upgradeInfo, UpgradeFileInfo fileInfo)
			throws UpgradeException {
		for (UpgradeComponent upgradeComponent : upgradeComponents) {
			upgradeComponent.beforeFile(rootPath, application, fileInfo);
		}

		if (fileInfo.getFileUpgradeMode() == FileUpgradeMode.ADD
				|| fileInfo.getFileUpgradeMode() == FileUpgradeMode.UPDATE) {
			updateFile(rootPath, application, upgradeInfo, fileInfo);
		} else if (fileInfo.getFileUpgradeMode() == FileUpgradeMode.REMOVE) {
			removeFile(rootPath, application, upgradeInfo, fileInfo);
		}

		for (UpgradeComponent upgradeComponent : upgradeComponents) {
			upgradeComponent.afterFile(rootPath, application, fileInfo);
		}
	}

	@Override
	public void updateFile(String rootPath, Application application,
			UpgradeInfo upgradeInfo, UpgradeFileInfo fileInfo)
			throws UpgradeException {
		try {
			String fileRelativePathFromRoot = upgradeInfo.getPath()
					+ FileSystem.FILE_SPLITTER + fileInfo.getPath();

			byte[] fileContent = upgradeServerAPI.getFileContent(
					upgradeInfo.getUpgradeVersion(), fileRelativePathFromRoot);
			fileSystemService.writeTemporaryFile(fileRelativePathFromRoot,
					fileContent);
		} catch (IOException | APIException e) {
			throw new UpgradeException("Error updating file "
					+ fileInfo.getPath(), e);
		}
	}

	@Override
	public void removeFile(String rootPath, Application application,
			UpgradeInfo upgradeInfo, UpgradeFileInfo fileInfo)
			throws UpgradeException {
		try {
			String removedFileFullPath = FileSystem.getApplicationPath(
					rootPath, application)
					+ FileSystem.FILE_SPLITTER
					+ fileInfo.getPath();
			fileSystemService.removeFile(removedFileFullPath);
		} catch (IOException e) {
			throw new UpgradeException("Error removing file "
					+ fileInfo.getPath(), e);
		}
	}

	public void setUpgradeComponents(
			Collection<UpgradeComponent> upgradeComponents) {
		this.upgradeComponents = upgradeComponents;
	}

}
