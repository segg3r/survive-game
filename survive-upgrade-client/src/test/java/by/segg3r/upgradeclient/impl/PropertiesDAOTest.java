package by.segg3r.upgradeclient.impl;

import static org.mockito.Mockito.*;
import static org.testng.Assert.*;

import java.io.IOException;
import java.util.Properties;

import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class PropertiesDAOTest {

	@InjectMocks
	private PropertiesDAOImpl dao;
	
	@BeforeClass
	public void initMocks() { 
		MockitoAnnotations.initMocks(this);
	
		dao = spy(dao);
	}
	
	@Test(description = "should return property from properties file")
	public void testGetPropertyWhenPropertyExists() throws IOException {
		String filePath = "D:/file.properties";
		String propertyName = "version";
		String defaultValue = "0";
		String propertyValue = "1";
		
		Properties properties = mock(Properties.class);
		doReturn(properties).when(dao).getProperties(eq(filePath));
		when(properties.getProperty(eq(propertyName))).thenReturn(propertyValue);
		
		assertEquals(dao.getProperty(filePath, propertyName, defaultValue), propertyValue);
	}
	
	@Test(description = "should return default value when Exception occured")
	public void testGetPropertyExceptionOccured() throws IOException {
		String filePath = "D:/file.properties";
		String propertyName = "version";
		String defaultValue = "0";
		
		doThrow(new IOException()).when(dao).getProperties(eq(filePath));
		
		assertEquals(dao.getProperty(filePath, propertyName, defaultValue), defaultValue);
	}
	
}
