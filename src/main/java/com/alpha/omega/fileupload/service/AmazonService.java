package com.alpha.omega.fileupload.service;

import java.io.File;
import java.io.InputStream;
import java.util.List;

public interface AmazonService {
	 public void uploadFileToBucket(String fileName, File file, String folderToUpload);
	 public void deleteFileFromBucket(String filename, String folderName);
	 public void deleteMultipleFilesFromBucket(List<String> files);
	 public File getFileFromBucket(String filename, String folderName);
	 public InputStream getFileInputStream(String filename, String folderName);

}

