package by.segg3r.services;

import by.segg3r.Application;
import by.segg3r.exceptions.UpgradeException;
import by.segg3r.http.entities.UpgradeInfo;

public interface UpgradeService {

	UpgradeInfo getUpgradeInfo(String version, Application application) throws UpgradeException;

	byte[] getFileContent(String version, String path) throws UpgradeException;
	
}
