package com.itis.mvar.pdf.dao;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itis.mvar.pdf.model.MVARReportInput;
import com.itis.mvar.pdf.model.MVARReportOutput;
import com.itis.mvar.pdf.service.PropertiesService;

import oracle.jdbc.OracleBlob;

public class MVARReportDAO {

	private static final Logger logger = LoggerFactory.getLogger(MVARReportDAO.class);
	
	private static final PropertiesService propertiesService = new PropertiesService();
	
	private static final String DB_DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String DB_CONNECTION = propertiesService.getProperty("connection");
	private static final String DB_USER = propertiesService.getProperty("username");
	private static final String DB_PASSWORD = propertiesService.getProperty("password");

	public static List<MVARReportInput> getMVARReportInputs(int crashId) throws SQLException {

		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;

		List<MVARReportInput> reports = new LinkedList<MVARReportInput>();

		final String selectSQL = "SELECT * FROM mvar_reports_in WHERE crash_id = ? ORDER BY page_no ASC";

		try {
			dbConnection = getDBConnection();
			preparedStatement = dbConnection.prepareStatement(selectSQL);
			preparedStatement.setInt(1, crashId);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				MVARReportInput report = MVARReportInput.builder().crashId(crashId)
						.reportName(rs.getString("REPORT_NAME")).pageNumber(rs.getInt("PAGE_NO"))
						.reportMime(rs.getString("REPORT_MIME")).reportBytes(rs.getBytes("REPORT_BLOB")).build();

				reports.add(report);
			}

		} finally {
			if (preparedStatement != null) {
				preparedStatement.close();
			}

			if (dbConnection != null) {
				dbConnection.close();
			}
		}

		return reports;
	}

	public static void insertMVARReportOutput(MVARReportOutput reportOutput) throws SQLException {

		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;

		String insertStatementSQL = "INSERT INTO mvar_reports"
				+ "(mvar_report_id,crash_id,report_mime,total_pages,last_modified,report_blob) VALUES"
				+ "(mvar_reports_seq.nextval,?,?,?,?,?)";

		try {

			dbConnection = getDBConnection();

			OracleBlob tempBlob = (OracleBlob) dbConnection.createBlob();

			preparedStatement = dbConnection.prepareStatement(insertStatementSQL);
			preparedStatement.setInt(1, reportOutput.getReportId());
			preparedStatement.setString(2, reportOutput.getReportMime());
			preparedStatement.setInt(3, reportOutput.getTotalPages());
			preparedStatement.setTimestamp(4, getCurrentTimeStamp());

			OutputStream os = tempBlob.setBinaryStream(1);
			os.write(reportOutput.getReportBytes());

			preparedStatement.setBlob(5, tempBlob);
			// execute insert SQL statement
			preparedStatement.execute();
			preparedStatement.close();

			tempBlob.free();

			logger.debug("Record is inserted into mvar_reports table!");

		} catch (SQLException e) {

			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());

		} finally {

			if (preparedStatement != null) {
				preparedStatement.close();
			}

			if (dbConnection != null) {
				dbConnection.close();
			}
		}
	}

	private static Connection getDBConnection() {

		Connection dbConnection = null;

		try {

			Class.forName(DB_DRIVER);

		} catch (ClassNotFoundException e) {
			logger.error(e.getMessage());
		}

		try {
			dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
			return dbConnection;

		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
		return dbConnection;
	}

	private static java.sql.Timestamp getCurrentTimeStamp() {

		java.util.Date today = new java.util.Date();
		return new java.sql.Timestamp(today.getTime());
	}
}
