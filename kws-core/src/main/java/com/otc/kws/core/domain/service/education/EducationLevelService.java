package com.otc.kws.core.domain.service.education;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.otc.kws.core.domain.constant.value.MasterStatusValue;
import com.otc.kws.core.domain.entity.EducationLevel;
import com.otc.kws.core.domain.repository.jpa.JpaEducationLevelRepository;
import com.otc.kws.core.domain.service.CacheKwsService;

@Service
public class EducationLevelService extends CacheKwsService<EducationLevel, String>
{
	@Autowired
	protected JpaEducationLevelRepository educationLevelRepository;

	
	@Override
	public List<EducationLevel> loadDatas() 
	{
		return educationLevelRepository.findAll();
	}

	@Override
	protected String extractId(EducationLevel entity) 
	{
		return entity.getId();
	}
	
	
	public List<EducationLevel> getAllActive()
	{
		return getAll().stream().filter(e -> e.getStatus() == MasterStatusValue.Active).collect(Collectors.toList());
	}
	
	public List<EducationLevel> getByIds(List<String> ids)
	{
		return getAll().
				stream().
				filter(e -> e.getStatus() == MasterStatusValue.Active).
				filter(e -> ids.contains(e.getId())).
				collect(Collectors.toList());
	}
}