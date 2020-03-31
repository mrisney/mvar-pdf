package com.itis.mvar.pdf.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.itis.mvar.pdf.service.PDFService;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("v1/api")
@Api(tags = "Download and Merge Methods", description = "Methods for retreiving MVAR PDF from Oracle RDBMS")
@Slf4j
public class DownloadController {

	@Autowired
	private PDFService pdfService;

	@RequestMapping(value = "/download/{agency}/{crashid}", method = RequestMethod.GET)
	public ResponseEntity<InputStreamResource> downloadMVAR(@PathVariable("agency") String agency, @PathVariable("crashid") int crashid)
			throws Exception {

		ByteArrayOutputStream buffer = null;
		try {
			buffer = pdfService.download(crashid, agency);

		} catch (IOException e) {
			log.error(e.getMessage());

		}

		byte[] bytes = buffer.toByteArray();

		String fileName = crashid + "-mvar.report.pdf";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentLength(bytes.length);
		headers.setContentType(MediaType.parseMediaType("application/pdf"));
		headers.add("Access-Control-Allow-Origin", "*");
		headers.add("Access-Control-Allow-Methods", "GET, POST, PUT");
		headers.add("Access-Control-Allow-Headers", "Content-Type");
		headers.add("Content-Disposition", "filename=" + fileName);
		headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
		headers.add("Pragma", "no-cache");
		headers.add("Expires", "0");

		ResponseEntity<InputStreamResource> response = new ResponseEntity<InputStreamResource>(
				new InputStreamResource(new ByteArrayInputStream(bytes)), headers, HttpStatus.OK);
		return response;

	}

}
