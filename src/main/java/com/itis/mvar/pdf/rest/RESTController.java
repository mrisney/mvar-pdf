package com.itis.mvar.pdf.rest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itis.mvar.pdf.service.PDFService;

@Path("/")
public class RESTController {

	private static final Logger logger = LoggerFactory.getLogger(RESTController.class);

	@Context
	private HttpServletRequest request;

	@Context
	private HttpServletResponse response;

	@GET
	@Path("download/{crashid}")
	@Produces("application/pdf")
	public void getDownload(@PathParam("crashid") Integer crashId) {

		ByteArrayOutputStream outputStream = null;
		try {
			outputStream = PDFService.download(crashId);
			outputStream.writeTo(response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}

		String fileName = crashId + "-mvar.report.pdf";
		response.addHeader("Content-disposition", "filename=" + fileName);
		response.setContentType("application/pdf");
		response.setContentLength(outputStream.toByteArray().length);
	}

	@GET
	@Path("merge/{crashid}")
	public Response merge(@PathParam("crashid") Integer crashId) {
		try {
			PDFService.merge(crashId);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		String output = "merged  : " + crashId;

		return Response.status(200).entity(output).build();
	}
}
