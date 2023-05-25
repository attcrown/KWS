package com.otc.kws.core.domain.service.filestore.filesystem;

import org.springframework.beans.factory.annotation.Autowired;

import com.otc.kws.core.domain.service.BaseKwsCommand;
import com.otc.kws.core.domain.service.BaseKwsCommandRequest;
import com.otc.kws.core.domain.service.BaseKwsCommandResponse;

import lombok.Data;

public abstract class FileSystemStoreRemoveFileCommand extends BaseKwsCommand
{
	@Autowired
	protected FileSystemStoreService fileSystemStoreService;
	
	
	public abstract Response removeFile(Request request);
	
	
	@Data
	public static class Request extends BaseKwsCommandRequest
	{
		protected String fileURI;
	}
	
	
	@Data
	public static class Response extends BaseKwsCommandResponse
	{
		protected boolean deleted;
	}
}