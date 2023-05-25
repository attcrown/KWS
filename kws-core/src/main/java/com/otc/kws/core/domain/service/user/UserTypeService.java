package com.otc.kws.core.domain.service.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.otc.kws.core.domain.entity.UserType;
import com.otc.kws.core.domain.repository.jpa.JpaUserTypeRepository;
import com.otc.kws.core.domain.service.CacheKwsService;

@Service
public class UserTypeService extends CacheKwsService<UserType, String>
{
	@Autowired
	protected JpaUserTypeRepository userTypeRepository;

	@Override
	public List<UserType> loadDatas() 
	{
		return userTypeRepository.findAll();
	}

	@Override
	protected String extractId(UserType entity) 
	{
		return entity.getId();
	}
}