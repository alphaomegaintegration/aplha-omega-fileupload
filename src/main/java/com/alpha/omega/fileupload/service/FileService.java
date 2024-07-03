package com.alpha.omega.fileupload.service;

import com.alpha.omega.fileupload.dto.ConvertedFileDTO;
import com.alpha.omega.fileupload.dto.FileDTO;

public interface FileService {
	public ConvertedFileDTO uploadFile(FileDTO fileDTO);
	
}
