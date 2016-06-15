package by.segg3r.dao;

import java.util.List;

import by.segg3r.Application;
import by.segg3r.ApplicationVersion;
import by.segg3r.exceptions.UpgradeException;
import by.segg3r.http.entities.FileInfo;

public interface UpgradeDAO {

	void populateApplicationVersion(ApplicationVersion applicationVersion,
			String sourcePath) throws UpgradeException;

	List<FileInfo> getFileInfos(ApplicationVersion applicationVersion)
			throws UpgradeException;

	List<String> getAvailableVersions() throws UpgradeException;

	List<ApplicationVersion> getAvailableApplicationVersions(
			Application application) throws UpgradeException;

	byte[] getFileContent(ApplicationVersion applicationVersion, String path)
			throws UpgradeException;

}