package com.otc.kws.core.domain.service.salesman;

import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.otc.kws.core.domain.service.BaseKwsService;

@Service
public class SalesmanService extends BaseKwsService
{
	@Autowired
	protected SalesmanCreateCommand salesmanCreateCommand;
	
	@Autowired
	protected SalesmanUpdateCommand salesmanUpdateCommand;
	
	@Autowired
	protected SalesmanRemoveCommand salesmanRemoveCommand;
	
	
	// ****************************** create ****************************** //
	@Transactional
	public SalesmanCreateCommand.Response create(Consumer<SalesmanCreateCommand.Request> consumer)
	{
		SalesmanCreateCommand.Request request = new SalesmanCreateCommand.Request();
		consumer.accept(request);
		return create(request);
	}
	
	@Transactional
	public SalesmanCreateCommand.Response create(SalesmanCreateCommand.Request request)
	{
		return salesmanCreateCommand.create(request);
	}
	// ****************************** create ****************************** //
	
	
	// ****************************** update ****************************** //
	@Transactional
	public SalesmanUpdateCommand.Response update(Consumer<SalesmanUpdateCommand.Request> consumer)
	{
		SalesmanUpdateCommand.Request request = new SalesmanUpdateCommand.Request();
		consumer.accept(request);
		return update(request);
	}
	
	@Transactional
	public SalesmanUpdateCommand.Response update(SalesmanUpdateCommand.Request request)
	{
		return salesmanUpdateCommand.update(request);
	}
	// ****************************** update ****************************** //
	
	
	// ****************************** remove ****************************** //
	@Transactional
	public SalesmanRemoveCommand.Response remove(Consumer<SalesmanRemoveCommand.Request> consumer)
	{
		SalesmanRemoveCommand.Request request = new SalesmanRemoveCommand.Request();
		consumer.accept(request);
		return remove(request);
	}
	
	@Transactional
	public SalesmanRemoveCommand.Response remove(SalesmanRemoveCommand.Request request)
	{
		return salesmanRemoveCommand.remove(request);
	}
	// ****************************** remove ****************************** //
}