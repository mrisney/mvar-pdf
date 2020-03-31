package com.itis.mvar.pdf.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itis.mvar.pdf.dao.MVARReportDAO;
import com.itis.mvar.pdf.model.MVARReportInput;
import com.itis.mvar.pdf.model.MVARReportOutput;
import com.itis.mvar.pdf.repository.Schema;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PDFService {

	@Autowired
	private MVARReportDAO reportDAO;

	public void merge(int crashId, Schema schema) throws IOException {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		try {
			PDFMergerUtility mergePdf = new PDFMergerUtility();

			mergePdf.setDestinationStream(baos);
			List<MVARReportInput> mvarReports = reportDAO.getMVARReportInputs(crashId,schema.toText());
			mergePdf.mergeDocuments(MemoryUsageSetting.setupMainMemoryOnly());
			byte[] reportBytes = baos.toByteArray();

			MVARReportOutput outputReport = MVARReportOutput.builder()
					.crashId(crashId)
					.reportMime("application/pdf;charset=UTF-8")
					.totalPages(mvarReports.size())
					.reportBytes(reportBytes)
					.build();

			MVARReportDAO.insertMVARReportOutput(outputReport);

			log.info("documents merged, file size = " + reportBytes.length);
		} catch (Exception e) {
			log.error(e.toString());
		}

	}

	public ByteArrayOutputStream download(Integer crashId, String agency) throws IOException {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		try {
			PDFMergerUtility mergePdf = new PDFMergerUtility();
			mergePdf.setDestinationStream(baos);

			List<MVARReportInput> reports = reportDAO.getMVARReportInputs(crashId, agency);

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
			log.info("Documents merged, file size = " + pdfBytes.length);
		} catch (Exception e) {
			log.error(e.toString());
		}
		return baos;
	}
}
