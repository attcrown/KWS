package com.otc.kws.core.domain.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.otc.kws.core.domain.entity.MasterStatus;
import com.otc.kws.core.domain.repository.jpa.JpaMasterStatusRepository;

@Service
public class MasterStatusService extends CacheKwsService<MasterStatus, String>
{
	@Autowired
	protected JpaMasterStatusRepository masterStatusRepository;

	
	@Override
	public List<MasterStatus> loadDatas() 
	{
		return masterStatusRepository.findAll();
	}

	@Override
	protected String extractId(MasterStatus entity) 
	{
		return entity.getId();
	}
	
	
	public List<MasterStatus> getAllActive()
	{
		return getAll().stream().filter(e -> e.isActive()).collect(Collectors.toList());
	}
}