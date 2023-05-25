package com.otc.kws.core.domain.service.notification;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.otc.kws.core.domain.entity.NotificationChannel;
import com.otc.kws.core.domain.repository.jpa.JpaNotificationChannelRepository;
import com.otc.kws.core.domain.service.CacheKwsService;

@Service
public class NotificationChannelService extends CacheKwsService<NotificationChannel, String>
{
	@Autowired
	protected JpaNotificationChannelRepository notificationChannelRepository;

	
	@Override
	public List<NotificationChannel> loadDatas() 
	{
		return notificationChannelRepository.findAll();
	}

	@Override
	protected String extractId(NotificationChannel entity) 
	{
		return entity.getId();
	}
}