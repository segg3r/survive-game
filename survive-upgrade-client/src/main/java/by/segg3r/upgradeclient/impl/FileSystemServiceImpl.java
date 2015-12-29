package by.segg3r.upgradeclient.impl;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

import by.segg3r.constants.FileSystem;
import by.segg3r.upgradeclient.FileSystemService;

@Component
public class FileSystemServiceImpl implements FileSystemService {

	private static final String TEMPORARY_FOLDER_RELATIVE_PATH = "temp";
	private static final String TEMPORARY_FOLDER_FULL_PATH = FileSystem
			.getFullPathFromCurrentRelativePath(TEMPORARY_FOLDER_RELATIVE_PATH);

	@Override
	public void removeTemporaryFolder() throws IOException {
		File temporaryFolder = new File(TEMPORARY_FOLDER_FULL_PATH);
		FileUtils.deleteDirectory(temporaryFolder);
	}

	@Override
	public void createTemporaryFolder() throws IOException {
		File temporaryFolder = new File(TEMPORARY_FOLDER_FULL_PATH);
		if (!temporaryFolder.mkdir()) {
			throw new IOException("Could not create directory at "
					+ TEMPORARY_FOLDER_FULL_PATH);
		}
	}

	@Override
	public void writeTemporaryFile(String fileRelativePath, byte[] fileContent)
			throws IOException {
		String fullFilePath = TEMPORARY_FOLDER_FULL_PATH
				+ FileSystem.FILE_SPLITTER + fileRelativePath;
		File temporaryFile = new File(fullFilePath);
		FileUtils.writeByteArrayToFile(temporaryFile, fileContent);
	}

	@Override
	public void copyFromTemporaryFolderTo(String sourceRelativePath,
			String destinationFullPath) throws IOException {
		File sourceDirectory = new File(TEMPORARY_FOLDER_FULL_PATH
				+ FileSystem.FILE_SPLITTER + sourceRelativePath);
		File destinationDirectory = new File(destinationFullPath);

		FileUtils.copyDirectory(sourceDirectory, destinationDirectory);
	}

}
