package com.itis.mvar.pdf.service;

import java.io.FileInputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertiesService {

	private static final Logger logger = LoggerFactory.getLogger(PropertiesService.class);

	private Properties appProps;
	private String rootPath;

	public PropertiesService() {

		try {
			rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
			String appConfigPath = rootPath + "app.properties";
			Properties defaultProps = new Properties();
			appProps = new Properties(defaultProps);
			appProps.load(new FileInputStream(appConfigPath));
		} catch (Exception e) {
			logger.error("error loading properties file : " + e.getMessage());
		}
	}

	public String getProperty(String key) {
		return appProps.getProperty(key);
	}
}
