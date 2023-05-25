package com.otc.kws.core.domain.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.web.multipart.MultipartFile;

import com.otc.kws.core.domain.model.FileData;

public class FileDataUtil 
{
	public static FileData buildFileData(MultipartFile multipartFile) throws Exception
	{
		if(multipartFile == null) {
    		return null;
    	}
    	
    	FileData fileData = new FileData();
    	fileData.setBytes(multipartFile.getBytes());
    	fileData.setSize(multipartFile.getSize());
    	fileData.setContentType(multipartFile.getContentType());
    	fileData.setOriginalFileName(multipartFile.getOriginalFilename());
    	fileData.setFileExtension(FilenameUtils.getExtension(multipartFile.getOriginalFilename()));
    	fileData.setInputStream(multipartFile.getInputStream());
    	
    	return fileData;
	}
	
	public static FileData buildFileData(String base64) throws Exception
	{
		if(base64 == null || base64.trim().isEmpty()) {
    		return null;
    	}
		
		String delims="[,]";
		String[] parts = base64.split(delims);
	    String imageString = parts[1];
	    
	    byte[] imageByteArray = Base64.decodeBase64(imageString);
	    InputStream is = new ByteArrayInputStream(imageByteArray);

	    String delimiter="[/]";
	    String mimeType = URLConnection.guessContentTypeFromStream(is);
        String[] tokens = mimeType.split(delimiter);
        String fileExtension = tokens[1];
    	
    	FileData fileData = new FileData();
    	fileData.setBytes(imageByteArray);
    	fileData.setSize(imageByteArray.length);
    	fileData.setContentType(mimeType);
    	fileData.setOriginalFileName(UUID.randomUUID().toString());
    	fileData.setFileExtension(fileExtension);
    	fileData.setInputStream(is);
    	
    	return fileData;
	}
}