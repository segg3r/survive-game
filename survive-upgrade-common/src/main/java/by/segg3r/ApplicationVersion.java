package by.segg3r;

public class ApplicationVersion {

	public static final String VERSION_ZERO = "0";
	
	private Application application;
	private String version;

	public static ApplicationVersion of(Application application, String version) {
		return new ApplicationVersion(application, version);
	}
	
	public static ApplicationVersion zeroOf(Application application) {
		return new ApplicationVersion(application, VERSION_ZERO);
	}
	
	public ApplicationVersion(Application application, String version) {
		super();
		this.application = application;
		this.version = version;
	}

	public Application getApplication() {
		return application;
	}

	public void setApplication(Application application) {
		this.application = application;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((application == null) ? 0 : application.hashCode());
		result = prime * result + ((version == null) ? 0 : version.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ApplicationVersion other = (ApplicationVersion) obj;
		if (application != other.application)
			return false;
		if (version == null) {
			if (other.version != null)
				return false;
		} else if (!version.equals(other.version))
			return false;
		return true;
	}

}
