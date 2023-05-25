package com.otc.kws.core.gateway.web.api.filestore;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.otc.kws.core.domain.entity.FileStoreLog;
import com.otc.kws.core.domain.service.filestore.FileStoreRemoveFileCommand;
import com.otc.kws.core.domain.service.filestore.FileStoreService;
import com.otc.kws.core.gateway.web.api.BaseKwsAPI;
import com.otc.kws.core.gateway.web.api.ResponseMessage;

import lombok.Data;

@RestController
@RequestMapping(BaseKwsAPI.PREFIX_PATH + "/core/filestore")
public class FileStoreRemoveFileAPI extends BaseKwsAPI
{
	@Autowired
	protected FileStoreService fileStoreService;
	
	
	@PostMapping("/remove-file")
	public ResponseEntity<?> removeFile(HttpServletRequest request, @RequestBody RequestMessage requestMessage)
	{
		logger.info("### FileStoreRemoveFileAPI.removeFile ###");
		logger.info("requestMessage => " + requestMessage);
		
		FileStoreRemoveFileCommand.Response removeFileResponse = fileStoreService.removeFile(req -> {
			setupServiceRequest(request, req);
			req.setFileURI(requestMessage.getFileURI());
		});
		
		BodyResponseMessage bodyResponseMessage = new BodyResponseMessage();
		bodyResponseMessage.setDeleted(removeFileResponse.isDeleted());
		bodyResponseMessage.setRemovedFileStoreLog(removeFileResponse.getRemovedFileStoreLog());
		
		return replySuccess(request, bodyResponseMessage);
	}
	
	
	@Data
	public static class RequestMessage
	{
		protected String fileURI;
	}
	
	
	@Data
	public static class BodyResponseMessage extends ResponseMessage.BaseBody
	{
		protected boolean deleted;
		protected FileStoreLog removedFileStoreLog;
	}
}