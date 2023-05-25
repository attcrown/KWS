package com.otc.kws.core.gateway.web.api.notification;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.otc.kws.core.domain.service.notification.NotificationSendNotifyCommand;
import com.otc.kws.core.domain.service.notification.NotificationService;
import com.otc.kws.core.gateway.web.api.BaseKwsAPI;
import com.otc.kws.core.gateway.web.api.ResponseMessage;

import lombok.Data;

@RestController
@RequestMapping(BaseKwsAPI.PREFIX_PATH + "/core/notification")
public class NotificationSendNotifyAPI extends BaseKwsAPI
{
	@Autowired
	protected NotificationService notificationService;
	
	
	@PostMapping("/send-notify")
	public ResponseEntity<?> sendNotify(HttpServletRequest request, @RequestBody RequestMessage requestMessage)
	{
		logger.info("### NotificationSendNotifyAPI.sendNotify ###");
		logger.info("requestMessage => " + requestMessage);
		
		NotificationSendNotifyCommand.Response sendNotifyResponse = notificationService.sendNotify(req -> {
			setupServiceRequest(request, req);
			req.setNotificationConfigId(requestMessage.getNotificationConfigId());
			req.setEvent(requestMessage.getEvent());
			req.setVariables(requestMessage.getVariables());
		});
		
		BodyResponseMessage bodyResponseMessage = new BodyResponseMessage();
		bodyResponseMessage.setNotificationConfig(sendNotifyResponse.getNotificationConfig());
		bodyResponseMessage.setLineNotifyResponse(sendNotifyResponse.getLineNotifyResponse());
		
		return replySuccess(request, bodyResponseMessage);
	}
	
	
	@Data
	public static class RequestMessage
	{
		protected String event;
		protected String notificationConfigId;
		protected Map<String, String> variables;
	}
	
	
	public static class BodyResponseMessage extends NotificationSendNotifyCommand.Response implements ResponseMessage.Body
	{
		
	}
}