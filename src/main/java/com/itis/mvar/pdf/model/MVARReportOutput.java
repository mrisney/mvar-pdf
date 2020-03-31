package com.itis.mvar.pdf.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder=true)
public class MVARReportOutput {

	private Integer reportId;

	private Integer crashId;

	private String reportMime;

	private byte[] reportBytes;

	private Date lastModified;

	private Integer totalPages;

}
