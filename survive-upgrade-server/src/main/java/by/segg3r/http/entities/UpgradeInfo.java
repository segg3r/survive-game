package by.segg3r.http.entities;

import java.util.Collections;
import java.util.List;

public class UpgradeInfo {

	public static UpgradeInfo noUpgradeRequired(String clientVersion) {
		return new UpgradeInfo(clientVersion, clientVersion, false,
				Collections.emptyList());
	}

	public static UpgradeInfo withFileInfos(String clientVersion,
			String upgradeVersion, List<FileInfo> fileInfos) {
		return new UpgradeInfo(clientVersion, upgradeVersion, true, fileInfos);
	}

	private String clientVersion;
	private String upgradeVersion;
	private boolean upgradeRequired;
	private List<FileInfo> fileInfos;

	public UpgradeInfo() {
		super();
	}

	public UpgradeInfo(String clientVersion, String upgradeVersion,
			boolean upgradeRequired, List<FileInfo> fileInfos) {
		super();
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

}
