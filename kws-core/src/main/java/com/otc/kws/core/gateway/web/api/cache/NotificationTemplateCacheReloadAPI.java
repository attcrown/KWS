package com.otc.kws.core.gateway.web.api.cache;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.otc.kws.core.domain.entity.NotificationTemplate;
import com.otc.kws.core.domain.service.notification.NotificationTemplateService;
import com.otc.kws.core.gateway.web.api.BaseKwsAPI;
import com.otc.kws.core.gateway.web.api.CacheReloadKwsAPI;

@RestController
@RequestMapping(BaseKwsAPI.PREFIX_PATH + "/notification-template")
public class NotificationTemplateCacheReloadAPI extends CacheReloadKwsAPI<NotificationTemplate>
{
	@Autowired
	protected NotificationTemplateService notificationTemplateService;

	@Override
	protected List<NotificationTemplate> reloadCache() 
	{
		notificationTemplateService.reloadCache();
		return notificationTemplateService.getAll();
	}
}