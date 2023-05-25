package com.otc.kws.core.domain.service.auth;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.otc.kws.core.domain.entity.AuthPolicy;
import com.otc.kws.core.domain.repository.jpa.JpaAuthPolicyRepository;
import com.otc.kws.core.domain.service.CacheKwsService;

@Service
public class AuthPolicyService extends CacheKwsService<AuthPolicy, String>
{
	@Autowired
	protected JpaAuthPolicyRepository authPolicyRepository;

	@Override
	public List<AuthPolicy> loadDatas() 
	{
		return authPolicyRepository.findAll();
	}

	@Override
	protected String extractId(AuthPolicy entity) 
	{
		return entity.getId();
	}
}