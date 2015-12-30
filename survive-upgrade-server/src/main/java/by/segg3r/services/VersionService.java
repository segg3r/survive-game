package by.segg3r.services;

import by.segg3r.Application;
import by.segg3r.exceptions.UpgradeException;

public interface VersionService {

	String getNewerVersion(String version, Application application) throws UpgradeException;
	
}
