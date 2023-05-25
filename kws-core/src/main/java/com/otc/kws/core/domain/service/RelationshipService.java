package com.otc.kws.core.domain.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.otc.kws.core.domain.constant.value.MasterStatusValue;
import com.otc.kws.core.domain.entity.Relationship;
import com.otc.kws.core.domain.repository.jpa.JpaRelationshipRepository;

@Service
public class RelationshipService extends CacheKwsService<Relationship, String>
{
	@Autowired
	protected JpaRelationshipRepository relationshipRepository;

	
	@Override
	public List<Relationship> loadDatas() 
	{
		return relationshipRepository.findAll();
	}

	@Override
	protected String extractId(Relationship entity) 
	{
		return entity.getId();
	}
	
	
	public List<Relationship> getAllActive()
	{
		return getAll().stream().filter(e -> e.getStatus() == MasterStatusValue.Active).collect(Collectors.toList());
	}
}