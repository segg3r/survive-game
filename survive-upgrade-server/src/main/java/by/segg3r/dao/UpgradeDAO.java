package by.segg3r.dao;

import java.util.List;

import by.segg3r.Application;
import by.segg3r.ApplicationVersion;
import by.segg3r.exceptions.UpgradeException;
import by.segg3r.http.entities.FileInfo;

public interface UpgradeDAO {

	List<FileInfo> getFileInfos(ApplicationVersion applicationVersion) throws UpgradeException;

	List<ApplicationVersion> getAvailableVersions(Application application) throws UpgradeException;

	byte[] getFileContent(String version, String path) throws UpgradeException;

}