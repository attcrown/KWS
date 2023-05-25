package com.otc.kws.firstjobber.lib.domain.service.internship;

import org.springframework.beans.factory.annotation.Autowired;

import com.otc.kws.core.domain.entity.FileStoreLog;
import com.otc.kws.core.domain.model.FileData;
import com.otc.kws.core.domain.service.BaseKwsCommand;
import com.otc.kws.core.domain.service.BaseKwsCommandRequest;
import com.otc.kws.core.domain.service.BaseKwsCommandResponse;
import com.otc.kws.core.domain.service.filestore.FileStoreService;

import lombok.Data;

public abstract class FjbInternshipUploadHigh5TestImageCommand extends BaseKwsCommand
{
	@Autowired
	protected FileStoreService fileStoreService;
	
	
	public abstract Response uploadHigh5TestImage(Request request);
	
	
	@Data
	public static class Request extends BaseKwsCommandRequest
	{
		protected String internshipId;
		protected FileData testImageFile;
	}
	
	
	@Data
	public static class Response extends BaseKwsCommandResponse
	{
		protected String uri;
		protected String url;
		protected FileStoreLog fileStoreLog;
	}
}