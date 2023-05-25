package com.otc.kws.marketpet.lib.domain.service.customer;

import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.otc.kws.marketpet.lib.domain.entity.MkpCustomerRegister;
import com.otc.kws.marketpet.lib.domain.repository.jpa.JpaMkpCustomerRegisterRepository;
import com.otc.kws.marketpet.lib.domain.service.RepositoryKwsMarketPetService;

@Service
public class MkpCustomerRegisterService extends RepositoryKwsMarketPetService<JpaMkpCustomerRegisterRepository, MkpCustomerRegister, String>
{
	@Autowired
	protected JpaMkpCustomerRegisterRepository mkpCustomerRegisterRepository;
	
	@Autowired
	@Lazy
	protected MkpCustomerRegisterCommand mkpCustomerRegisterCommand;
	
	@Autowired
	@Lazy
	protected MkpCustomerActivatedRegistrationCommand mkpCustomerActivatedRegistrationCommand;
	
	@Autowired
	@Lazy
	protected MkpCustomerCompletedRegistrationCommand mkpCustomerCompletedRegistrationCommand;
	
	
	// ****************************** register ****************************** //
	@Transactional
	public MkpCustomerRegisterCommand.Response register(Consumer<MkpCustomerRegisterCommand.Request> consumer)
	{
		MkpCustomerRegisterCommand.Request request = new MkpCustomerRegisterCommand.Request();
		consumer.accept(request);
		return register(request);
	}
	
	@Transactional
	public MkpCustomerRegisterCommand.Response register(MkpCustomerRegisterCommand.Request request)
	{
		return mkpCustomerRegisterCommand.register(request);
	}
	// ****************************** register ****************************** //
	
	
	// ****************************** activatedRegistration ****************************** //
	@Transactional
	public MkpCustomerActivatedRegistrationCommand.Response activatedRegistration(Consumer<MkpCustomerActivatedRegistrationCommand.Request> consumer)
	{
		MkpCustomerActivatedRegistrationCommand.Request request = new MkpCustomerActivatedRegistrationCommand.Request();
		consumer.accept(request);
		return activatedRegistration(request);
	}
	
	@Transactional
	public MkpCustomerActivatedRegistrationCommand.Response activatedRegistration(MkpCustomerActivatedRegistrationCommand.Request request)
	{
		return mkpCustomerActivatedRegistrationCommand.activatedRegistration(request);
	}
	// ****************************** activatedRegistration ****************************** //
	
	
	// ****************************** completedRegistration ****************************** //
	@Transactional
	public MkpCustomerCompletedRegistrationCommand.Response completedRegistration(Consumer<MkpCustomerCompletedRegistrationCommand.Request> consumer)
	{
		MkpCustomerCompletedRegistrationCommand.Request request = new MkpCustomerCompletedRegistrationCommand.Request();
		consumer.accept(request);
		return completedRegistration(request);
	}
	
	@Transactional
	public MkpCustomerCompletedRegistrationCommand.Response completedRegistration(MkpCustomerCompletedRegistrationCommand.Request request)
	{
		return mkpCustomerCompletedRegistrationCommand.completedRegistration(request);
	}
	// ****************************** completedRegistration ****************************** //

	
	@Override
	protected JpaMkpCustomerRegisterRepository getRepository() 
	{
		return mkpCustomerRegisterRepository;
	}

	@Override
	protected String getId(MkpCustomerRegister entity) 
	{
		return entity.getId();
	}
}