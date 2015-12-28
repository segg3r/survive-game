package by.segg3r.services;

import by.segg3r.exceptions.UpgradeException;
import by.segg3r.http.entities.UpgradeInfo;

public interface UpgradeService {

	UpgradeInfo getUpgradeInfo(String version, String path) throws UpgradeException;

	byte[] getFileContent(String version, String path) throws UpgradeException;
	
}
