package com.otc.kws.firstjobber.lib.domain.service.employer;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.otc.kws.firstjobber.lib.domain.entity.FjbEmployerUser;
import com.otc.kws.firstjobber.lib.domain.repository.jpa.JpaFjbEmployerUserRepository;
import com.otc.kws.firstjobber.lib.domain.service.RepositoryKwsFirstJobberService;

@Service
public class FjbEmployerUserService extends RepositoryKwsFirstJobberService<JpaFjbEmployerUserRepository, FjbEmployerUser, String>
{
	@Autowired
	protected JpaFjbEmployerUserRepository fjbEmployerUserRepository;
	

	@Override
	protected JpaFjbEmployerUserRepository getRepository() 
	{
		return fjbEmployerUserRepository;
	}

	@Override
	protected String getId(FjbEmployerUser entity) 
	{
		return entity.getId();
	}
	
	
	public List<FjbEmployerUser> findByEmployerId(String employerId)
	{
		return fjbEmployerUserRepository.findByEmployerId(employerId);
	}
	
	@Async
	public CompletableFuture<List<FjbEmployerUser>> findByEmployerIdAsync(String employerId)
	{
		return CompletableFuture.completedFuture(findByEmployerId(employerId));
	}
}