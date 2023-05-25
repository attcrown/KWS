package com.otc.kws.core.domain.service.notification;

import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.otc.kws.core.domain.service.BaseKwsService;

@Service
public class NotificationService extends BaseKwsService
{
	@Autowired
	protected NotificationSendNotifyCommand notificationSendNotifyCommand;
	
	
	public NotificationSendNotifyCommand.Response sendNotify(Consumer<NotificationSendNotifyCommand.Request> consumer)
	{
		NotificationSendNotifyCommand.Request request = new NotificationSendNotifyCommand.Request();
		consumer.accept(request);
		return sendNotify(request);
	}
	
	public NotificationSendNotifyCommand.Response sendNotify(NotificationSendNotifyCommand.Request request)
	{
		return notificationSendNotifyCommand.sendNotify(request);
	}
}