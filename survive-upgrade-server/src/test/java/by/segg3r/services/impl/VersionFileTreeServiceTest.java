package by.segg3r.services.impl;

import static org.testng.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import by.segg3r.http.entities.FileInfo;
import by.segg3r.http.entities.FileUpgradeMode;
import by.segg3r.http.entities.UpgradeFileInfo;

public class VersionFileTreeServiceTest {

	@InjectMocks
	private VersionFileTreeServiceImpl service;
	
	@BeforeClass
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test(description = "test added/changed/removed files")
	public void testComparingVersionFileTrees() {
		FileInfo file1 = new FileInfo("toChange.txt", 10, "someDigest");
		FileInfo file2 = new FileInfo("toRemove.txt", 20, "someDigest");
		List<FileInfo> oldTree = Arrays.asList(file1, file2);
		
		FileInfo changedFile1 = new FileInfo("toChange.txt", 10, "newDigest");
		FileInfo newFile = new FileInfo("new.txt", 5, "someDigest");
		List<FileInfo> newTree = Arrays.asList(changedFile1, newFile);
		
		List<UpgradeFileInfo> upgrade = service.compare(oldTree, newTree);
	
		assertEquals(upgrade.size(), 3);
		assertTrue(upgrade.contains(new UpgradeFileInfo(changedFile1, FileUpgradeMode.UPDATE)));
		assertTrue(upgrade.contains(new UpgradeFileInfo(file2, FileUpgradeMode.REMOVE)));
		assertTrue(upgrade.contains(new UpgradeFileInfo(newFile, FileUpgradeMode.ADD)));
	}
	
}
