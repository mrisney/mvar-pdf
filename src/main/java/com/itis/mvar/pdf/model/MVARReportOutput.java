package com.itis.mvar.pdf.model;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MVARReportOutput {

	private Integer reportId;

	private Integer crashId;

	private String reportMime;

	private byte[] reportBytes;

	private Date lastModified;

	private Integer totalPages;

}
