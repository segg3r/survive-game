package by.segg3r.upgradeclient.impl;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import by.segg3r.constants.FileSystem;
import by.segg3r.exceptions.APIException;
import by.segg3r.exceptions.UpgradeException;
import by.segg3r.http.entities.FileUpgradeMode;
import by.segg3r.http.entities.UpgradeFileInfo;
import by.segg3r.http.entities.UpgradeInfo;
import by.segg3r.upgradeclient.FileSystemService;
import by.segg3r.upgradeclient.PropertiesService;
import by.segg3r.upgradeclient.UpgradeClient;
import by.segg3r.upgradeclient.UpgradeResult;
import by.segg3r.upgradeclient.UpgradeServerAPI;

@Component
public class UpgradeClientImpl implements UpgradeClient {

	private static final Logger LOG = LogManager
			.getLogger(UpgradeClientImpl.class);

	private static final double ONE_HUNDRED_PERCENT = 100.;
	private static final String SPACE = " ";

	@Autowired
	private PropertiesService propertiesService;
	@Autowired
	private UpgradeServerAPI upgradeServerAPI;
	@Autowired
	private FileSystemService fileSystemService;

	@Override
	public UpgradeResult executeUpgrade(String rootPath)
			throws UpgradeException {
		try {
			String upgradeClientVersion = propertiesService
					.getUpgradeClientVersion(rootPath);
			UpgradeInfo upgradeClientUpgradeInfo = upgradeServerAPI
					.getUpgradeClientUpgradeInfo(upgradeClientVersion);
			if (upgradeClientUpgradeInfo.isUpgradeRequired()) {
				upgradeUpgradeClient(rootPath, upgradeClientUpgradeInfo);
				return UpgradeResult.UPGRADER_UPGRADED;
			} else {
				System.out.println("Upgrade-client is up-to-date");
			}

			String clientVersion = propertiesService.getClientVersion(rootPath);
			UpgradeInfo clientUpgradeInfo = upgradeServerAPI
					.getClientUpgradeInfo(clientVersion);
			if (clientUpgradeInfo.isUpgradeRequired()) {
				upgradeClient(rootPath, clientUpgradeInfo);
				return UpgradeResult.CLIENT_UPGRADED;
			} else {
				System.out.println("Client is up-to-date");
			}

			return UpgradeResult.NO_UPGRADE;
		} catch (IOException | APIException e) {
			throw new UpgradeException(
					"Error performing call to upgrade server", e);
		}
	}

	@Override
	public void upgradeClient(String rootPath, UpgradeInfo clientUpgradeInfo)
			throws UpgradeException {
		try {
			String upgradingClientMessage = "Upgrading client";
			LOG.info(upgradingClientMessage);
			System.out.println(upgradingClientMessage);

			performUpgrade(rootPath, clientUpgradeInfo);
			propertiesService.updateClientVersion(rootPath,
					clientUpgradeInfo.getUpgradeVersion());
		} catch (APIException | IOException e) {
			throw new UpgradeException("Error during client upgrade", e);
		}
	}

	@Override
	public void upgradeUpgradeClient(String rootPath, UpgradeInfo upgradeInfo)
			throws UpgradeException {
		try {
			String upgradingUpgradeClientMessage = "Upgrading upgrade-client";
			LOG.info(upgradingUpgradeClientMessage);
			System.out.println(upgradingUpgradeClientMessage);

			performUpgrade(rootPath, upgradeInfo);
			propertiesService.updateUpgradeClientVersion(rootPath,
					upgradeInfo.getUpgradeVersion());
		} catch (APIException | IOException e) {
			throw new UpgradeException("Error during upgrade client upgrade", e);
		}
	}

	private void performUpgrade(String rootPath, UpgradeInfo upgradeInfo)
			throws IOException, APIException {
		String clientVersionMessage = "Client version : "
				+ upgradeInfo.getClientVersion();
		LOG.info(clientVersionMessage);
		System.out.println(clientVersionMessage);
		String upgradeVersionMessage = "Upgrade version : "
				+ upgradeInfo.getUpgradeVersion();
		LOG.info(upgradeVersionMessage);
		System.out.println(upgradeVersionMessage);

		String upgradeClientPath = rootPath + FileSystem.FILE_SPLITTER
				+ upgradeInfo.getPath();
		String upgradeVersion = upgradeInfo.getUpgradeVersion();
		fileSystemService.removeTemporaryFolder();
		fileSystemService.createTemporaryFolder();

		long totalSize = upgradeInfo.getTotalSize();
		String totalSizeMessage = "Total size : " + totalSize;
		LOG.info(totalSizeMessage);
		System.out.println(totalSizeMessage);

		long upgraded = 0;

		List<UpgradeFileInfo> fileInfos = upgradeInfo.getFileInfos();
		for (UpgradeFileInfo fileInfo : fileInfos) {
			upgraded = upgradeFile(upgradeInfo, upgradeClientPath,
					upgradeVersion, totalSize, upgraded, fileInfo);
		}

		String copyingMessage = "Copying...";
		LOG.info(copyingMessage);
		System.out.println(copyingMessage);

		fileSystemService.copyFromTemporaryFolderTo(upgradeInfo.getPath(),
				upgradeClientPath);
		fileSystemService.removeTemporaryFolder();
	}

	private long upgradeFile(UpgradeInfo upgradeInfo, String upgradeClientPath,
			String upgradeVersion, long totalSize, long upgraded,
			UpgradeFileInfo fileInfo) throws APIException, IOException {
		if (fileInfo.getFileUpgradeMode() == FileUpgradeMode.ADD
				|| fileInfo.getFileUpgradeMode() == FileUpgradeMode.UPDATE) {
			String fileRelativePathFromRoot = upgradeInfo.getPath()
					+ FileSystem.FILE_SPLITTER + fileInfo.getPath();

			byte[] fileContent = upgradeServerAPI.getFileContent(
					upgradeVersion, fileRelativePathFromRoot);
			fileSystemService.writeTemporaryFile(fileRelativePathFromRoot,
					fileContent);
		} else if (fileInfo.getFileUpgradeMode() == FileUpgradeMode.REMOVE) {
			String removedFileFullPath = upgradeClientPath
					+ FileSystem.FILE_SPLITTER + fileInfo.getPath();
			fileSystemService.removeFile(removedFileFullPath);
		}
		
		upgraded += fileInfo.getSize();
		int percent = (int) (upgraded * ONE_HUNDRED_PERCENT / totalSize);
		String upgradedMessage = "Upgraded : " + SPACE + upgraded + "/" + totalSize
				+ SPACE + ">>> " + percent + "% <<<";
		LOG.info(upgradedMessage);
		System.out.println(upgradedMessage);
		
		return upgraded;
	}
}
