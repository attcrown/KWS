package com.otc.kws.core.domain.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.otc.kws.core.domain.constant.value.MasterStatusValue;
import com.otc.kws.core.domain.entity.SixteenPersonalityType;
import com.otc.kws.core.domain.repository.jpa.JpaSixteenPersonalityTypeRepository;

@Service
public class SixteenPersonalityTypeService extends CacheKwsService<SixteenPersonalityType, String>
{
	@Autowired
	protected JpaSixteenPersonalityTypeRepository sixteenPersonalityTypeRepository;

	
	@Override
	public List<SixteenPersonalityType> loadDatas() 
	{
		return sixteenPersonalityTypeRepository.findAll();
	}

	@Override
	protected String extractId(SixteenPersonalityType entity) 
	{
		return entity.getId();
	}
	
	
	public List<SixteenPersonalityType> getAllActive()
	{
		return getAll().stream().filter(e -> e.getStatus() == MasterStatusValue.Active).collect(Collectors.toList());
	}
}