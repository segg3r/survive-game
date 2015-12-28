package by.segg3r.dao;

import java.util.List;

import by.segg3r.exceptions.UpgradeException;
import by.segg3r.http.entities.FileInfo;

public interface UpgradeDAO {

	List<FileInfo> getFileInfos(String version, String path) throws UpgradeException;

	List<String> getAvailableVersions(String path) throws UpgradeException;

	byte[] getFileContent(String version, String path) throws UpgradeException;
	
}