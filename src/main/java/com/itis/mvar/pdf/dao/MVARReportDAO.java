package com.itis.mvar.pdf.dao;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;


import com.itis.mvar.pdf.model.MVARReportInput;
import com.itis.mvar.pdf.model.MVARReportOutput;
import com.itis.mvar.pdf.repository.DataRepositories;
import com.itis.mvar.pdf.repository.Schema;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MVARReportDAO {

	@Autowired
	private DataRepositories dataRepositories;

	public List<MVARReportInput> getMVARReportInputs(int crashId, String schema) throws SQLException {

		List<MVARReportInput> reports = new LinkedList<MVARReportInput>();

		final String sql = "SELECT * FROM mvar_reports_in WHERE crash_id = " + crashId + " ORDER BY page_no ASC";

		log.info("SQL : {}",sql);
		JdbcTemplate jdbcTemplate = dataRepositories.getJdbcTemplate(Schema.fromString(schema));
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);

		for (Map row : rows) {

			MVARReportInput report = MVARReportInput.builder()
					.crashId(crashId)
					.reportName((String) row.get("REPORT_NAME"))
					.pageNumber((BigDecimal) row.get("PAGE_NO"))
					.reportMime((String) row.get("REPORT_MIME"))
					.reportBytes((byte[]) row.get("REPORT_BLOB"))
					.build();

			reports.add(report);
		}

		return reports;
	}

	public static void insertMVARReportOutput(MVARReportOutput reportOutput) throws SQLException {
		
		final String sql = "INSERT INTO mvar_reports\" +\n" + 
				"		 * \"(mvar_report_id,crash_id,report_mime,total_pages,last_modified,report_blob) VALUES\"\n" + 
				"		 * + \"(mvar_reports_seq.nextval,?,?,?,?,?)";

		log.info("SQL : {}",sql);
		
		
		/*
		 * Connection dbConnection = null; PreparedStatement preparedStatement = null;
		 * 
		 * String insertStatementSQL = "INSERT INTO mvar_reports" +
		 * "(mvar_report_id,crash_id,report_mime,total_pages,last_modified,report_blob) VALUES"
		 * + "(mvar_reports_seq.nextval,?,?,?,?,?)";
		 * 
		 * try {
		 * 
		 * dbConnection = getDBConnection();
		 * 
		 * OracleBlob tempBlob = (OracleBlob) dbConnection.createBlob();
		 * 
		 * preparedStatement = dbConnection.prepareStatement(insertStatementSQL);
		 * preparedStatement.setInt(1, reportOutput.getReportId());
		 * preparedStatement.setString(2, reportOutput.getReportMime());
		 * preparedStatement.setInt(3, reportOutput.getTotalPages());
		 * preparedStatement.setTimestamp(4, getCurrentTimeStamp());
		 * 
		 * OutputStream os = tempBlob.setBinaryStream(1);
		 * os.write(reportOutput.getReportBytes());
		 * 
		 * preparedStatement.setBlob(5, tempBlob); // execute insert SQL statement
		 * preparedStatement.execute(); preparedStatement.close();
		 * 
		 * tempBlob.free();
		 * 
		 * log.debug("Record is inserted into mvar_reports table!");
		 * 
		 * } catch (SQLException e) {
		 * 
		 * log.error(e.getMessage()); } catch (IOException e) {
		 * log.error(e.getMessage());
		 * 
		 * } finally {
		 * 
		 * if (preparedStatement != null) { preparedStatement.close(); }
		 * 
		 * if (dbConnection != null) { dbConnection.close(); } }
		 * 
		 */
	}

}
