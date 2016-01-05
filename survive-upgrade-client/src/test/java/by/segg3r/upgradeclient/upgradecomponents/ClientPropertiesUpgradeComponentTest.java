package by.segg3r.upgradeclient.upgradecomponents;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

import java.io.IOException;
import java.util.Properties;

import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import by.segg3r.Application;
import by.segg3r.http.entities.UpgradeInfo;
import by.segg3r.upgradeclient.PropertiesService;
import by.segg3r.upgradeclient.upradecomponents.ClientPropertiesUpgradeComponent;

public class ClientPropertiesUpgradeComponentTest {

	@Captor
	private ArgumentCaptor<Properties> propertiesCaptor;
	@Mock
	private PropertiesService propertiesService;
	@InjectMocks
	private ClientPropertiesUpgradeComponent component;

	@BeforeClass
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}

	@AfterMethod
	public void resetMocks() {
		reset(propertiesService);

		component.setClientProperties(null);
	}
	
	@Test(description = "should not load client properties if can not find file")
	public void testNotLoadingClientPropertiesFileNotFound() throws Exception {
		String rootPath = "D:/survive-game";
		Application application = Application.CLIENT;
		UpgradeInfo upgradeInfo = mock(UpgradeInfo.class);
		
		when(propertiesService.getProperties(eq(rootPath), eq(application)))
			.thenThrow(new IOException());
	
		component.beforeApplicationUpgrade(rootPath, application, upgradeInfo);
		
		assertNull(component.getClientProperties());
	}

	@Test(description = "should load client properties before client upgrade")
	public void testLoadingPropertiesBeforeApplicationUpgrade()
			throws Exception {
		String rootPath = "D:/survive-game";
		Application application = Application.CLIENT;
		Properties properties = mock(Properties.class);
		UpgradeInfo upgradeInfo = mock(UpgradeInfo.class);

		when(propertiesService.getProperties(rootPath, application))
				.thenReturn(properties);

		component.beforeApplicationUpgrade(rootPath, application, upgradeInfo);

		assertEquals(component.getClientProperties(), properties);
	}

	@Test(description = "should not load client properties if not client application")
	public void testPropertiesShouldNotLoadIfNotClient() throws Exception {
		String rootPath = "D:/survive-game";
		Application application = Application.UPGRADE_CLIENT;
		Properties properties = mock(Properties.class);
		UpgradeInfo upgradeInfo = mock(UpgradeInfo.class);

		when(propertiesService.getProperties(eq(rootPath), eq(application)))
				.thenReturn(properties);

		component.beforeApplicationUpgrade(rootPath, application, upgradeInfo);

		assertNull(component.getClientProperties());
	}

	@Test(description = "should update properties with old saved properties")
	public void testUpdatePropertiesAfterClientUpgrade() throws Exception {
		String rootPath = "D:/survive-game";
		Application application = Application.CLIENT;
		UpgradeInfo upgradeInfo = mock(UpgradeInfo.class);

		Properties oldProperties = new Properties();
		oldProperties.setProperty("key1", "value1");
		oldProperties.setProperty("key2", "value2");
		component.setClientProperties(oldProperties);

		Properties newProperties = new Properties();
		newProperties.setProperty("key1", "overrideThis");
		newProperties.setProperty("key3", "value3");
		when(propertiesService.getProperties(eq(rootPath), eq(application)))
				.thenReturn(newProperties);

		component.afterApplicationUpgrade(rootPath, application, upgradeInfo);

		verify(propertiesService).updateProperties(eq(rootPath),
				eq(application), propertiesCaptor.capture());
		Properties savedProperties = propertiesCaptor.getValue();
		assertEquals(savedProperties.getProperty("key1"), "value1");
		assertEquals(savedProperties.getProperty("key2"), "value2");
		assertEquals(savedProperties.getProperty("key3"), "value3");
	}

	@Test(description = "should not update properties for other applications")
	public void testShouldNotUpdatePropertiesForOtherApplications()
			throws Exception {
		String rootPath = "D:/survive-game";
		Application application = Application.UPGRADE_CLIENT;
		UpgradeInfo upgradeInfo = mock(UpgradeInfo.class);

		component.afterApplicationUpgrade(rootPath, application, upgradeInfo);

		verify(propertiesService, never()).updateProperties(anyString(),
				any(Application.class), any(Properties.class));
	}
	
	@Test(description = "should not update properties if file was not found")
	public void testShouldNotUpdatePropertiesFileNotFound()
			throws Exception {
		String rootPath = "D:/survive-game";
		Application application = Application.CLIENT;
		UpgradeInfo upgradeInfo = mock(UpgradeInfo.class);

		component.setClientProperties(null);
		component.afterApplicationUpgrade(rootPath, application, upgradeInfo);

		verify(propertiesService, never()).updateProperties(anyString(),
				any(Application.class), any(Properties.class));
	}
	
}
