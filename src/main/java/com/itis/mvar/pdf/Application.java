package com.itis.mvar.pdf;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class Application {

	public static void main(String[] args) throws Exception {
		new SpringApplicationBuilder(Application.class)
		.logStartupInfo(false)
		.run(args);

	}
}
