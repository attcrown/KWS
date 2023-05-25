package com.otc.kws.core.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.otc.kws.core.domain.entity.ResourceAccessControl;
import com.otc.kws.core.domain.repository.jpa.JpaResourceAccessControlRepository;

@Service
public class ResourceAccessControlService extends RepositoryKwsService<JpaResourceAccessControlRepository, ResourceAccessControl, String>
{
	@Autowired
	protected JpaResourceAccessControlRepository resourceAccessControlRepository;

	
	@Override
	protected JpaResourceAccessControlRepository getRepository() 
	{
		return resourceAccessControlRepository;
	}

	@Override
	protected String getId(ResourceAccessControl entity) 
	{
		return entity.getId();
	}
	
	
	public ResourceAccessControl findByAccessToken(String accessToken)
	{
		return resourceAccessControlRepository.findByAccessToken(accessToken);
	}
}