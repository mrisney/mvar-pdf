package com.itis.mvar.pdf.config;

import org.apache.catalina.connector.Connector;
import org.apache.catalina.valves.RemoteIpValve;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TomcatConfig {

	@Value("${ajp.port}")
	int ajpPort;

	@Value("${ajp.enabled}")
	boolean ajpEnabled;

	private static RemoteIpValve createRemoteIpValves() {
		RemoteIpValve remoteIpValve = new RemoteIpValve();
		remoteIpValve.setRemoteIpHeader("x-forwarded-for");
		remoteIpValve.setProtocolHeader("x-forwarded-proto");
		return remoteIpValve;
	}

	@Bean
	@SuppressWarnings("static-method")
	public TomcatServletWebServerFactory servletContainer() {
		TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
		tomcat.addAdditionalTomcatConnectors(createConnector());
		tomcat.addContextValves(createRemoteIpValves());
		return tomcat;
	}

	private Connector createConnector() {
		Connector connector = new Connector("AJP/1.3");
		connector.setPort(ajpPort);
		connector.setSecure(false);
		connector.setScheme("http");
		connector.setAllowTrace(false);
		return connector;
	}
}
