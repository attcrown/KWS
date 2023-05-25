package com.otc.kws.core.domain.service.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.otc.kws.core.domain.entity.UserGroup;
import com.otc.kws.core.domain.repository.jpa.JpaUserGroupRepository;
import com.otc.kws.core.domain.service.CacheKwsService;

@Service
public class UserGroupService extends CacheKwsService<UserGroup, String>
{
	@Autowired
	protected JpaUserGroupRepository userGroupRepository;

	@Override
	public List<UserGroup> loadDatas() 
	{
		return userGroupRepository.findAll();
	}

	@Override
	protected String extractId(UserGroup entity) 
	{
		return entity.getId();
	}
}