package by.segg3r.services;

import by.segg3r.ApplicationVersion;
import by.segg3r.exceptions.UpgradeException;
import by.segg3r.http.entities.UpgradeInfo;

public interface UpgradeService {

	UpgradeInfo getUpgradeInfo(ApplicationVersion applicationVersion) throws UpgradeException;

	byte[] getFileContent(String version, String path) throws UpgradeException;
	
}
