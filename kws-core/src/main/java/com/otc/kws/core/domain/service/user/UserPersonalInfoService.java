package com.otc.kws.core.domain.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.otc.kws.core.domain.entity.UserPersonalInfo;
import com.otc.kws.core.domain.repository.jpa.JpaUserPersonalInfoRepository;
import com.otc.kws.core.domain.service.RepositoryKwsService;

@Service
public class UserPersonalInfoService extends RepositoryKwsService<JpaUserPersonalInfoRepository, UserPersonalInfo, String>
{
	@Autowired
	protected JpaUserPersonalInfoRepository userPersonalInfoRepository;

	
	@Override
	protected JpaUserPersonalInfoRepository getRepository() 
	{
		return userPersonalInfoRepository;
	}

	@Override
	protected String getId(UserPersonalInfo entity) 
	{
		return entity.getUserId();
	}
}