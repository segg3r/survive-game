package by.segg3r.constants;

public class FileSystem {

	public static final String FILE_SPLITTER = "/";
	
	public static final String CLIENT_FOLDER = "client";
	public static final String UPGRADE_CLIENT_FOLDER = "upgrade-client";
	
	public static String getClientPath(String rootPath) {
		return rootPath + FILE_SPLITTER + CLIENT_FOLDER;
	}
	
	public static String getUpgradeClientPath(String rootPath) {
		return rootPath + FILE_SPLITTER + UPGRADE_CLIENT_FOLDER;
	}
	
	public static String getCurrentPath() {
		return System.getProperty("user.dir");
	}
	
	public static String getFullPathFromCurrentRelativePath(String relativePath) {
		return getCurrentPath() + FILE_SPLITTER + relativePath;
	}
	
}
