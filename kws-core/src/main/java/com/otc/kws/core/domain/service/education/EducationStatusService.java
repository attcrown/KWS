package com.otc.kws.core.domain.service.education;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.otc.kws.core.domain.constant.value.MasterStatusValue;
import com.otc.kws.core.domain.entity.EducationStatus;
import com.otc.kws.core.domain.repository.jpa.JpaEducationStatusRepository;
import com.otc.kws.core.domain.service.CacheKwsService;

@Service
public class EducationStatusService extends CacheKwsService<EducationStatus, String>
{
	@Autowired
	protected JpaEducationStatusRepository educationStatusRepository;

	
	@Override
	public List<EducationStatus> loadDatas() 
	{
		return educationStatusRepository.findAll();
	}

	@Override
	protected String extractId(EducationStatus entity) 
	{
		return entity.getId();
	}
	
	
	public List<EducationStatus> getAllActive()
	{
		return getAll().stream().filter(e -> e.getStatus() == MasterStatusValue.Active).collect(Collectors.toList());
	}
}