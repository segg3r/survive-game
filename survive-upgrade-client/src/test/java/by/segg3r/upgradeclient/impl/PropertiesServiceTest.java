package by.segg3r.upgradeclient.impl;

import static org.testng.Assert.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.Properties;

import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import by.segg3r.upgradeclient.PropertiesDAO;

public class PropertiesServiceTest {

	@Mock
	private PropertiesDAO propertiesDAO;
	@InjectMocks
	private PropertiesServiceImpl reader;

	@BeforeClass
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}

	@AfterMethod
	public void resetMocks() {
		reset(propertiesDAO);
	}

	@Test(description = "should get client version")
	public void testGetClientVersion() throws IOException {
		String rootPath = "D:/survive-game";
		String version = "0.0.1";

		when(
				propertiesDAO
						.getProperty(
								eq("D:/survive-game/client/resources/client.properties"),
								eq("version"), eq("0"))).thenReturn(version);

		assertEquals(reader.getClientVersion(rootPath), version);
	}

	@Test(description = "should get upgrade client version")
	public void testGetUpgradeClientVersion() throws IOException {
		String rootPath = "D:/survive-game";
		String version = "0.0.1";

		when(
				propertiesDAO
						.getProperty(
								eq("D:/survive-game/upgrade-client/resources/upgrade-client.properties"),
								eq("version"), eq("0"))).thenReturn(version);

		assertEquals(reader.getUpgradeClientVersion(rootPath), version);
	}

	@Test(description = "should update client version property")
	public void testUpdateClientVersion() throws IOException {
		String rootPath = "D:/survive-game";
		String version = "0.0.1";
		Properties properties = mock(Properties.class);

		when(
				propertiesDAO
						.getProperties(eq("D:/survive-game/client/resources/client.properties")))
				.thenReturn(properties);

		InOrder order = inOrder(propertiesDAO, properties);

		reader.updateClientVersion(rootPath, version);
		order.verify(properties).setProperty(eq("version"), eq(version));
		order.verify(propertiesDAO).saveProperties(
				eq("D:/survive-game/client/resources/client.properties"),
				eq(properties));
	}

	@Test(description = "should update upgrade client version property")
	public void testUpdateUpgradeClientVersion() throws IOException {
		String rootPath = "D:/survive-game";
		String version = "0.0.1";
		Properties properties = mock(Properties.class);

		when(
				propertiesDAO
						.getProperties(eq("D:/survive-game/upgrade-client/resources/upgrade-client.properties")))
				.thenReturn(properties);

		InOrder order = inOrder(propertiesDAO, properties);

		reader.updateUpgradeClientVersion(rootPath, version);
		order.verify(properties).setProperty(eq("version"), eq(version));
		order.verify(propertiesDAO)
				.saveProperties(
						eq("D:/survive-game/upgrade-client/resources/upgrade-client.properties"),
						eq(properties));
	}

}
