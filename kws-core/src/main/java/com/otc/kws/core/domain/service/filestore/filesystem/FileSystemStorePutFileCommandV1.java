package com.otc.kws.core.domain.service.filestore.filesystem;

import java.io.File;
import java.io.FileOutputStream;

import org.springframework.stereotype.Component;

import com.otc.kws.core.domain.constant.FileStoreConst;
import com.otc.kws.core.domain.exception.KwsRuntimeException;

@Component
public class FileSystemStorePutFileCommandV1 extends FileSystemStorePutFileCommand
{
	@Override
	public Response putFile(Request request) 
	{
		Response response = new Response();
		
		String resourcePath = fileSystemStoreService.getFileSystemBaseResourcePath();
		String fileName = request.getFileName().replace("${resource_path}", resourcePath);
		
		File file = new File(fileName);
		File folder = file.getParentFile();
		
		if(! folder.exists()) {
			folder.mkdirs();
			logger.info("create folder: " + folder.getAbsolutePath());
		}
		
		logger.info("fileName => " + fileName);
		
		try (FileOutputStream outputStream = new FileOutputStream(fileName)) {
		    outputStream.write(request.getFileData().getBytes());
		    
		    String uri = (FileStoreConst.SCHEME_FS + fileName).replace(resourcePath + "/", "");
			
			String url = fileSystemStoreService.getURL(req -> {
				req.copyFrom(request);
				req.setFileURI(uri);
			}).getUrl();
			
			response.setUri(uri);
			response.setUrl(url);
		} catch(Exception ex) {
			throw new KwsRuntimeException(ex);
		}
		
		return response;
	}
}