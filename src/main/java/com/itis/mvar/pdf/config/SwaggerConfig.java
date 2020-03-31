package com.itis.mvar.pdf.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	private static final String TITLE = "MVAR PDF Download and Merge Service REST API";
	private static final String DESCRIPTION = "API for retrieving MVAR PDF from OC REPORTS";
	private static final String VERSION = "1.0";
	private static final String TERMS_OF_SERVICS = "Terms of service";
	private final String CONTACT = "marc.risney@gmail.com";
	private static final String LICENSE = "Apache License Version 2.0";
	private static final String LICENSE_URL = "https://www.apache.org/licenses/LICENSE-2.0";

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.itis.mvar.pdf.controller")).build()
				.apiInfo(apiInfo());
	}

	private ApiInfo apiInfo() {
		return new ApiInfo(TITLE, DESCRIPTION, VERSION, TERMS_OF_SERVICS, CONTACT, LICENSE, LICENSE_URL);
	}
}