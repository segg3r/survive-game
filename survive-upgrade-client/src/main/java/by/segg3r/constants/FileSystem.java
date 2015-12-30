package by.segg3r.constants;

import by.segg3r.Application;

public class FileSystem {

	public static final String FILE_SPLITTER = "/";
	
	public static String getApplicationPath(String rootPath, Application application) {
		return rootPath + FILE_SPLITTER + application.getPath();
	}
	
	public static String getCurrentPath() {
		return System.getProperty("user.dir");
	}
	
	public static String getFullPathFromCurrentRelativePath(String relativePath) {
		return getCurrentPath() + FILE_SPLITTER + relativePath;
	}
	
}
