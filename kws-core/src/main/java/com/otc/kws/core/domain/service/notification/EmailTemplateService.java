package com.otc.kws.core.domain.service.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.otc.kws.core.domain.entity.EmailTemplate;
import com.otc.kws.core.domain.repository.jpa.JpaEmailTemplateRepository;
import com.otc.kws.core.domain.service.RepositoryKwsService;

@Service
public class EmailTemplateService extends RepositoryKwsService<JpaEmailTemplateRepository, EmailTemplate, String>
{
	@Autowired
	protected JpaEmailTemplateRepository emailTemplateRepository;

	
	@Override
	protected JpaEmailTemplateRepository getRepository() 
	{
		return emailTemplateRepository;
	}

	@Override
	protected String getId(EmailTemplate entity) 
	{
		return entity.getId();
	}
}