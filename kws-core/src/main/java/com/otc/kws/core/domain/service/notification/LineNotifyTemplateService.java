package com.otc.kws.core.domain.service.notification;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.otc.kws.core.domain.entity.LineNotifyTemplate;
import com.otc.kws.core.domain.repository.jpa.JpaLineNotifyTemplateRepository;
import com.otc.kws.core.domain.service.RepositoryKwsService;

@Service
public class LineNotifyTemplateService extends RepositoryKwsService<JpaLineNotifyTemplateRepository, LineNotifyTemplate, String>
{
	@Autowired
	protected JpaLineNotifyTemplateRepository lineNotifyTemplateRepository;

	
	@Override
	protected JpaLineNotifyTemplateRepository getRepository() 
	{
		return lineNotifyTemplateRepository;
	}

	@Override
	protected String getId(LineNotifyTemplate entity) 
	{
		return entity.getId();
	}
	
	
	public List<LineNotifyTemplate> findAllActiveByNotificationConfigId(String notificationConfigId)
	{
		return lineNotifyTemplateRepository.findAllActiveByNotificationConfigId(notificationConfigId);
	}
	
	@Async
	public CompletableFuture<List<LineNotifyTemplate>> findAllActiveByNotificationConfigIdAsync(String notificationConfigId)
	{
		return CompletableFuture.completedFuture(findAllActiveByNotificationConfigId(notificationConfigId));
	}
}