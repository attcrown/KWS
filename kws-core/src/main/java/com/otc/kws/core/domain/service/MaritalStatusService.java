package com.otc.kws.core.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.otc.kws.core.domain.entity.MaritalStatus;
import com.otc.kws.core.domain.repository.jpa.JpaMaritalStatusRepository;

@Service
public class MaritalStatusService extends CacheKwsService<MaritalStatus, String>
{
	@Autowired
	protected JpaMaritalStatusRepository maritalStatusRepository;

	
	@Override
	public List<MaritalStatus> loadDatas() 
	{
		return maritalStatusRepository.findAll();
	}

	@Override
	protected String extractId(MaritalStatus entity) 
	{
		return entity.getId();
	}
}