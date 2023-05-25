package com.otc.kws.core.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.otc.kws.core.domain.entity.Role;
import com.otc.kws.core.domain.repository.jpa.JpaRoleRepository;

@Service
public class RoleService extends CacheKwsService<Role, String>
{
	@Autowired
	protected JpaRoleRepository roleRepository;

	
	@Override
	public List<Role> loadDatas() 
	{
		return roleRepository.findAll();
	}

	@Override
	protected String extractId(Role entity) 
	{
		return entity.getId();
	}
}