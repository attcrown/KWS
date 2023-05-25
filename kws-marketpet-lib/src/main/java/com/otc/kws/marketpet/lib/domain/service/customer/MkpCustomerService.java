package com.otc.kws.marketpet.lib.domain.service.customer;

import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.otc.kws.marketpet.lib.domain.entity.MkpCustomer;
import com.otc.kws.marketpet.lib.domain.repository.jpa.JpaMkpCustomerRepository;
import com.otc.kws.marketpet.lib.domain.service.RepositoryKwsMarketPetService;

@Service
public class MkpCustomerService extends RepositoryKwsMarketPetService<JpaMkpCustomerRepository, MkpCustomer, String>
{
	@Autowired
	protected JpaMkpCustomerRepository mkpCustomerRepository;
	
	@Autowired
	@Lazy
	protected MkpCustomerGenerateInvitedRegisterLinkCommand mkpCustomerGenerateInvitedRegisterLinkCommand;
	
	@Autowired
	@Lazy
	protected MkpCustomerCalculatePointCommand mkpCustomerCalculatePointCommand;
	
	
	// ****************************** generateInvitedRegisterLink ****************************** //
	public MkpCustomerGenerateInvitedRegisterLinkCommand.Response generateInvitedRegisterLink(Consumer<MkpCustomerGenerateInvitedRegisterLinkCommand.Request> consumer)
	{
		MkpCustomerGenerateInvitedRegisterLinkCommand.Request request = new MkpCustomerGenerateInvitedRegisterLinkCommand.Request();
		consumer.accept(request);
		return generateInvitedRegisterLink(request);
	}
	
	public MkpCustomerGenerateInvitedRegisterLinkCommand.Response generateInvitedRegisterLink(MkpCustomerGenerateInvitedRegisterLinkCommand.Request request)
	{
		return mkpCustomerGenerateInvitedRegisterLinkCommand.generateInvitedRegisterLink(request);
	}
	// ****************************** generateInvitedRegisterLink ****************************** //
	
	
	// ****************************** calculatePoint ****************************** //
	public MkpCustomerCalculatePointCommand.Response calculatePoint(Consumer<MkpCustomerCalculatePointCommand.Request> consumer)
	{
		MkpCustomerCalculatePointCommand.Request request = new MkpCustomerCalculatePointCommand.Request();
		consumer.accept(request);
		return calculatePoint(request);
	}
	
	public MkpCustomerCalculatePointCommand.Response calculatePoint(MkpCustomerCalculatePointCommand.Request request)
	{
		return mkpCustomerCalculatePointCommand.calculatePoint(request);
	}
	// ****************************** calculatePoint ****************************** //

	
	@Override
	protected JpaMkpCustomerRepository getRepository() 
	{
		return mkpCustomerRepository;
	}

	@Override
	protected String getId(MkpCustomer entity) 
	{
		return entity.getId();
	}
}