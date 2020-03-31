package com.itis.mvar.pdf.repository;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DataRepositories {


	@Autowired
	@Qualifier("hawaiiJDBCTemplate")
	protected JdbcTemplate hawaiiJDBCTemplate;

	@Autowired
	@Qualifier("wyomingJDBCTemplate")
	protected JdbcTemplate wyomingJDBCTemplate;

	private Map<Schema, JdbcTemplate> jdbcTemplates;

	@PostConstruct
	private void initJdbcTemplates() {
		jdbcTemplates = new HashMap<>();
		jdbcTemplates.put(Schema.HAWAII, hawaiiJDBCTemplate);
		jdbcTemplates.put(Schema.WYOMING, wyomingJDBCTemplate);

		log.debug("jdbctemplates initiated");
	}

	public JdbcTemplate getJdbcTemplate(Schema schema) {
		return jdbcTemplates.get(schema);
	}

}