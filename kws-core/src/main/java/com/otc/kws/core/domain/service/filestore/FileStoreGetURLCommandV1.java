package com.otc.kws.core.domain.service.filestore;

import org.springframework.stereotype.Component;

import com.otc.kws.core.domain.constant.FileStoreConst;

@Component
public class FileStoreGetURLCommandV1 extends FileStoreGetURLCommand
{
	@Override
	public Response getURL(Request request) 
	{
		Response response = new Response();
		
		if(request.getFileURI().startsWith(FileStoreConst.SCHEME_FS)) {
			String url = fileSystemStoreService.getURL(req -> {
				req.copyFrom(request);
				req.setFileURI(request.getFileURI());
			}).getUrl();
			response.setUrl(url);
		}
		
		return response;
	}
}