package com.otc.kws.firstjobber.lib.domain.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.otc.kws.firstjobber.lib.domain.entity.Job;
import com.otc.kws.firstjobber.lib.domain.repository.jpa.JpaJobRepository;

@Service
public class JobService extends RepositoryKwsFirstJobberService<JpaJobRepository, Job, String>
{
	@Autowired
	protected JpaJobRepository jobRepository;

	@Override
	protected JpaJobRepository getRepository() 
	{
		return jobRepository;
	}

	@Override
	protected String getId(Job entity) 
	{
		return entity.getId();
	}
	
	
	public List<Job> findAllActive()
	{
		return jobRepository.findAllActive();
	}
	
	@Async
	public CompletableFuture<List<Job>> findAllActiveAsync()
	{
		return CompletableFuture.completedFuture(findAllActive());
	}
}