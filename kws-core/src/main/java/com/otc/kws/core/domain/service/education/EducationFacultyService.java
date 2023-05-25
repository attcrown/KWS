package com.otc.kws.core.domain.service.education;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.otc.kws.core.domain.entity.EducationFaculty;
import com.otc.kws.core.domain.exception.DuplicateEntityException;
import com.otc.kws.core.domain.exception.EntityNotFoundException;
import com.otc.kws.core.domain.repository.jpa.JpaEducationFacultyRepository;
import com.otc.kws.core.domain.service.RepositoryKwsService;
import com.otc.kws.core.domain.service.education.faculty.EducationFacultyCreateCommand;
import com.otc.kws.core.domain.service.education.faculty.EducationFacultyRemoveCommand;
import com.otc.kws.core.domain.service.education.faculty.EducationFacultySearchCommand;
import com.otc.kws.core.domain.service.education.faculty.EducationFacultyUpdateCommand;

@Service
public class EducationFacultyService extends RepositoryKwsService<JpaEducationFacultyRepository, EducationFaculty, String>
{
	@Autowired
	protected JpaEducationFacultyRepository educationFacultyRepository;
	
	@Autowired
	protected EducationFacultySearchCommand educationFacultySearchCommand;
	
	@Autowired
	protected EducationFacultyCreateCommand educationFacultyCreateCommand;
	
	@Autowired
	protected EducationFacultyUpdateCommand educationFacultyUpdateCommand;
	
	@Autowired
	protected EducationFacultyRemoveCommand educationFacultyRemoveCommand;
	
	
	@Override
	protected JpaEducationFacultyRepository getRepository() 
	{
		return educationFacultyRepository;
	}

	@Override
	protected String getId(EducationFaculty entity) 
	{
		return entity.getId();
	}
	
	
	public List<EducationFaculty> findAllActive()
	{
		return educationFacultyRepository.findAllActive();
	}
	
	@Async
	public CompletableFuture<List<EducationFaculty>> findAllActiveAsync()
	{
		return CompletableFuture.completedFuture(findAllActive());
	}
	
	public List<EducationFaculty> fetchFlyweightAllActive()
	{
		return educationFacultyRepository.fetchFlyweightAllActive();
	}
	
	@Async
	public CompletableFuture<List<EducationFaculty>> fetchFlyweightAllActiveAsync()
	{
		return CompletableFuture.completedFuture(fetchFlyweightAllActive());
	}
	
	
	public List<EducationFaculty> fetchFlyweightByIds(List<String> ids)
	{
		return educationFacultyRepository.fetchFlyweightByIds(ids);
	}
	
	@Async
	public CompletableFuture<List<EducationFaculty>> fetchFlyweightByIdsAsync(List<String> ids)
	{
		return CompletableFuture.completedFuture(fetchFlyweightByIds(ids));
	}
	
	
	// ****************************** search ****************************** //
	public EducationFacultySearchCommand.Response search(Consumer<EducationFacultySearchCommand.Request> consumer)
	{
		EducationFacultySearchCommand.Request request = new EducationFacultySearchCommand.Request();
		consumer.accept(request);
		return search(request);
	}
	
	public EducationFacultySearchCommand.Response search(EducationFacultySearchCommand.Request request)
	{
		return educationFacultySearchCommand.search(request);
	}
	// ****************************** search ****************************** //
	
	
	// ****************************** create ****************************** //
	@Transactional
	public EducationFacultyCreateCommand.Response create(Consumer<EducationFacultyCreateCommand.Request> consumer) throws DuplicateEntityException
	{
		EducationFacultyCreateCommand.Request request = new EducationFacultyCreateCommand.Request();
		consumer.accept(request);
		return create(request);
	}
	
	@Transactional
	public EducationFacultyCreateCommand.Response create(EducationFacultyCreateCommand.Request request) throws DuplicateEntityException
	{
		return educationFacultyCreateCommand.create(request);
	}
	// ****************************** create ****************************** //
	
	
	// ****************************** update ****************************** //
	@Transactional
	public EducationFacultyUpdateCommand.Response update(Consumer<EducationFacultyUpdateCommand.Request> consumer) throws EntityNotFoundException
	{
		EducationFacultyUpdateCommand.Request request = new EducationFacultyUpdateCommand.Request();
		consumer.accept(request);
		return update(request);
	}
	
	@Transactional
	public EducationFacultyUpdateCommand.Response update(EducationFacultyUpdateCommand.Request request) throws EntityNotFoundException
	{
		return educationFacultyUpdateCommand.update(request);
	}
	// ****************************** update ****************************** //
	
	
	// ****************************** remove ****************************** //
	@Transactional
	public EducationFacultyRemoveCommand.Response remove(Consumer<EducationFacultyRemoveCommand.Request> consumer) throws EntityNotFoundException
	{
		EducationFacultyRemoveCommand.Request request = new EducationFacultyRemoveCommand.Request();
		consumer.accept(request);
		return remove(request);
	}
	
	@Transactional
	public EducationFacultyRemoveCommand.Response remove(EducationFacultyRemoveCommand.Request request) throws EntityNotFoundException
	{
		return educationFacultyRemoveCommand.remove(request);
	}
	// ****************************** remove ****************************** //
}