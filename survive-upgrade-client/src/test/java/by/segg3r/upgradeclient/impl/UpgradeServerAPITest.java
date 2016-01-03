package by.segg3r.upgradeclient.impl;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import by.segg3r.Application;
import by.segg3r.exceptions.APIException;
import by.segg3r.http.entities.UpgradeInfo;
import by.segg3r.upgradeclient.http.impl.JsonHttpClient;
import by.segg3r.upgradeclient.http.impl.SimpleHttpClient;

public class UpgradeServerAPITest {

	@Mock
	private SimpleHttpClient simpleHttpClient;
	@Mock
	private JsonHttpClient jsonHttpClient;
	@InjectMocks
	private UpgradeServerAPIImpl api;

	private String upgradeServerHost;
	private int upgradeServerPort;

	@BeforeClass
	public void initMocks() {
		MockitoAnnotations.initMocks(this);

		upgradeServerHost = "segg3r.servegame.com";
		upgradeServerPort = 11199;
		api.setUpgradeServerHost(upgradeServerHost);
		api.setUpgradeServerPort(upgradeServerPort);
	}

	@AfterMethod
	public void resetMocks() {
		reset(simpleHttpClient, jsonHttpClient);
	}

	@Test(description = "should get application upgrade info")
	public void testGetApplicationUpgradeInfo() throws APIException {
		String clientVersion = "0.0.1";
		UpgradeInfo upgradeInfo = mock(UpgradeInfo.class);

		when(
				jsonHttpClient
						.get(eq("http://segg3r.servegame.com:11199/api/upgrade/0.0.1/client"),
								eq(UpgradeInfo.class))).thenReturn(upgradeInfo);

		assertEquals(api.getApplicationUpgradeInfo(Application.CLIENT, clientVersion), upgradeInfo);
	}

	@Test(description = "should get file content")
	public void testGetFileContent() throws APIException {
		String upgradeVersion = "0.0.2";
		String relativePath = "client/resources/img.png";
		byte[] fileContent = new byte[] { 1, 2, 3, 4, 5 };

		when(
				simpleHttpClient
						.get("http://segg3r.servegame.com:11199/api/file/0.0.2/client/resources/img.png"))
				.thenReturn(fileContent);
		
		assertEquals(api.getFileContent(upgradeVersion, relativePath), fileContent);
	}

}
