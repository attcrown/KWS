package com.otc.kws.core.domain.service.filestore.filesystem;

import org.springframework.stereotype.Component;

import com.otc.kws.core.domain.constant.FileStoreConst;

@Component
public class FileSystemStoreGetURLCommandV1 extends FileSystemStoreGetURLCommand
{
	@Override
	public Response getURL(Request request) 
	{
		String serverURL = fileSystemStoreService.getServerURL();
		String resourceWebPrefixPath = fileSystemStoreService.getResourceWebPrefixPath();
		
		String url = request.getFileURI().replace(FileStoreConst.SCHEME_FS, serverURL + "/" + resourceWebPrefixPath + "/");
		
		Response response = new Response();
		response.setUrl(url);
		
		return response;
	}
}