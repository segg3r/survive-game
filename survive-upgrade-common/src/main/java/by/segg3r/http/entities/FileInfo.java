package by.segg3r.http.entities;

public class FileInfo {

	private String path;
	private long size;

	public FileInfo() {
		super();
	}

	public FileInfo(String path, long size) {
		super();
		this.path = path;
		this.size = size;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

}
