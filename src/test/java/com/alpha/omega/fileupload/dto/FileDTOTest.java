package com.alpha.omega.fileupload.dto;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import com.alpha.omega.fileupload.dto.FileDTO;
import org.apache.commons.lang3.StringUtils;

public class FileDTOTest {
	
	@Test
	public void testConstructor() {
		FileDTO obj = new FileDTO();
		assertNotNull(obj);
		
	}
	
	@Test
	public void testGettersSetters() {
		FileDTO obj = new FileDTO();
		obj.setFileName("fileName");
		obj.setEncryption("encryption");
		assertTrue(StringUtils.endsWithIgnoreCase(obj.getFileName(), "fileName"));
		assertTrue(StringUtils.endsWithIgnoreCase(obj.getEncryption(),"encryption"));
	
	}
	

}
