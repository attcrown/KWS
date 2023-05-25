package com.otc.kws.firstjobber.lib.domain.service.internship;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.otc.kws.firstjobber.lib.domain.entity.FjbInternshipRegisterExperience;
import com.otc.kws.firstjobber.lib.domain.repository.jpa.JpaFjbInternshipRegisterExperienceRepository;
import com.otc.kws.firstjobber.lib.domain.service.RepositoryKwsFirstJobberService;

@Service
public class FjbInternshipRegisterExperienceService extends RepositoryKwsFirstJobberService<JpaFjbInternshipRegisterExperienceRepository, FjbInternshipRegisterExperience, String>
{
	@Autowired
	protected JpaFjbInternshipRegisterExperienceRepository fjbInternshipRegisterExperienceRepository;

	
	@Override
	protected JpaFjbInternshipRegisterExperienceRepository getRepository() 
	{
		return fjbInternshipRegisterExperienceRepository;
	}

	@Override
	protected String getId(FjbInternshipRegisterExperience entity) 
	{
		return entity.getId();
	}
	
	
	public List<FjbInternshipRegisterExperience> findByInternshipRegisterId(String internshipRegisterId)
	{
		return fjbInternshipRegisterExperienceRepository.findByInternshipRegisterId(internshipRegisterId);
	}
	
	@Async
	public CompletableFuture<List<FjbInternshipRegisterExperience>> findByInternshipRegisterIdAsync(String internshipRegisterId)
	{
		return CompletableFuture.completedFuture(findByInternshipRegisterId(internshipRegisterId));
	}
	
	
	public List<FjbInternshipRegisterExperience> findByInternshipRegisterIds(Collection<String> internshipRegisterIds)
	{
		return fjbInternshipRegisterExperienceRepository.findByInternshipRegisterIds(internshipRegisterIds);
	}
	
	@Async
	public CompletableFuture<List<FjbInternshipRegisterExperience>> findByInternshipRegisterIdsAsync(Collection<String> internshipRegisterIds)
	{
		return CompletableFuture.completedFuture(findByInternshipRegisterIds(internshipRegisterIds));
	}
}