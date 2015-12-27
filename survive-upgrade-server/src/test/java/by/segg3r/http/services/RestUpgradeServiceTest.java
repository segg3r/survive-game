package by.segg3r.http.services;

import static org.testng.Assert.*;
import static org.mockito.Mockito.*;

import javax.ws.rs.core.Response;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import by.segg3r.exceptions.UpgradeException;
import by.segg3r.http.entities.UpgradeInfo;
import by.segg3r.services.UpgradeService;

public class RestUpgradeServiceTest {

	@Mock
	private UpgradeService upgradeService;
	@InjectMocks
	private RestUpgradeService restUpgradeService;

	@BeforeClass
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}

	@AfterMethod
	public void resetMocks() {
		reset(upgradeService);
	}

	@Test(description = "should correctly get upgrade info")
	public void testGetUpgradeInfo() throws UpgradeException {
		String version = "0.0.1";
		UpgradeInfo upgradeInfo = mock(UpgradeInfo.class);

		when(upgradeService.getUpgradeInfo(eq(version)))
				.thenReturn(upgradeInfo);

		Response result = restUpgradeService.getUpgradeInfo(version);
		UpgradeInfo actualUgradeInfo = (UpgradeInfo) result.getEntity();
		assertEquals(actualUgradeInfo, upgradeInfo);
	}

	@Test(description = "should correctly get file content")
	public void testGetFileContent() throws UpgradeException {
		String version = "1.3.4";
		String filePath = "test/test.txt";

		byte[] fileData = new byte[] { 1, 2, 3 };
		when(upgradeService.getFileContent(eq(version), eq(filePath)))
				.thenReturn(fileData);
		
		Response result = restUpgradeService.getFileContent(version, filePath);
		byte[] actualFileData = (byte[]) result.getEntity();
		assertEquals(actualFileData, fileData);
	}
}
