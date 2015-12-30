package by.segg3r.upgradeclient;

import java.io.IOException;

public interface FileSystemService {

	void removeTemporaryFolder() throws IOException;

	void createTemporaryFolder() throws IOException;

	void writeTemporaryFile(String fileRelativePath, byte[] fileContent) throws IOException;

	void copyFromTemporaryFolderTo(String sourceRelativePath, String destinationFullPath) throws IOException;

	void removeFile(String path) throws IOException;
	
}
