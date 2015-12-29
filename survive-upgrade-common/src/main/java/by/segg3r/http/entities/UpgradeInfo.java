package by.segg3r.http.entities;

import java.util.Collections;
import java.util.List;

public class UpgradeInfo {

	public static UpgradeInfo noUpgradeRequired(String path, String clientVersion) {
		return new UpgradeInfo(path, clientVersion, clientVersion, false,
				Collections.emptyList());
	}

	public static UpgradeInfo withFileInfos(String path, String clientVersion,
			String upgradeVersion, List<FileInfo> fileInfos) {
		return new UpgradeInfo(path, clientVersion, upgradeVersion, true, fileInfos);
	}

	private String clientVersion;
	private String upgradeVersion;
	private boolean upgradeRequired;
	private List<FileInfo> fileInfos;
	private String path;

	public UpgradeInfo() {
		super();
	}

	public UpgradeInfo(String path, String clientVersion, String upgradeVersion,
			boolean upgradeRequired, List<FileInfo> fileInfos) {
		super();
		this.path = path;
		this.clientVersion = clientVersion;
		this.upgradeVersion = upgradeVersion;
		this.upgradeRequired = upgradeRequired;
		this.fileInfos = fileInfos;
	}

	public boolean isUpgradeRequired() {
		return upgradeRequired;
	}

	public void setUpgradeRequired(boolean upgradeRequired) {
		this.upgradeRequired = upgradeRequired;
	}

	public List<FileInfo> getFileInfos() {
		return fileInfos;
	}

	public void setFileInfos(List<FileInfo> fileInfos) {
		this.fileInfos = fileInfos;
	}

	public String getClientVersion() {
		return clientVersion;
	}

	public void setClientVersion(String clientVersion) {
		this.clientVersion = clientVersion;
	}

	public String getUpgradeVersion() {
		return upgradeVersion;
	}

	public void setUpgradeVersion(String upgradeVersion) {
		this.upgradeVersion = upgradeVersion;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
