package com.otc.kws.core.domain.service.education;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.otc.kws.core.domain.repository.jpa.JpaSchoolOwnerTypeRepository;
import com.otc.kws.core.domain.constant.value.MasterStatusValue;
import com.otc.kws.core.domain.entity.SchoolOwnerType;
import com.otc.kws.core.domain.service.CacheKwsService;

@Service
public class SchoolOwnerTypeService extends CacheKwsService<SchoolOwnerType, String>
{
	@Autowired
	protected JpaSchoolOwnerTypeRepository schoolOwnerTypeRepository;

	
	@Override
	public List<SchoolOwnerType> loadDatas() 
	{
		return schoolOwnerTypeRepository.findAll();
	}

	@Override
	protected String extractId(SchoolOwnerType entity) 
	{
		return entity.getId();
	}
	
	
	public List<SchoolOwnerType> getAllActive()
	{
		return getAll().stream().filter(e -> e.getStatus() == MasterStatusValue.Active).collect(Collectors.toList());
	}
}