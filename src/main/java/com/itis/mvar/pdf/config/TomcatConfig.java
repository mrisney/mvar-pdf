package com.itis.mvar.pdf.config;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.ajp.AbstractAjpProtocol;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TomcatConfig {


    @Value("${ajp.port}")
    int ajpPort;

    @Value("${ajp.remoteauthentication}")
    String remoteAuthentication;

    @Value("${ajp.enabled}")
    boolean tomcatAjpEnabled;

    @Bean
    public TomcatServletWebServerFactory servletContainer() {

        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
        if (tomcatAjpEnabled) {
            Connector ajpConnector = new Connector("AJP/1.3");
            ajpConnector.setPort(ajpPort);
            ajpConnector.setSecure(false);
            ajpConnector.setAllowTrace(false);
            ajpConnector.setScheme("http");
            ((AbstractAjpProtocol) ajpConnector.getProtocolHandler()).setSecretRequired(false);
            tomcat.addAdditionalTomcatConnectors(ajpConnector);
        }

        return tomcat;
    }

}