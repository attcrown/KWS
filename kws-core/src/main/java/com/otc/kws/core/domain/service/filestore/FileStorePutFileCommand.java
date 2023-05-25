package com.otc.kws.core.domain.service.filestore;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.otc.kws.core.domain.constant.value.FileStorageTypeValue;
import com.otc.kws.core.domain.entity.FileStoreLog;
import com.otc.kws.core.domain.model.FileData;
import com.otc.kws.core.domain.service.BaseKwsCommand;
import com.otc.kws.core.domain.service.BaseKwsCommandRequest;
import com.otc.kws.core.domain.service.BaseKwsCommandResponse;
import com.otc.kws.core.domain.service.filestore.filesystem.FileSystemStoreService;

import lombok.Data;

public abstract class FileStorePutFileCommand extends BaseKwsCommand
{
	@Autowired
	protected FileSystemStoreService fileSystemStoreService;
	
	@Autowired
	protected FileStoreConfigService fileStoreConfigService;
	
	@Autowired
	protected FileStoreLogService fileStoreLogService;
	
	
	public abstract Response putFile(Request request);
	
	
	@Data
	public static class Request extends BaseKwsCommandRequest
	{
		protected String module;
		protected String fileCategory;
		protected String fileGroup;
		protected FileStorageTypeValue fileStorageType;
		protected Map<String, String> variables;
		protected int seqNo = 1;
		protected FileData fileData;
	}
	
	
	@Data
	public static class Response extends BaseKwsCommandResponse
	{
		protected String uri;
		protected String url;
		protected FileStoreLog fileStoreLog;
	}
}