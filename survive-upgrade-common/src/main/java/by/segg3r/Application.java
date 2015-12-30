package by.segg3r;

public enum Application {

	UPGRADE_CLIENT("upgrade-client"), CLIENT("client");

	public static Application withPath(String applicationPath) {
		for (Application application : Application.values()) {
			if (applicationPath.equals(application.getPath())) {
				return application;
			}
		}
		return null;
	}
	
	private String path;
	
	Application(String path) {
		this.path = path;
	}
	
	public String getPath() {
		return path;
	}
	
}
