package by.segg3r.http.entities;

public class UpgradeFileInfo extends FileInfo {

	private FileUpgradeMode fileUpgradeMode;

	public UpgradeFileInfo(FileInfo fileInfo, FileUpgradeMode fileUpgradeMode) {
		this(fileInfo.getPath(), fileInfo.getSize(), fileInfo.getDigest(),
				fileUpgradeMode);
	}

	public UpgradeFileInfo(String path, long size, String digest,
			FileUpgradeMode fileUpgradeMode) {
		super(path, size, digest);
		this.fileUpgradeMode = fileUpgradeMode;
	}

	public FileUpgradeMode getFileUpgradeMode() {
		return fileUpgradeMode;
	}

	public void setFileUpgradeMode(FileUpgradeMode fileUpgradeMode) {
		this.fileUpgradeMode = fileUpgradeMode;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((fileUpgradeMode == null) ? 0 : fileUpgradeMode.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		UpgradeFileInfo other = (UpgradeFileInfo) obj;
		if (fileUpgradeMode != other.fileUpgradeMode)
			return false;
		return true;
	}
	
}
