package com.otc.kws.core.domain.service.notification;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.otc.kws.core.domain.ApplicationConfigProvider;
import com.otc.kws.core.domain.entity.NotificationConfig;
import com.otc.kws.core.domain.service.BaseKwsCommand;
import com.otc.kws.core.domain.service.BaseKwsCommandRequest;
import com.otc.kws.core.domain.service.BaseKwsCommandResponse;
import com.otc.kws.core.domain.service.notification.line.LineNotificationService;
import com.otc.kws.core.domain.service.notification.line.SendLineNotifyCommand;

import lombok.Data;

public abstract class NotificationSendNotifyCommand extends BaseKwsCommand
{
	@Autowired
	protected ApplicationConfigProvider applicationConfigProvider;
	
	@Autowired
	protected NotificationConfigService notificationConfigService;
	
	@Autowired
	protected LineNotificationService lineNotificationService;
	
	
	public abstract Response sendNotify(Request request);
	
	
	@Data
	public static class Request extends BaseKwsCommandRequest
	{
		protected String event;
		protected String notificationConfigId;
		protected Map<String, String> variables;
	}
	
	
	@Data
	public static class Response extends BaseKwsCommandResponse
	{
		protected NotificationConfig notificationConfig;
		protected SendLineNotifyCommand.Response lineNotifyResponse;
	}
}