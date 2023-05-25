package com.otc.kws.core.domain.service.notification.line;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.otc.kws.core.domain.ApplicationConfigProvider;
import com.otc.kws.core.domain.entity.LineNotifyTemplate;
import com.otc.kws.core.domain.entity.NotificationConfig;
import com.otc.kws.core.domain.service.BaseKwsCommand;
import com.otc.kws.core.domain.service.BaseKwsCommandRequest;
import com.otc.kws.core.domain.service.BaseKwsCommandResponse;
import com.otc.kws.core.domain.service.notification.LineNotifyChannelService;
import com.otc.kws.core.domain.service.notification.LineNotifyTemplateService;
import com.otc.kws.core.domain.service.notification.NotificationConfigService;

import lombok.Data;

public abstract class SendLineNotifyCommand extends BaseKwsCommand
{
	@Autowired
	protected ApplicationConfigProvider applicationConfigProvider;
	
	@Autowired
	protected NotificationConfigService notificationConfigService;
	
	@Autowired
	protected LineNotifyChannelService lineNotifyChannelService;
	
	@Autowired
	protected LineNotifyTemplateService lineNotifyTemplateService;
	
	@Autowired
	protected LineNotifySender lineNotifySender;
	
	
	public abstract Response sendLineNotify(Request request);
	
	
	@Data
	public static class Request extends BaseKwsCommandRequest
	{
		protected String notificationConfigId = null;
		protected NotificationConfig notificationConfig;
		protected Map<String, String> variables;
	}
	
	
	@Data
	public static class Response extends BaseKwsCommandResponse
	{
		protected NotificationConfig notificationConfig;
		protected List<ResponseItem> responseItems;
		
		@Data
		public static class ResponseItem
		{
			protected LineNotifyTemplate lineNotifyTemplate;
			protected String rawResponse;
		}
	}
}