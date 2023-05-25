package com.otc.kws.core.domain.service.filestore.filesystem;

import java.io.File;

import org.springframework.stereotype.Component;

import com.otc.kws.core.domain.constant.FileStoreConst;

@Component
public class FileSystemStoreRemoveFileCommandV1 extends FileSystemStoreRemoveFileCommand
{
	@Override
	public Response removeFile(Request request) 
	{
		Response response = new Response();
		
		String resourcePath = fileSystemStoreService.getFileSystemBaseResourcePath();
		String fileName = resourcePath + File.separator + request.getFileURI().replace(FileStoreConst.SCHEME_FS, "");
		logger.info("try to delete file [{}]", fileName);
		
		File file = new File(fileName); 
	    
		if(file.delete()) {
			response.setDeleted(true);
		}
		
		return response;
	}
}