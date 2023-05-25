package com.otc.kws.firstjobber.lib.domain.service.applicant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.otc.kws.firstjobber.lib.domain.entity.Applicant;
import com.otc.kws.firstjobber.lib.domain.repository.jpa.JpaFjbApplicantRepository;
import com.otc.kws.firstjobber.lib.domain.service.RepositoryKwsFirstJobberService;

@Service
public class FjbApplicantService extends RepositoryKwsFirstJobberService<JpaFjbApplicantRepository, Applicant, String>
{
	@Autowired
	protected JpaFjbApplicantRepository fjbApplicantRepository;

	@Override
	protected JpaFjbApplicantRepository getRepository() 
	{
		return fjbApplicantRepository;
	}

	@Override
	protected String getId(Applicant entity) 
	{
		return entity.getId();
	}
}