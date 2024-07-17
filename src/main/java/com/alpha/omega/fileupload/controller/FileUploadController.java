package com.alpha.omega.fileupload.controller;

import com.alpha.omega.fileupload.dto.ConvertedFileDTO;
import com.alpha.omega.fileupload.dto.FileDTO;
import com.alpha.omega.fileupload.service.FileService;
import com.alpha.omega.fileupload.service.FileServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
public class FileUploadController {
	
	@Autowired
	private FileServiceImpl fileService;
	
	@PostMapping("/api/v1/file/upload")
	public ResponseEntity<ConvertedFileDTO> uploadFile(@RequestBody FileDTO fileDTO) {
		return ResponseEntity.ok(fileService.uploadFile(fileDTO));
	}

	@GetMapping("/api/v1/file/{fileName}/base64")
	public ResponseEntity<String> getFileInBase64(@PathVariable("fileName") String fileName) {
		return ResponseEntity.ok(fileService.getFileInBase64(fileName));
	}

	@GetMapping("/api/v1/file/{fileName}/download")
	public ResponseEntity<Resource> downloadFile(@PathVariable("fileName") String fileName) {
		byte[] content = fileService.getFileAsBytes(fileName);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_TYPE, getFileMediaType(fileName))
				.header(HttpHeaders.CONTENT_DISPOSITION, MediaType.APPLICATION_OCTET_STREAM_VALUE)
				.header(HttpHeaders.CONTENT_LENGTH, String.valueOf(content.length))
				.body(new ByteArrayResource(content));
	}

	@DeleteMapping("/api/v1/file/{fileName:.+}")
	public ResponseEntity<Void> deleteFile(@PathVariable("fileName") String fileName) {
		fileService.deleteFile(fileName);
		return ResponseEntity.noContent().build();
	}

	private String getFileMediaType(String fileName) {
		String mediaType;
		String fileExtension = fileName.substring(fileName.lastIndexOf('.') + 1);
		switch (fileExtension.toLowerCase()) {
			case "pdf":
				mediaType = MediaType.APPLICATION_PDF_VALUE;
				break;
			case "png":
				mediaType = MediaType.IMAGE_PNG_VALUE;
				break;
			case "jpeg":
				mediaType = MediaType.IMAGE_JPEG_VALUE;
				break;
			default:
				mediaType = MediaType.TEXT_PLAIN_VALUE;
		}
		return mediaType;
	}

}
