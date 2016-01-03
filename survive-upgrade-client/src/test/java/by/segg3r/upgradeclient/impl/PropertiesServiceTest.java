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

import by.segg3r.Application;
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

	@Test(description = "should get application version")
	public void testGetApplicationVersion() throws IOException {
		String rootPath = "D:/survive-game";
		String version = "0.0.1";

		when(
				propertiesDAO
						.getProperty(
								eq("D:/survive-game/client/resources/client.properties"),
								eq("version"), eq("0"))).thenReturn(version);

		assertEquals(reader.getApplicationVersion(rootPath, Application.CLIENT), version);
	}

	@Test(description = "should update application version property")
	public void testUpdateClientVersion() throws IOException {
		String rootPath = "D:/survive-game";
		String version = "0.0.1";
		Properties properties = mock(Properties.class);

		when(
				propertiesDAO
						.getProperties(eq("D:/survive-game/client/resources/client.properties")))
				.thenReturn(properties);

		InOrder order = inOrder(propertiesDAO, properties);

		reader.updateApplicationVersion(rootPath, Application.CLIENT, version);
		order.verify(properties).setProperty(eq("version"), eq(version));
		order.verify(propertiesDAO).saveProperties(
				eq("D:/survive-game/client/resources/client.properties"),
				eq(properties));
	}

}
