package com.itis.mvar.pdf.service;

import static org.junit.Assert.*;
import com.itis.mvar.pdf.service.PropertiesService;
import org.junit.Test;

public class ServicesUnitTest {

	@Test
	public void testPropertiesService() {
		
		PropertiesService propertiesService = new PropertiesService();

		String connection = propertiesService.getProperty("connection");
		String username = propertiesService.getProperty("username");
		String password = propertiesService.getProperty("password");

		assertNotNull(connection);
		assertNotNull(username);
		assertNotNull(password);
	}
	
	@Test
	public void testPDFService() {
		
	}
}
