package com.otc.kws.core.domain.service.filestore;

import org.springframework.beans.factory.annotation.Autowired;

import com.otc.kws.core.domain.entity.FileStoreLog;
import com.otc.kws.core.domain.service.BaseKwsCommand;
import com.otc.kws.core.domain.service.BaseKwsCommandRequest;
import com.otc.kws.core.domain.service.BaseKwsCommandResponse;
import com.otc.kws.core.domain.service.filestore.filesystem.FileSystemStoreService;

import lombok.Data;

public abstract class FileStoreRemoveFileCommand extends BaseKwsCommand
{
	@Autowired
	protected FileStoreService fileStoreService;
	
	@Autowired
	protected FileSystemStoreService fileSystemStoreService;
	
	@Autowired
	protected FileStoreLogService fileStoreLogService;
	
	
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
		protected FileStoreLog removedFileStoreLog;
	}
}