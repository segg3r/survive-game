package by.segg3r.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import by.segg3r.http.entities.FileInfo;
import by.segg3r.http.entities.FileUpgradeMode;
import by.segg3r.http.entities.UpgradeFileInfo;
import by.segg3r.services.VersionFileTreeService;

@Component
public class VersionFileTreeServiceImpl implements VersionFileTreeService {

	@Override
	public List<UpgradeFileInfo> compare(List<FileInfo> oldTree,
			List<FileInfo> newTree) {
		Map<String, FileInfo> oldTreeMap = oldTree.stream().collect(
				Collectors.toMap(FileInfo::getPath, Function.identity()));
		Map<String, FileInfo> newTreeMap = newTree.stream().collect(
				Collectors.toMap(FileInfo::getPath, Function.identity()));

		List<FileInfo> removedFiles = oldTree.stream()
				.filter(oldFile -> newTreeMap.get(oldFile.getPath()) == null)
				.collect(Collectors.toList());
		List<FileInfo> addedFiles = newTree.stream()
				.filter(newFile -> oldTreeMap.get(newFile.getPath()) == null)
				.collect(Collectors.toList());
		List<FileInfo> updatedFiles = oldTree.stream().filter(oldFile -> {
			FileInfo newFile = newTreeMap.get(oldFile.getPath());
			if (newFile == null) {
				return false;
			}
			return !oldFile.getDigest().equals(newFile.getDigest());
		}).collect(Collectors.toList());

		List<UpgradeFileInfo> result = new ArrayList<UpgradeFileInfo>();
		result.addAll(updatedFiles
				.stream()
				.map(updatedFile -> new UpgradeFileInfo(updatedFile,
						FileUpgradeMode.UPDATE)).collect(Collectors.toList()));
		result.addAll(addedFiles
				.stream()
				.map(addedFile -> new UpgradeFileInfo(addedFile,
						FileUpgradeMode.ADD)).collect(Collectors.toList()));
		result.addAll(removedFiles
				.stream()
				.map(removedFile -> new UpgradeFileInfo(removedFile,
						FileUpgradeMode.REMOVE)).collect(Collectors.toList()));
		return result;
	}
}
