package com.otc.kws.firstjobber.lib.domain.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.otc.kws.core.domain.constant.value.MasterStatusValue;
import com.otc.kws.firstjobber.lib.domain.entity.InternshipType;
import com.otc.kws.firstjobber.lib.domain.repository.jpa.JpaInternshipTypeRepository;

@Service
public class InternshipTypeService extends CacheKwsFirstJobberService<InternshipType, String>
{
	@Autowired
	protected JpaInternshipTypeRepository internshipTypeRepository;

	@Override
	public List<InternshipType> loadDatas() 
	{
		return internshipTypeRepository.findAll();
	}

	@Override
	protected String extractId(InternshipType entity) 
	{
		return entity.getId();
	}
	
	
	public List<InternshipType> getAllActive()
	{
		return getAll().stream().filter(e -> e.getStatus() == MasterStatusValue.Active).collect(Collectors.toList());
	}
}