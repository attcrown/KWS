package com.otc.kws.firstjobber.lib.domain.service.applicant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.otc.kws.firstjobber.lib.domain.entity.ApplicantAttribute;
import com.otc.kws.firstjobber.lib.domain.repository.jpa.JpaFjbApplicantAttributeRepository;
import com.otc.kws.firstjobber.lib.domain.service.RepositoryKwsFirstJobberService;

@Service
public class FjbApplicantAttributeService extends RepositoryKwsFirstJobberService<JpaFjbApplicantAttributeRepository, ApplicantAttribute, String>
{
	@Autowired
	protected JpaFjbApplicantAttributeRepository fjbApplicantAttributeRepository;

	
	@Override
	protected JpaFjbApplicantAttributeRepository getRepository() 
	{
		return fjbApplicantAttributeRepository;
	}

	@Override
	protected String getId(ApplicantAttribute entity) 
	{
		return entity.getId();
	}
}