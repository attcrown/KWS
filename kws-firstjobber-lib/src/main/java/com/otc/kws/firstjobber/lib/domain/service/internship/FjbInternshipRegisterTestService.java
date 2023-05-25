package com.otc.kws.firstjobber.lib.domain.service.internship;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.otc.kws.firstjobber.lib.domain.entity.FjbInternshipRegisterTest;
import com.otc.kws.firstjobber.lib.domain.repository.jpa.JpaFjbInternshipRegisterTestRepository;
import com.otc.kws.firstjobber.lib.domain.service.RepositoryKwsFirstJobberService;

@Service
public class FjbInternshipRegisterTestService extends RepositoryKwsFirstJobberService<JpaFjbInternshipRegisterTestRepository, FjbInternshipRegisterTest, String>
{
	@Autowired
	protected JpaFjbInternshipRegisterTestRepository fjbInternshipRegisterTestRepository;

	
	@Override
	protected JpaFjbInternshipRegisterTestRepository getRepository() 
	{
		return fjbInternshipRegisterTestRepository;
	}

	@Override
	protected String getId(FjbInternshipRegisterTest entity) 
	{
		return entity.getId();
	}
	
	
	public FjbInternshipRegisterTest findLatestTestByInternshipRegisterId(String internshipRegisterId)
	{
		return fjbInternshipRegisterTestRepository.findLatestTest(internshipRegisterId);
	}
}