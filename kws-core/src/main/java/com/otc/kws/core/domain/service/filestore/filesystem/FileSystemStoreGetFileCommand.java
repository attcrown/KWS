package com.otc.kws.core.domain.service.filestore.filesystem;

import com.otc.kws.core.domain.service.BaseKwsCommand;
import com.otc.kws.core.domain.service.BaseKwsCommandRequest;
import com.otc.kws.core.domain.service.BaseKwsCommandResponse;

import lombok.Data;

public abstract class FileSystemStoreGetFileCommand extends BaseKwsCommand
{
	public abstract Response getFile(Request request);
	
	
	@Data
	public static class Request extends BaseKwsCommandRequest
	{
		
	}
	
	
	@Data
	public static class Response extends BaseKwsCommandResponse
	{
		
	}
}