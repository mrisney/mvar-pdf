package com.itis.mvar.pdf.model;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder=true)
public class MVARReportInput {

	private Integer reportId;

	private Integer crashId;

	private String reportName;

	private BigDecimal pageNumber;

	private String reportMime;

	private byte[] reportBytes;

	private Date lastModified;

	private Integer totalPages;

}
