package com.itis.mvar.pdf.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itis.mvar.pdf.dao.MVARReportDAO;
import com.itis.mvar.pdf.model.MVARReportInput;
import com.itis.mvar.pdf.model.MVARReportOutput;

public class PDFService {

	private static final Logger logger = LoggerFactory.getLogger(PDFService.class);

	public static void merge(int crashId) throws IOException {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			PDFMergerUtility mergePdf = new PDFMergerUtility();

			mergePdf.setDestinationStream(baos);
			List<MVARReportInput> mvarReports = MVARReportDAO.getMVARReportInputs(crashId);
			mergePdf.mergeDocuments(MemoryUsageSetting.setupMainMemoryOnly());
			byte[] reportBytes = baos.toByteArray();

			MVARReportOutput outputReport = MVARReportOutput.builder().crashId(crashId)
					.reportMime("application/pdf;charset=UTF-8").totalPages(mvarReports.size()).reportBytes(reportBytes)
					.build();

			MVARReportDAO.insertMVARReportOutput(outputReport);

			logger.debug("Documents merged, file size = " + reportBytes.length);
		} catch (Exception e) {
			logger.error(e.toString());
		}

	}

	public static ByteArrayOutputStream download(Integer crashId) throws IOException {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			PDFMergerUtility mergePdf = new PDFMergerUtility();
			mergePdf.setDestinationStream(baos);

			List<MVARReportInput> reports = MVARReportDAO.getMVARReportInputs(crashId);

			List<InputStream> sourcesList = new LinkedList<InputStream>();
			for (MVARReportInput report : reports) {
				InputStream is = new ByteArrayInputStream(report.getReportBytes());
				sourcesList.add(is);
			}
			mergePdf.addSources(sourcesList);
			mergePdf.mergeDocuments(MemoryUsageSetting.setupTempFileOnly());
			baos.flush();
			byte[] pdfBytes = baos.toByteArray();
			baos.close();
			logger.debug("Documents merged, file size = " + pdfBytes.length);
		} catch (Exception e) {
			logger.error(e.toString());
		}
		return baos;
	}
}
