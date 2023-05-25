package com.otc.kws.firstjobber.lib.domain.service.internship;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.otc.kws.core.domain.entity.FileStoreLog;
import com.otc.kws.core.domain.model.FileData;
import com.otc.kws.core.domain.service.BaseKwsCommand;
import com.otc.kws.core.domain.service.BaseKwsCommandRequest;
import com.otc.kws.core.domain.service.BaseKwsCommandResponse;
import com.otc.kws.core.domain.service.filestore.FileStoreService;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public abstract class InternshipUploadPortfolioFileCommand extends BaseKwsCommand
{
	@Autowired
	protected FileStoreService fileStoreService;
	
	
	public abstract Response uploadPortfolioFile(Request request);
	
	
	@Data
	public static class Request extends BaseKwsCommandRequest
	{
		protected String internshipId;
		protected List<FileData> portfolioFiles;
	}
	
	
	@Data
	public static class Response extends BaseKwsCommandResponse
	{
		protected List<ReplyItem> replyItems;
		
		
		@Data
		@NoArgsConstructor
		@AllArgsConstructor
		public static class ReplyItem
		{
			protected String uri;
			protected String url;
			protected FileStoreLog fileStoreLog;
		}
	}
}