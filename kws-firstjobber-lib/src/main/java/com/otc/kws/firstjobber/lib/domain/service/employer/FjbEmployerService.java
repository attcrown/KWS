package com.otc.kws.firstjobber.lib.domain.service.employer;

import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.otc.kws.firstjobber.lib.domain.entity.FjbEmployer;
import com.otc.kws.firstjobber.lib.domain.repository.jpa.JpaFjbEmployerRepository;
import com.otc.kws.firstjobber.lib.domain.service.RepositoryKwsFirstJobberService;

@Service
public class FjbEmployerService extends RepositoryKwsFirstJobberService<JpaFjbEmployerRepository, FjbEmployer, String>
{
	@Autowired
	protected JpaFjbEmployerRepository fjbEmployerRepository;
	
	@Autowired
	@Lazy
	protected FjbEmployerBuildProfileCommand fjbEmployerBuildProfileCommand;
	
	@Autowired
	@Lazy
	protected FjbEmployerSearchCommand fjbEmployerSearchCommand;
	
	@Autowired
	@Lazy
	protected FjbEmployerCreateCommand fjbEmployerCreateCommand;
	
	@Autowired
	@Lazy
	protected FjbEmployerUpdateCommand fjbEmployerUpdateCommand;
	
	@Autowired
	@Lazy
	protected FjbEmployerRemoveCommand fjbEmployerRemoveCommand;

	
	@Override
	protected JpaFjbEmployerRepository getRepository() 
	{
		return fjbEmployerRepository;
	}

	@Override
	protected String getId(FjbEmployer entity) 
	{
		return entity.getId();
	}
	
	
	// ****************************** buildEmployerProfile ****************************** //
	public FjbEmployerBuildProfileCommand.Response buildEmployerProfile(Consumer<FjbEmployerBuildProfileCommand.Request> consumer)
	{
		FjbEmployerBuildProfileCommand.Request request = new FjbEmployerBuildProfileCommand.Request();
		consumer.accept(request);
		return buildEmployerProfile(request);
	}
	
	public FjbEmployerBuildProfileCommand.Response buildEmployerProfile(FjbEmployerBuildProfileCommand.Request request)
	{
		return fjbEmployerBuildProfileCommand.buildEmployerProfile(request);
	}
	// ****************************** buildEmployerProfile ****************************** //
	
	
	// ****************************** searchEmployer ****************************** //
	public FjbEmployerSearchCommand.Response searchEmployer(Consumer<FjbEmployerSearchCommand.Request> consumer)
	{
		FjbEmployerSearchCommand.Request request = new FjbEmployerSearchCommand.Request();
		consumer.accept(request);
		return searchEmployer(request);
	}
	
	public FjbEmployerSearchCommand.Response searchEmployer(FjbEmployerSearchCommand.Request request)
	{
		return fjbEmployerSearchCommand.searchEmployer(request);
	}
	// ****************************** searchEmployer ****************************** //
	
	
	// ****************************** createEmployer ****************************** //
	@Transactional
	public FjbEmployerCreateCommand.Response createEmployer(Consumer<FjbEmployerCreateCommand.Request> consumer)
	{
		FjbEmployerCreateCommand.Request request = new FjbEmployerCreateCommand.Request();
		consumer.accept(request);
		return createEmployer(request);
	}
	
	@Transactional
	public FjbEmployerCreateCommand.Response createEmployer(FjbEmployerCreateCommand.Request request)
	{
		return fjbEmployerCreateCommand.createEmployer(request);
	}
	// ****************************** createEmployer ****************************** //
	
	
	// ****************************** updateEmployer ****************************** //
	@Transactional
	public FjbEmployerUpdateCommand.Response updateEmployer(Consumer<FjbEmployerUpdateCommand.Request> consumer)
	{
		FjbEmployerUpdateCommand.Request request = new FjbEmployerUpdateCommand.Request();
		consumer.accept(request);
		return updateEmployer(request);
	}
	
	@Transactional
	public FjbEmployerUpdateCommand.Response updateEmployer(FjbEmployerUpdateCommand.Request request)
	{
		return fjbEmployerUpdateCommand.updateEmployer(request);
	}
	// ****************************** updateEmployer ****************************** //
	
	
	// ****************************** removeEmployer ****************************** //
	@Transactional
	public FjbEmployerRemoveCommand.Response removeEmployer(Consumer<FjbEmployerRemoveCommand.Request> consumer)
	{
		FjbEmployerRemoveCommand.Request request = new FjbEmployerRemoveCommand.Request();
		consumer.accept(request);
		return removeEmployer(request);
	}
	
	@Transactional
	public FjbEmployerRemoveCommand.Response removeEmployer(FjbEmployerRemoveCommand.Request request)
	{
		return fjbEmployerRemoveCommand.removeEmployer(request);
	}
	// ****************************** removeEmployer ****************************** //
}