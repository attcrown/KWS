package com.otc.kws.core.domain.service.notification;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.otc.kws.core.domain.entity.NotificationConfig;
import com.otc.kws.core.domain.repository.jpa.JpaNotificationConfigRepository;
import com.otc.kws.core.domain.service.CacheKwsService;

@Service
public class NotificationConfigService extends CacheKwsService<NotificationConfig, String>
{
	@Autowired
	protected JpaNotificationConfigRepository notificationConfigRepository;

	
	@Override
	public List<NotificationConfig> loadDatas() 
	{
		return notificationConfigRepository.findAll();
	}

	@Override
	protected String extractId(NotificationConfig entity) 
	{
		return entity.getId();
	}
	
	
	public NotificationConfig getByEvent(String event)
	{
		return getAll().
				stream().
				filter(e -> e.getEvent().equals(event)).
				findFirst().
				orElse(null);
	}
}