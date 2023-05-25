package com.otc.kws.core.domain.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.otc.kws.core.domain.constant.value.MasterStatusValue;
import com.otc.kws.core.domain.entity.Gender;
import com.otc.kws.core.domain.repository.jpa.JpaGenderRepository;

@Service
public class GenderService extends CacheKwsService<Gender, String>
{
	@Autowired
	protected JpaGenderRepository genderRepository;

	
	@Override
	public List<Gender> loadDatas() 
	{
		return genderRepository.findAll();
	}

	@Override
	protected String extractId(Gender entity) 
	{
		return entity.getId();
	}
	
	
	public List<Gender> getAllActive()
	{
		return getAll().stream().filter(e -> e.getStatus() == MasterStatusValue.Active).collect(Collectors.toList());
	}
}