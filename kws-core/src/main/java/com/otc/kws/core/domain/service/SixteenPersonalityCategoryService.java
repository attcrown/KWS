package com.otc.kws.core.domain.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.otc.kws.core.domain.constant.value.MasterStatusValue;
import com.otc.kws.core.domain.entity.SixteenPersonalityCategory;
import com.otc.kws.core.domain.repository.jpa.JpaSixteenPersonalityCategoryRepository;

@Service
public class SixteenPersonalityCategoryService extends CacheKwsService<SixteenPersonalityCategory, String>
{
	@Autowired
	protected JpaSixteenPersonalityCategoryRepository sixteenPersonalityCategoryRepository;

	
	@Override
	public List<SixteenPersonalityCategory> loadDatas() 
	{
		return sixteenPersonalityCategoryRepository.findAll();
	}

	@Override
	protected String extractId(SixteenPersonalityCategory entity) 
	{
		return entity.getId();
	}
	
	
	public List<SixteenPersonalityCategory> getAllActive()
	{
		return getAll().stream().filter(e -> e.getStatus() == MasterStatusValue.Active).collect(Collectors.toList());
	}
}