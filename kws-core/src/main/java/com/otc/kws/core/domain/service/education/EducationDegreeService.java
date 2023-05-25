package com.otc.kws.core.domain.service.education;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.otc.kws.core.domain.entity.EducationDegree;
import com.otc.kws.core.domain.repository.jpa.JpaEducationDegreeRepository;
import com.otc.kws.core.domain.service.BaseKwsService;

@Service
public class EducationDegreeService extends BaseKwsService
{
	@Autowired
	protected JpaEducationDegreeRepository educationDegreeRepository;
	
	
	public List<EducationDegree> findAll()
	{
		return educationDegreeRepository.findAll();
	}
	
	@Async
	public CompletableFuture<List<EducationDegree>> findAllAsync()
	{
		return CompletableFuture.completedFuture(educationDegreeRepository.findAll());
	}
	
	public EducationDegree findById(String id)
	{
		return educationDegreeRepository.findById(id).orElse(null);
	}
	
	@Async
	public CompletableFuture<EducationDegree> findByIdAsync(String id)
	{
		return CompletableFuture.completedFuture(educationDegreeRepository.findById(id).orElse(null));
	}
	
	public List<EducationDegree> findAllActive()
	{
		return educationDegreeRepository.findAll();
	}
	
	@Async
	public CompletableFuture<List<EducationDegree>> findAllActiveAsync()
	{
		return CompletableFuture.completedFuture(educationDegreeRepository.findAllActive());
	}
	
	public List<EducationDegree> fetchFlyweightAllActive()
	{
		return educationDegreeRepository.fetchFlyweightAllActive();
	}
	
	@Async
	public CompletableFuture<List<EducationDegree>> fetchFlyweightAllActiveAsync()
	{
		return CompletableFuture.completedFuture(educationDegreeRepository.fetchFlyweightAllActive());
	}
}