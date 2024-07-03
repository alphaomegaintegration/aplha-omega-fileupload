package com.alpha.omega.fileupload.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class ConvertedFileDTO {
	private String generatedFileName;
	private String fileName;
	private Date uploadedDate;

}
