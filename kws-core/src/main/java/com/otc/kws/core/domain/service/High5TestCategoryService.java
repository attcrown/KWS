package com.otc.kws.core.domain.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.otc.kws.core.domain.constant.value.MasterStatusValue;
import com.otc.kws.core.domain.entity.High5TestCategory;
import com.otc.kws.core.domain.repository.jpa.JpaHigh5TestCategoryRepository;

@Service
public class High5TestCategoryService extends CacheKwsService<High5TestCategory, String>
{
	@Autowired
	protected JpaHigh5TestCategoryRepository high5TestCategoryRepository;

	
	@Override
	public List<High5TestCategory> loadDatas() 
	{
		return high5TestCategoryRepository.findAll();
	}

	@Override
	protected String extractId(High5TestCategory entity) 
	{
		return entity.getId();
	}
	
	
	public List<High5TestCategory> getAllActive()
	{
		return getAll().stream().filter(e -> e.getStatus() == MasterStatusValue.Active).collect(Collectors.toList());
	}
}