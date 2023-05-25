package com.otc.kws.core.gateway.web.api.filestore;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.otc.kws.core.domain.service.filestore.FileStoreService;
import com.otc.kws.core.gateway.web.api.BaseKwsAPI;
import com.otc.kws.core.gateway.web.api.ResponseMessage;

import lombok.Data;

@RestController
@RequestMapping(BaseKwsAPI.PREFIX_PATH + "/core/filestore")
public class FileStoreGetUrlAPI extends BaseKwsAPI
{
	@Autowired
	protected FileStoreService fileStoreService;
	
	
	@PostMapping("/get-url")
	public ResponseEntity<?> getURL(HttpServletRequest request, @RequestBody RequestMessage requestMessage)
	{
		logger.info("### FileStoreGetUrlAPI.getURL ###");
		logger.info("requestMessage => " + requestMessage);
		
		String url = fileStoreService.getURL(req -> {
			setupServiceRequest(request, req);
			req.setFileURI(requestMessage.getFileURI());
		}).getUrl();
		
		logger.info("url => " + url);
		
		BodyResponseMessage bodyResponseMessage = new BodyResponseMessage();
		bodyResponseMessage.setUrl(url);
		
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
		protected String url;
	}
}