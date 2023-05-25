package com.otc.kws.core.domain.service.notification.line;

import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.otc.kws.core.domain.service.BaseKwsService;

@Service
public class LineNotificationService extends BaseKwsService
{
	@Autowired
	protected SendLineNotifyCommand sendLineNotifyCommand;
	
	
	// ****************************** sendLineNotify ****************************** //
	public SendLineNotifyCommand.Response sendLineNotify(Consumer<SendLineNotifyCommand.Request> consumer)
	{
		SendLineNotifyCommand.Request request = new SendLineNotifyCommand.Request();
		consumer.accept(request);
		return sendLineNotify(request);
	}
	
	public SendLineNotifyCommand.Response sendLineNotify(SendLineNotifyCommand.Request request)
	{
		return sendLineNotifyCommand.sendLineNotify(request);
	}
	// ****************************** sendLineNotify ****************************** //
}