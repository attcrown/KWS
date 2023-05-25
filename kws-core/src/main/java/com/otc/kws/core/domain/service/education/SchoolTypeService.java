package com.otc.kws.core.domain.service.education;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.otc.kws.core.domain.constant.value.MasterStatusValue;
import com.otc.kws.core.domain.entity.SchoolType;
import com.otc.kws.core.domain.repository.jpa.JpaSchoolTypeRepository;
import com.otc.kws.core.domain.service.CacheKwsService;

@Service
public class SchoolTypeService extends CacheKwsService<SchoolType, String>
{
	@Autowired
	protected JpaSchoolTypeRepository schoolTypeRepository;

	
	@Override
	public List<SchoolType> loadDatas() 
	{
		return schoolTypeRepository.findAll();
	}

	@Override
	protected String extractId(SchoolType entity) 
	{
		return entity.getId();
	}
	
	
	public List<SchoolType> getAllActive()
	{
		return getAll().stream().filter(e -> e.getStatus() == MasterStatusValue.Active).collect(Collectors.toList());
	}
}