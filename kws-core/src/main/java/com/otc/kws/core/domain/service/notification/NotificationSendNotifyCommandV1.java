package com.otc.kws.core.domain.service.notification;

import org.springframework.stereotype.Component;

import com.otc.kws.core.domain.constant.NotificationConst;
import com.otc.kws.core.domain.entity.NotificationConfig;
import com.otc.kws.core.domain.service.notification.line.SendLineNotifyCommand;

@Component
public class NotificationSendNotifyCommandV1 extends NotificationSendNotifyCommand
{
	@Override
	public Response sendNotify(Request request) 
	{
		Response response = new Response();
		
		NotificationConfig notificationConfig = null;
		
		if(notificationConfig == null && request.getNotificationConfigId() != null) {
			notificationConfig = notificationConfigService.getById(request.getNotificationConfigId());
		}
		
		if(notificationConfig == null && request.getEvent() != null) {
			notificationConfig = notificationConfigService.getByEvent(request.getEvent());
		}
		
		if(notificationConfig != null) {
			NotificationConfig _notificationConfig = notificationConfig;
			response.setNotificationConfig(_notificationConfig);
			
			SendLineNotifyCommand.Response lineNotifyResponse = lineNotificationService.sendLineNotify(req -> {
				if(request.getVariables() != null) {
					if(request.getVariables().get(NotificationConst.VAR_SERVICE) == null) {
						request.getVariables().put(NotificationConst.VAR_SERVICE, applicationConfigProvider.getApplicationName());
					}
					
					if(request.getVariables().get(NotificationConst.VAR_WEB_ENV) == null) {
						request.getVariables().put(NotificationConst.VAR_WEB_ENV, applicationConfigProvider.getWebEnvirontment());
					}
					
					if(request.getVariables().get(NotificationConst.VAR_DB_ENV) == null) {
						request.getVariables().put(NotificationConst.VAR_DB_ENV, applicationConfigProvider.getDatabaseEnvirontment());
					}
				}
				
				req.copyFrom(request);
				req.setNotificationConfig(_notificationConfig);
				req.setVariables(request.getVariables());
			});
			
			response.setLineNotifyResponse(lineNotifyResponse);
		}
		
		return response;
	}
}