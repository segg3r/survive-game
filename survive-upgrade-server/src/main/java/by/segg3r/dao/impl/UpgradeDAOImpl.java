package by.segg3r.dao.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import by.segg3r.Application;
import by.segg3r.ApplicationVersion;
import by.segg3r.FileSystem;
import by.segg3r.dao.UpgradeDAO;
import by.segg3r.exceptions.UpgradeException;
import by.segg3r.http.entities.FileInfo;
import by.segg3r.util.DigestUtil;

@Component
public class UpgradeDAOImpl implements UpgradeDAO {

	private static final String UPGRADES_DIRECTORY = "upgrades";
	private static final String EMPTY_PATH = "";

	private static final String UPGRADE_DIRECTORY_PATH = FileSystem
			.getRelativePath(UPGRADES_DIRECTORY);

	@Autowired
	private DigestUtil digestUtil;

	@Override
	public void populateApplicationVersion(
			ApplicationVersion applicationVersion, String sourcePath)
					throws UpgradeException {
		try {
			File versionDirectory = getApplicationVersionUpgradeDirectory(applicationVersion);
			if (!versionDirectory.exists()) {
				boolean versionDirectoryCreationResult = versionDirectory.mkdirs();
				if (!versionDirectoryCreationResult) {
					throw new UpgradeException(
							"Could not create application version upgrade directory "
							+ versionDirectory.getAbsolutePath());
				}
			}
			
			File sourceDirectory = new File(sourcePath);
			FileUtils.copyDirectory(sourceDirectory, versionDirectory);
		} catch (IOException e) {
			throw new UpgradeException("Could not populate application version " + applicationVersion
					+ " from source path " + sourcePath);
		}
	}

	@Override
	public List<FileInfo> getFileInfos(ApplicationVersion applicationVersion)
			throws UpgradeException {
		try {
			File versionDirectory = getApplicationVersionUpgradeDirectory(applicationVersion); 

			List<FileInfo> result = populateFileInfos(versionDirectory,
					versionDirectory.getAbsolutePath(), EMPTY_PATH);
			return result;
		} catch (IOException e) {
			throw new UpgradeException("Eror during getting file infos", e);
		}
	}

	@Override
	public List<String> getAvailableVersions() throws UpgradeException {
		File upgradeDirectory = getDirectory(UPGRADE_DIRECTORY_PATH);
		String[] versionDirectories = upgradeDirectory.list();
		if (versionDirectories == null) {
			throw new UpgradeException(
					"Error getting list of available versions");
		}
		
		return Arrays.asList(versionDirectories);
	}
	
	@Override
	public List<ApplicationVersion> getAvailableApplicationVersions(Application application)
			throws UpgradeException {
		List<String> versionDirectories = getAvailableVersions();

		List<ApplicationVersion> result = new ArrayList<ApplicationVersion>();
		for (String versionDirectory : versionDirectories) {
			String requiredDirectoryPath = UPGRADE_DIRECTORY_PATH
					+ FileSystem.FILE_SPLITTER + versionDirectory
					+ FileSystem.FILE_SPLITTER + application.getPath();
			File file = new File(requiredDirectoryPath);
			if (file.isDirectory()) {
				result.add(ApplicationVersion.of(application, versionDirectory));
			}
		}
		return result;
	}

	@Override
	public byte[] getFileContent(ApplicationVersion applicationVersion,
			String path) throws UpgradeException {
		String relativeFilePath = applicationVersion.getVersion()
				+ FileSystem.FILE_SPLITTER + applicationVersion.getApplication().getPath()
				+ FileSystem.FILE_SPLITTER + path;
		try {
			String filePath = UPGRADE_DIRECTORY_PATH
					+ FileSystem.FILE_SPLITTER + relativeFilePath;
			File file = new File(filePath);

			if (!file.isFile()) {
				throw new UpgradeException("Can not get file "
						+ relativeFilePath);
			}

			byte[] result = Files.readAllBytes(file.toPath());
			return result;
		} catch (IOException e) {
			throw new UpgradeException(
					"Error reading file " + relativeFilePath, e);
		}
	}

	private File getApplicationVersionUpgradeDirectory(ApplicationVersion applicationVersion) {
		String versionDirectoryPath = UPGRADE_DIRECTORY_PATH
				+ FileSystem.FILE_SPLITTER + applicationVersion.getVersion()
				+ FileSystem.FILE_SPLITTER
				+ applicationVersion.getApplication().getPath();
		return new File(versionDirectoryPath);
	}
	
	private File getDirectory(String directoryPath) throws UpgradeException {
		File directory = new File(directoryPath);
		if (!directory.isDirectory()) {
			boolean directoriesCreated = directory.mkdirs();
			if (!directoriesCreated) {
				throw new UpgradeException("Could not find application directory");
			}
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
	 * @throws IOException
	 */
	private List<FileInfo> populateFileInfos(File directory,
			String currentFullPath, String currentRelativePath)
			throws IOException {
		String[] subFilesNames = directory.list();
		if (subFilesNames == null) {
			return Collections.emptyList();
		}

		List<FileInfo> result = new ArrayList<FileInfo>();

		for (String subFileName : subFilesNames) {
			String subFileFullPath = currentFullPath 
					+ FileSystem.FILE_SPLITTER + subFileName;
			String subFileRelativePath = EMPTY_PATH.equals(currentRelativePath)
					? subFileName
					: currentRelativePath + FileSystem.FILE_SPLITTER + subFileName;

			File subFile = new File(subFileFullPath);
			if (subFile.isDirectory()) {
				List<FileInfo> subResult = populateFileInfos(subFile,
						subFileFullPath, subFileRelativePath);
				result.addAll(subResult);
			} else if (subFile.isFile()) {
				String digest = digestUtil.getFileDigest(subFile);
				FileInfo fileInfo = new FileInfo(subFileRelativePath,
						subFile.length(), digest);
				result.add(fileInfo);
			}
		}

		return result;
	}


}
