package by.segg3r.dao.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

import by.segg3r.Application;
import by.segg3r.dao.UpgradeDAO;
import by.segg3r.exceptions.UpgradeException;
import by.segg3r.http.entities.FileInfo;

@Component
public class UpgradeDAOImpl implements UpgradeDAO {

	private static final String PATH_SPLITTER = "/";
	private static final String CURRENT_DIRECTORY_SYSTEM_PROPERTY = "user.dir";
	private static final String UPGRADES_DIRECTORY = "upgrades";
	private static final String EMPTY_PATH = "";

	private static final String CURRENT_DIRECTORY_PATH = System
			.getProperty(CURRENT_DIRECTORY_SYSTEM_PROPERTY);
	private static final String UPGRADE_DIRECTORY_PATH = CURRENT_DIRECTORY_PATH
			+ PATH_SPLITTER + UPGRADES_DIRECTORY;

	@Override
	public List<FileInfo> getFileInfos(String version, Application application) throws UpgradeException {
		String versionDirectoryPath = UPGRADE_DIRECTORY_PATH + PATH_SPLITTER
				+ version + PATH_SPLITTER + application.getPath();
		File versionDirectory = getDirectory(versionDirectoryPath);

		List<FileInfo> result = populateFileInfos(versionDirectory,
				versionDirectoryPath, EMPTY_PATH);
		return result;
	}

	@Override
	public List<String> getAvailableVersions(Application application)
			throws UpgradeException {
		File upgradeDirectory = getDirectory(UPGRADE_DIRECTORY_PATH);
		String[] versionDirectories = upgradeDirectory.list();
		if (versionDirectories == null) {
			throw new UpgradeException(
					"Error getting list of available versions");
		}

		List<String> result = new ArrayList<String>();
		for (String versionDirectory : versionDirectories) {
			String requiredDirectoryPath = UPGRADE_DIRECTORY_PATH
					+ PATH_SPLITTER + versionDirectory + PATH_SPLITTER + application.getPath();
			File file = new File(requiredDirectoryPath);
			if (file.isDirectory()) {
				result.add(versionDirectory);
			}
		}
		return result;
	}

	@Override
	public byte[] getFileContent(String version, String path)
			throws UpgradeException {
		try {
			String filePath = UPGRADE_DIRECTORY_PATH + PATH_SPLITTER + version
					+ PATH_SPLITTER + path;
			File file = new File(filePath);

			if (!file.isFile()) {
				throw new UpgradeException("Can not get file " + version
						+ PATH_SPLITTER + path);
			}

			byte[] result = Files.readAllBytes(file.toPath());
			return result;
		} catch (IOException e) {
			throw new UpgradeException("Error reading file " + version
					+ PATH_SPLITTER + path, e);
		}
	}

	private File getDirectory(String directoryPath) throws UpgradeException {
		File directory = new File(directoryPath);
		if (!directory.isDirectory()) {
			throw new UpgradeException(directory + " is not a directory");
		}
		return directory;
	}

	/**
	 * 
	 * @param directory
	 *            directory to parse
	 * @param currentFullPath
	 *            current path to attach to found file
	 * @return recursively built list of FileInfos
	 */
	private List<FileInfo> populateFileInfos(File directory,
			String currentFullPath, String currentRelativePath) {
		String[] subFilesNames = directory.list();
		if (subFilesNames == null) {
			return Collections.emptyList();
		}

		List<FileInfo> result = new ArrayList<FileInfo>();

		for (String subFileName : subFilesNames) {
			String subFileFullPath = currentFullPath + PATH_SPLITTER
					+ subFileName;
			String subFileRelativePath = EMPTY_PATH.equals(currentRelativePath) ? subFileName
					: currentRelativePath + PATH_SPLITTER + subFileName;

			File subFile = new File(subFileFullPath);
			if (subFile.isDirectory()) {
				List<FileInfo> subResult = populateFileInfos(subFile,
						subFileFullPath, subFileRelativePath);
				result.addAll(subResult);
			} else if (subFile.isFile()) {
				FileInfo fileInfo = new FileInfo(subFileRelativePath,
						subFile.length());
				result.add(fileInfo);
			}
		}

		return result;
	}

}
