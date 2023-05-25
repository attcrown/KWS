package com.otc.kws.core.gateway.web.api.filestore;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.otc.kws.core.domain.constant.value.FileStorageTypeValue;
import com.otc.kws.core.domain.entity.FileStoreLog;
import com.otc.kws.core.domain.model.FileData;
import com.otc.kws.core.domain.service.filestore.FileStorePutFileCommand;
import com.otc.kws.core.domain.service.filestore.FileStoreService;
import com.otc.kws.core.domain.util.JsonUtil;
import com.otc.kws.core.gateway.web.api.BaseKwsAPI;
import com.otc.kws.core.gateway.web.api.ResponseMessage;

import lombok.Data;

@RestController
@RequestMapping(BaseKwsAPI.PREFIX_PATH + "/core/filestore")
public class FileStorePutFileAPI extends BaseKwsAPI
{
	@Autowired
	protected FileStoreService fileStoreService;
	
	
	@PostMapping("/put-file")
	public ResponseEntity<?> putFile(HttpServletRequest request, @ModelAttribute RequestMessage requestMessage) throws Exception
	{
		logger.info("### FileStoreGetUrlAPI.putFile ###");
		logger.info("requestMessage => " + requestMessage);
		
		Map<String, String> variables = JsonUtil.parse(requestMessage.getVariables(), RequestMessage.Variables.class);
		FileData fileData = buildFileData(requestMessage.getFile());
		
		logger.debug("variables => " + variables);
		
		FileStorePutFileCommand.Response putFileResponse = fileStoreService.putFile(req -> {
			setupServiceRequest(request, req);
			req.setModule(requestMessage.getModule());
			req.setFileCategory(requestMessage.getFileCategory());
			req.setFileGroup(requestMessage.getFileGroup());
			req.setFileStorageType(requestMessage.getFileStorageType());
			req.setSeqNo(requestMessage.getSeqNo());
			req.setVariables(variables);
			req.setFileData(fileData);
		});
		
		BodyResponseMessage bodyResponseMessage = new BodyResponseMessage();
		bodyResponseMessage.setUri(putFileResponse.getUri());
		bodyResponseMessage.setUrl(putFileResponse.getUrl());
		bodyResponseMessage.setFileStoreLog(putFileResponse.getFileStoreLog());
		
		return replySuccess(request, bodyResponseMessage);
	}
	
	
	@Data
	public static class RequestMessage
	{
		protected String module;
		protected String fileCategory;
		protected String fileGroup;
		protected FileStorageTypeValue fileStorageType;
		protected int seqNo = 1;
		protected String variables;
		protected MultipartFile file;
		
		
		public static class Variables extends HashMap<String, String>
		{
			
		}
	}
	
	
	@Data
	public static class BodyResponseMessage extends ResponseMessage.BaseBody
	{
		protected String uri;
		protected String url;
		protected FileStoreLog fileStoreLog;
	}
}