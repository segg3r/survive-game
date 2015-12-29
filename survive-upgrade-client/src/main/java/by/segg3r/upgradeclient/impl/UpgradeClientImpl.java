package by.segg3r.upgradeclient.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import by.segg3r.constants.FileSystem;
import by.segg3r.exceptions.APIException;
import by.segg3r.exceptions.UpgradeException;
import by.segg3r.http.entities.FileInfo;
import by.segg3r.http.entities.UpgradeInfo;
import by.segg3r.upgradeclient.FileSystemService;
import by.segg3r.upgradeclient.PropertiesService;
import by.segg3r.upgradeclient.UpgradeClient;
import by.segg3r.upgradeclient.UpgradeServerAPI;

@Component
public class UpgradeClientImpl implements UpgradeClient {

	@Autowired
	private PropertiesService propertiesService;
	@Autowired
	private UpgradeServerAPI upgradeServerAPI;
	@Autowired
	private FileSystemService fileSystemService;

	@Override
	public boolean executeUpgrade(String rootPath) throws UpgradeException {
		try {
			String upgradeClientVersion = propertiesService
					.getUpgradeClientVersion(rootPath);
			UpgradeInfo upgradeClientUpgradeInfo = upgradeServerAPI
					.getUpgradeClientUpgradeInfo(upgradeClientVersion);
			if (upgradeClientUpgradeInfo.isUpgradeRequired()) {
				upgradeUpgradeClient(rootPath, upgradeClientUpgradeInfo);
				return false;
			}

			String clientVersion = propertiesService.getClientVersion(rootPath);
			UpgradeInfo clientUpgradeInfo = upgradeServerAPI
					.getClientUpgradeInfo(clientVersion);
			if (clientUpgradeInfo.isUpgradeRequired()) {
				upgradeClient(rootPath, clientUpgradeInfo);
			}

			return true;
		} catch (IOException | APIException e) {
			throw new UpgradeException(
					"Error performing call to upgrade server", e);
		}
	}

	@Override
	public void upgradeClient(String rootPath, UpgradeInfo clientUpgradeInfo)
			throws UpgradeException {
		try {
			performUpgrade(rootPath, clientUpgradeInfo);
			propertiesService.updateClientVersion(rootPath,
					clientUpgradeInfo.getUpgradeVersion());
		} catch (APIException | IOException e) {
			throw new UpgradeException("Error during client upgrade",
					e);
		}
	}

	@Override
	public void upgradeUpgradeClient(String rootPath, UpgradeInfo upgradeInfo)
			throws UpgradeException {
		try {
			performUpgrade(rootPath, upgradeInfo);
			propertiesService.updateUpgradeClientVersion(rootPath,
					upgradeInfo.getUpgradeVersion());
		} catch (APIException | IOException e) {
			throw new UpgradeException("Error during upgrade client upgrade",
					e);
		}
	}

	private void performUpgrade(String rootPath, UpgradeInfo upgradeInfo)
			throws IOException, APIException {
		String upgradeClientPath = rootPath + FileSystem.FILE_SPLITTER
				+ upgradeInfo.getPath();
		String upgradeVersion = upgradeInfo.getUpgradeVersion();
		fileSystemService.removeTemporaryFolder();
		fileSystemService.createTemporaryFolder();

		List<FileInfo> fileInfos = upgradeInfo.getFileInfos();
		for (FileInfo fileInfo : fileInfos) {
			String fileRelativePathFromRoot = upgradeInfo.getPath()
					+ FileSystem.FILE_SPLITTER + fileInfo.getPath();

			byte[] fileContent = upgradeServerAPI.getFileContent(
					upgradeVersion, fileRelativePathFromRoot);
			fileSystemService.writeTemporaryFile(fileRelativePathFromRoot,
					fileContent);
		}
		fileSystemService.copyFromTemporaryFolderTo(upgradeInfo.getPath(),
				upgradeClientPath);
	}
}
