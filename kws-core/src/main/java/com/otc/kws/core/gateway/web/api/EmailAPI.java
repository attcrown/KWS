package com.otc.kws.core.gateway.web.api;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.otc.kws.core.domain.service.EmailService;

import lombok.Data;

@RestController
@RequestMapping(BaseKwsAPI.PREFIX_PATH + "/email")
public class EmailAPI extends BaseKwsAPI
{
	@Autowired
	protected EmailService emailService;
	
	
	@PostMapping("/send")
	public ResponseEntity<ResponseMessage<BodyResponseMessage>> sendMessage(HttpServletRequest request, @RequestBody RequestMessage requestMessage)
	{
		logger.info("### " + getClass().getSimpleName() + ".sendMessage ###");
		logger.info("requestMessage => " + requestMessage);
		
		emailService.sendMail(requestMessage.getFromEmail(), requestMessage.getFromName(), requestMessage.getToList(), requestMessage.getSubject(), requestMessage.getContent());
		
		return replySuccess(request);
	}
	
	
	@Data
	public static class RequestMessage
	{
		protected String fromEmail;
		protected String fromName;
		protected List<String> toList;
		protected String subject;
		protected String content;
	}
	
	
	@Data
	public static class BodyResponseMessage extends ResponseMessage.BaseBody
	{
		
	}
}