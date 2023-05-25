package com.otc.kws.core.domain.service.notification;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.otc.kws.core.domain.entity.NotificationTemplate;
import com.otc.kws.core.domain.repository.jpa.JpaNotificationTemplateRepository;
import com.otc.kws.core.domain.service.CacheKwsService;

@Service
public class NotificationTemplateService extends CacheKwsService<NotificationTemplate, String>
{
	@Autowired
	protected JpaNotificationTemplateRepository notificationTemplateRepository;

	
	@Override
	public List<NotificationTemplate> loadDatas() 
	{
		return notificationTemplateRepository.findAll();
	}

	@Override
	protected String extractId(NotificationTemplate entity) 
	{
		return entity.getId();
	}
}