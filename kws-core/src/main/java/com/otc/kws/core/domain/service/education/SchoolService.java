package com.otc.kws.core.domain.service.education;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.otc.kws.core.domain.entity.School;
import com.otc.kws.core.domain.exception.DuplicateEntityException;
import com.otc.kws.core.domain.exception.EntityNotFoundException;
import com.otc.kws.core.domain.repository.jpa.JpaSchoolRepository;
import com.otc.kws.core.domain.service.RepositoryKwsService;
import com.otc.kws.core.domain.service.education.school.SchoolCreateCommand;
import com.otc.kws.core.domain.service.education.school.SchoolRemoveCommand;
import com.otc.kws.core.domain.service.education.school.SchoolSearchCommand;
import com.otc.kws.core.domain.service.education.school.SchoolUpdateCommand;

@Service
public class SchoolService extends RepositoryKwsService<JpaSchoolRepository, School, String>
{
	@Autowired
	protected JpaSchoolRepository schoolRepository;
	
	@Autowired
	protected SchoolSearchCommand schoolSearchCommand;
	
	@Autowired
	protected SchoolCreateCommand schoolCreateCommand;
	
	@Autowired
	protected SchoolUpdateCommand schoolUpdateCommand;
	
	@Autowired
	protected SchoolRemoveCommand schoolRemoveCommand;
	
	
	@Override
	protected JpaSchoolRepository getRepository() 
	{
		return schoolRepository;
	}

	@Override
	protected String getId(School entity) 
	{
		return entity.getId();
	}
	
	
	public List<School> findAllActive()
	{
		return schoolRepository.findAllActive();
	}
	
	@Async
	public CompletableFuture<List<School>> findAllActiveAsync()
	{
		return CompletableFuture.completedFuture(findAllActive());
	}
	
	public List<School> fetchFlyweightAllActive()
	{
		return schoolRepository.fetchFlyweightAllActive();
	}
	
	@Async
	public CompletableFuture<List<School>> fetchFlyweightAllActiveAsync()
	{
		return CompletableFuture.completedFuture(fetchFlyweightAllActive());
	}
	
	
	public List<School> fetchFlyweightByIds(List<String> ids)
	{
		return schoolRepository.fetchFlyweightByIds(ids);
	}
	
	@Async
	public CompletableFuture<List<School>> fetchFlyweightByIdsAsync(List<String> ids)
	{
		return CompletableFuture.completedFuture(fetchFlyweightByIds(ids));
	}
	
	
	// ****************************** search ****************************** //
	public SchoolSearchCommand.Response search(Consumer<SchoolSearchCommand.Request> consumer)
	{
		SchoolSearchCommand.Request request = new SchoolSearchCommand.Request();
		consumer.accept(request);
		return search(request);
	}
	
	public SchoolSearchCommand.Response search(SchoolSearchCommand.Request request)
	{
		return schoolSearchCommand.search(request);
	}
	// ****************************** search ****************************** //
	
	
	// ****************************** create ****************************** //
	@Transactional
	public SchoolCreateCommand.Response create(Consumer<SchoolCreateCommand.Request> consumer) throws DuplicateEntityException
	{
		SchoolCreateCommand.Request request = new SchoolCreateCommand.Request();
		consumer.accept(request);
		return create(request);
	}
	
	@Transactional
	public SchoolCreateCommand.Response create(SchoolCreateCommand.Request request) throws DuplicateEntityException
	{
		return schoolCreateCommand.create(request);
	}
	// ****************************** create ****************************** //
	
	
	// ****************************** update ****************************** //
	@Transactional
	public SchoolUpdateCommand.Response update(Consumer<SchoolUpdateCommand.Request> consumer) throws EntityNotFoundException
	{
		SchoolUpdateCommand.Request request = new SchoolUpdateCommand.Request();
		consumer.accept(request);
		return update(request);
	}
	
	@Transactional
	public SchoolUpdateCommand.Response update(SchoolUpdateCommand.Request request) throws EntityNotFoundException
	{
		return schoolUpdateCommand.update(request);
	}
	// ****************************** update ****************************** //
	
	
	// ****************************** remove ****************************** //
	@Transactional
	public SchoolRemoveCommand.Response remove(Consumer<SchoolRemoveCommand.Request> consumer) throws EntityNotFoundException
	{
		SchoolRemoveCommand.Request request = new SchoolRemoveCommand.Request();
		consumer.accept(request);
		return remove(request);
	}
	
	@Transactional
	public SchoolRemoveCommand.Response remove(SchoolRemoveCommand.Request request) throws EntityNotFoundException
	{
		return schoolRemoveCommand.remove(request);
	}
	// ****************************** update ****************************** //
}