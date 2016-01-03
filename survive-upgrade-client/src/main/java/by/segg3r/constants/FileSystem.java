package by.segg3r.constants;

import by.segg3r.Application;

public class FileSystem {

	public static final String FILE_SPLITTER = "/";

	private static final String RESOURCES_FOLDER = "resources";
	private static final String PROPERTIES_EXTENSION = ".properties";

	public static String getApplicationPath(String rootPath,
			Application application) {
		return rootPath + FILE_SPLITTER + application.getPath();
	}

	public static String getApplicationPropertiesFileName(
			Application application) {
		return application.getPath() + PROPERTIES_EXTENSION;
	}

	public static String getApplicationPropertiesFilePath(String rootPath,
			Application application) {
		return getApplicationPath(rootPath, application) + FILE_SPLITTER
				+ RESOURCES_FOLDER + FILE_SPLITTER
				+ getApplicationPropertiesFileName(application);
	}

	public static String getCurrentPath() {
		return System.getProperty("user.dir");
	}

	public static String getFullPathFromCurrentRelativePath(String relativePath) {
		return getCurrentPath() + FILE_SPLITTER + relativePath;
	}

}
