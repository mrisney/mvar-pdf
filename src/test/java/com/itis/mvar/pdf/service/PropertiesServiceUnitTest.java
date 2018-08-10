package com.itis.mvar.pdf.service;

import static org.junit.Assert.*;

import org.junit.Test;

public class PropertiesServiceUnitTest {

	@Test
	public void testDatabaseProperties() {
		PropertiesService propertiesService = new PropertiesService();

		String connection = propertiesService.getProperty("connection");
		String username = propertiesService.getProperty("username");
		String password = propertiesService.getProperty("password");

		assertNotNull(connection);
		assertNotNull(username);
		assertNotNull(password);
	}
}
