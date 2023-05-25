package com.otc.kws.firstjobber.lib.domain.service.internship;

import org.springframework.beans.factory.annotation.Autowired;

import com.otc.kws.core.domain.entity.FileStoreLog;
import com.otc.kws.core.domain.model.FileData;
import com.otc.kws.core.domain.service.BaseKwsCommand;
import com.otc.kws.core.domain.service.BaseKwsCommandRequest;
import com.otc.kws.core.domain.service.BaseKwsCommandResponse;
import com.otc.kws.core.domain.service.filestore.FileStoreService;

import lombok.Data;

public abstract class InternshipUploadFormalPhotoCommand extends BaseKwsCommand
{
	@Autowired
	protected FileStoreService fileStoreService;
	
	
	public abstract Response uploadFormalPhoto(Request request);
	
	
	@Data
	public static class Request extends BaseKwsCommandRequest
	{
		protected String internshipId;
		protected FileData formalPhotoFile;
	}
	
	
	@Data
	public static class Response extends BaseKwsCommandResponse
	{
		protected String uri;
		protected String url;
		protected FileStoreLog fileStoreLog;
	}
}