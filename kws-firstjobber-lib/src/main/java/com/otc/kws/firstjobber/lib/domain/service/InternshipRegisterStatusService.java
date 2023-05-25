package com.otc.kws.firstjobber.lib.domain.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.otc.kws.core.domain.constant.value.MasterStatusValue;
import com.otc.kws.firstjobber.lib.domain.entity.InternshipRegisterStatus;
import com.otc.kws.firstjobber.lib.domain.repository.jpa.JpaInternshipRegisterStatusRepository;

@Service
public class InternshipRegisterStatusService extends CacheKwsFirstJobberService<InternshipRegisterStatus, String>
{
	@Autowired
	protected JpaInternshipRegisterStatusRepository internshipRegisterStatusRepository;

	@Override
	public List<InternshipRegisterStatus> loadDatas() 
	{
		return internshipRegisterStatusRepository.findAll();
	}

	@Override
	protected String extractId(InternshipRegisterStatus entity) 
	{
		return entity.getId();
	}
	
	
	public List<InternshipRegisterStatus> getAllActive()
	{
		return getAll().
				stream().
				filter(e -> e.getStatus() == MasterStatusValue.Active).
				collect(Collectors.toList());
	}
}