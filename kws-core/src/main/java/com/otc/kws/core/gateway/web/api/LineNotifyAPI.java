package com.otc.kws.core.gateway.web.api;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.Data;

import org.springframework.web.bind.annotation.RestController;

import com.otc.kws.core.domain.service.notification.line.LineNotifySender;

@RestController
@RequestMapping(BaseKwsAPI.PREFIX_PATH + "/line/notify")
public class LineNotifyAPI extends BaseKwsAPI
{
	@Autowired
	protected LineNotifySender lineNotifyService;
	
	
	@PostMapping("/send")
	public ResponseEntity sendLineNotify(HttpServletRequest request, @RequestBody RequestMessage requestMessage)
	{
		logger.info("### " + getClass().getSimpleName() + ".sendLineNotify ###");
		logger.info("requestMessage => " + requestMessage);
		
		lineNotifyService.sendNotify(requestMessage.getAccessToken(), requestMessage.getMessage());
		
		return replySuccess(request);
	}
	
	
	@Data
	public static class RequestMessage
	{
		protected String accessToken;
		protected LineNotifySender.Message message;
	}
}