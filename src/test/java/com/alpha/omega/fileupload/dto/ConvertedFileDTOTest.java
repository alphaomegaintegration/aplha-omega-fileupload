package com.alpha.omega.fileupload.dto;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

public class ConvertedFileDTOTest {
	@Test
	public void testConstructor() {
		ConvertedFileDTO obj = new  ConvertedFileDTO();
		assertNotNull(obj);
		
	}
	
	@Test
	public void testGettersSetters() {
		 ConvertedFileDTO obj = new  ConvertedFileDTO();
		obj.setGeneratedFileName("orgFileName");
		obj.setFileName("fileName");
		obj.setUploadedDate(new Date());
		assertTrue(StringUtils.endsWithIgnoreCase(obj.getGeneratedFileName(), "orgFileName"));
		assertTrue(StringUtils.endsWithIgnoreCase(obj.getFileName(),"fileName"));
		assertNotNull(obj.getUploadedDate());
	}
	

}
