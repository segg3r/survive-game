package by.segg3r.services;

import java.util.List;

import by.segg3r.http.entities.FileInfo;
import by.segg3r.http.entities.UpgradeFileInfo;

public interface VersionFileTreeService {

	List<UpgradeFileInfo> compare(List<FileInfo> oldTree,
			List<FileInfo> newTree);

}
