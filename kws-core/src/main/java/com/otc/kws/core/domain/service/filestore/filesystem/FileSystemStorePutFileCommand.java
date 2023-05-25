package com.otc.kws.core.domain.service.filestore.filesystem;

import org.springframework.beans.factory.annotation.Autowired;

import com.otc.kws.core.domain.model.FileData;
import com.otc.kws.core.domain.service.BaseKwsCommand;
import com.otc.kws.core.domain.service.BaseKwsCommandRequest;
import com.otc.kws.core.domain.service.BaseKwsCommandResponse;

import lombok.Data;

public abstract class FileSystemStorePutFileCommand extends BaseKwsCommand
{
	@Autowired
	protected FileSystemStoreService fileSystemStoreService;
	
	
	public abstract Response putFile(Request request);
	
	
	@Data
	public static class Request extends BaseKwsCommandRequest
	{
		protected String fileName;
		protected FileData fileData;
	}
	
	
	@Data
	public static class Response extends BaseKwsCommandResponse
	{
		protected String uri;
		protected String url;
	}
}