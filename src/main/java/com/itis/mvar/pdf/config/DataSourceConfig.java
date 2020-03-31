package com.itis.mvar.pdf.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class DataSourceConfig {

	@Bean
	@Primary
	@ConfigurationProperties("app.datasource.wyoming")
	public DataSourceProperties wyomingDataSourceProperties() {
		return new DataSourceProperties();
	}

	@Primary
	@Bean
	@Qualifier("wyomingDB")
	@ConfigurationProperties(prefix = "app.datasource.wyoming")
	public HikariDataSource wyomingDataSource() {
		return wyomingDataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();

	}

	@Bean(name = "wyomingJDBCTemplate")
	public JdbcTemplate wyomingJDBCTemplate(@Qualifier("wyomingDB") DataSource wyomingDB) {
		return new JdbcTemplate(wyomingDB);
	}

	@Bean
	@ConfigurationProperties("app.datasource.hawaii")
	public DataSourceProperties hawaiiDataSourceProperties() {
		return new DataSourceProperties();
	}


	@Bean
	@Qualifier("hawaiiDB")
	@ConfigurationProperties(prefix = "app.datasource.hawaii")
	public HikariDataSource hawaiiDataSource() {
		return hawaiiDataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();

	}

	@Bean(name = "hawaiiJDBCTemplate")
	public JdbcTemplate hawaiiJDBCTemplate(@Qualifier("hawaiiDB") DataSource hawaiiDB) {
		return new JdbcTemplate(hawaiiDB);
	}

}