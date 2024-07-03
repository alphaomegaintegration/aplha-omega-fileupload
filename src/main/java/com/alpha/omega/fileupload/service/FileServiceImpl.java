package com.alpha.omega.fileupload.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

import com.amazonaws.util.IOUtils;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alpha.omega.fileupload.dto.ConvertedFileDTO;
import com.alpha.omega.fileupload.dto.FileDTO;
import com.amazonaws.services.s3.AmazonS3;

@Service
public class FileServiceImpl implements FileService{
	private final Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);
	private static final String UPLOAD_FOLDER_NAME = "aoi";
	
	@Autowired
	private AmazonService amazonService;


    private AmazonS3 s3client;

	public ConvertedFileDTO uploadFile(FileDTO fileDTO) {
		ConvertedFileDTO convertedFileDTO = new ConvertedFileDTO();
		convertedFileDTO.setGeneratedFileName(generateFileName(fileDTO));
		convertedFileDTO.setFileName(fileDTO.getFileName());
		File file = convertBase64ToFile(fileDTO.getEncryption(), fileDTO.getFileName());
		this.amazonService.uploadFileToBucket(convertedFileDTO.getFileName(), file, UPLOAD_FOLDER_NAME);
		convertedFileDTO.setUploadedDate(new Date());
		try {
			FileUtils.forceDelete(file);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return convertedFileDTO;
	}

	public String getFileInBase64(String fileName) {
		File file = amazonService.getFileFromBucket(fileName, UPLOAD_FOLDER_NAME);
		try {
			return Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(file));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void deleteFile(String fileName) {
		amazonService.deleteFileFromBucket(fileName, UPLOAD_FOLDER_NAME);
	}

	public byte[] getFileAsBytes(String fileName) {
		InputStream inputStream = amazonService.getFileInputStream(fileName, UPLOAD_FOLDER_NAME);
		try {
			return IOUtils.toByteArray(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new byte[0];
	}

	private File convertBase64ToFile(String base64Content, String filename) {
		byte[] decodedContent = Base64.getDecoder().decode(base64Content.getBytes(StandardCharsets.UTF_8));
		return bytesToFile(decodedContent, filename);
	}

	private File bytesToFile(byte[] content, String fileName) {
		File file = new File(fileName);
		try (FileOutputStream fos = new FileOutputStream(file)) {
			fos.write(content);
		} catch (IOException e) {
			return null;
		}
		return file;
	}

	//Need to replace all special characters with underscores + add timestamp to filename to make it unique
	private String generateFileName(FileDTO fileDTO) {
		String name = fileDTO.getFileName().replaceAll("[^a-zA-Z0-9.-]", "_");
		return (new Date().getTime() + "_" + name);
	}
}
