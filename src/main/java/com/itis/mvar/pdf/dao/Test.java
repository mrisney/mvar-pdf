package com.itis.mvar.pdf.dao;

import com.itis.mvar.pdf.service.PropertiesService;

public class Test {

	private static PropertiesService propertiesService = new PropertiesService();
	public static void main(String[] args) {
		String connection = propertiesService.getProperty("connection");
		String username = propertiesService.getProperty("username");
		String password = propertiesService.getProperty("password");
		System.out.println("connection : " + connection);
		System.out.println("username : " + username);
		System.out.println("password : " + password);
	}
}
