package com.otc.kws.core.domain.service.education;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.otc.kws.core.domain.entity.EducationMajor;
import com.otc.kws.core.domain.exception.DuplicateEntityException;
import com.otc.kws.core.domain.exception.EntityNotFoundException;
import com.otc.kws.core.domain.repository.jpa.JpaEducationMajorRepository;
import com.otc.kws.core.domain.service.RepositoryKwsService;
import com.otc.kws.core.domain.service.education.major.EducationMajorCreateCommand;
import com.otc.kws.core.domain.service.education.major.EducationMajorRemoveCommand;
import com.otc.kws.core.domain.service.education.major.EducationMajorSearchCommand;
import com.otc.kws.core.domain.service.education.major.EducationMajorUpdateCommand;

@Service
public class EducationMajorService extends RepositoryKwsService<JpaEducationMajorRepository, EducationMajor, String>
{
	@Autowired
	protected JpaEducationMajorRepository educationMajorRepository;
	
	@Autowired
	protected EducationMajorSearchCommand educationMajorSearchCommand;
	
	@Autowired
	protected EducationMajorCreateCommand educationMajorCreateCommand;
	
	@Autowired
	protected EducationMajorUpdateCommand educationMajorUpdateCommand;
	
	@Autowired
	protected EducationMajorRemoveCommand educationMajorRemoveCommand;
	
	
	@Override
	protected JpaEducationMajorRepository getRepository() 
	{
		return educationMajorRepository;
	}

	@Override
	protected String getId(EducationMajor entity) 
	{
		return entity.getId();
	}
	
	
	public List<EducationMajor> findAllActive()
	{
		return educationMajorRepository.findAllActive();
	}
	
	@Async
	public CompletableFuture<List<EducationMajor>> findAllActiveAsync()
	{
		return CompletableFuture.completedFuture(findAllActive());
	}
	
	public List<EducationMajor> fetchFlyweightAllActive()
	{
		return educationMajorRepository.fetchFlyweightAllActive();
	}
	
	@Async
	public CompletableFuture<List<EducationMajor>> fetchFlyweightAllActiveAsync()
	{
		return CompletableFuture.completedFuture(fetchFlyweightAllActive());
	}
	
	
	public List<EducationMajor> fetchFlyweightByIds(List<String> ids)
	{
		return educationMajorRepository.fetchFlyweightByIds(ids);
	}
	
	@Async
	public CompletableFuture<List<EducationMajor>> fetchFlyweightByIdsAsync(List<String> ids)
	{
		return CompletableFuture.completedFuture(fetchFlyweightByIds(ids));
	}
	
	
	// ****************************** search ****************************** //
	public EducationMajorSearchCommand.Response search(Consumer<EducationMajorSearchCommand.Request> consumer)
	{
		EducationMajorSearchCommand.Request request = new EducationMajorSearchCommand.Request();
		consumer.accept(request);
		return search(request);
	}
	
	public EducationMajorSearchCommand.Response search(EducationMajorSearchCommand.Request request)
	{
		return educationMajorSearchCommand.search(request);
	}
	// ****************************** search ****************************** //
	
	
	// ****************************** create ****************************** //
	@Transactional
	public EducationMajorCreateCommand.Response create(Consumer<EducationMajorCreateCommand.Request> consumer) throws DuplicateEntityException
	{
		EducationMajorCreateCommand.Request request = new EducationMajorCreateCommand.Request();
		consumer.accept(request);
		return create(request);
	}
	
	@Transactional
	public EducationMajorCreateCommand.Response create(EducationMajorCreateCommand.Request request) throws DuplicateEntityException
	{
		return educationMajorCreateCommand.create(request);
	}
	// ****************************** create ****************************** //
	
	
	// ****************************** update ****************************** //
	@Transactional
	public EducationMajorUpdateCommand.Response update(Consumer<EducationMajorUpdateCommand.Request> consumer) throws EntityNotFoundException
	{
		EducationMajorUpdateCommand.Request request = new EducationMajorUpdateCommand.Request();
		consumer.accept(request);
		return update(request);
	}
	
	@Transactional
	public EducationMajorUpdateCommand.Response update(EducationMajorUpdateCommand.Request request) throws EntityNotFoundException
	{
		return educationMajorUpdateCommand.update(request);
	}
	// ****************************** update ****************************** //
	
	
	// ****************************** remove ****************************** //
	@Transactional
	public EducationMajorRemoveCommand.Response remove(Consumer<EducationMajorRemoveCommand.Request> consumer) throws EntityNotFoundException
	{
		EducationMajorRemoveCommand.Request request = new EducationMajorRemoveCommand.Request();
		consumer.accept(request);
		return remove(request);
	}
	
	@Transactional
	public EducationMajorRemoveCommand.Response remove(EducationMajorRemoveCommand.Request request) throws EntityNotFoundException
	{
		return educationMajorRemoveCommand.remove(request);
	}
	// ****************************** remove ****************************** //
}