package com.otc.kws.firstjobber.lib.domain.service.employer;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.otc.kws.core.domain.constant.value.MasterStatusValue;
import com.otc.kws.firstjobber.lib.domain.entity.FjbEmployerType;
import com.otc.kws.firstjobber.lib.domain.repository.jpa.JpaFjbEmployerTypeRepository;
import com.otc.kws.firstjobber.lib.domain.service.CacheKwsFirstJobberService;

@Service
public class FjbEmployerTypeService extends CacheKwsFirstJobberService<FjbEmployerType, String>
{
	@Autowired
	protected JpaFjbEmployerTypeRepository fjbEmployerTypeRepository;

	
	@Override
	public List<FjbEmployerType> loadDatas() 
	{
		return fjbEmployerTypeRepository.findAll();
	}

	@Override
	protected String extractId(FjbEmployerType entity) 
	{
		return entity.getId();
	}
	
	
	public List<FjbEmployerType> getAllActive()
	{
		return getAll().stream().filter(e -> e.getStatus() == MasterStatusValue.Active).collect(Collectors.toList());
	}
}