package com.itis.mvar.pdf.service;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;

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
			List<MVARReportInput> mvarReports = reportDAO.getMVARReportInputs(crashId, schema.toText());
			mergePdf.mergeDocuments(MemoryUsageSetting.setupMainMemoryOnly());
			byte[] reportBytes = baos.toByteArray();

			MVARReportOutput outputReport = MVARReportOutput.builder().crashId(crashId)
					.reportMime("application/pdf;charset=UTF-8").totalPages(mvarReports.size()).reportBytes(reportBytes)
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
				// convert byte array back to BufferedImage
				BufferedImage bImageFromConvert = ImageIO.read(is);
				if (!isBlank(bImageFromConvert)) {
					sourcesList.add(is);
				}
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

	private static Boolean isBlank(BufferedImage pageImage) throws IOException {
		BufferedImage bufferedImage = pageImage;
		long count = 0;
		int height = bufferedImage.getHeight();
		int width = bufferedImage.getWidth();
		Double areaFactor = (width * height) * 0.99;

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				Color c = new Color(bufferedImage.getRGB(x, y));
				if (c.getRed() == c.getGreen() && c.getRed() == c.getBlue() && c.getRed() >= 248) {
					count++;
				}
			}
		}
		if (count >= areaFactor) {
			return true;
		}
		return false;
	}
}
