package com.otc.kws.marketpet.lib.domain.service.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.otc.kws.marketpet.lib.domain.entity.MkpCustomerPointAcquire;
import com.otc.kws.marketpet.lib.domain.repository.jpa.JpaMkpCustomerPointAcquireRepository;
import com.otc.kws.marketpet.lib.domain.service.RepositoryKwsMarketPetService;

@Service
public class MkpCustomerPointAcquireService extends RepositoryKwsMarketPetService<JpaMkpCustomerPointAcquireRepository, MkpCustomerPointAcquire, String>
{
	@Autowired
	protected JpaMkpCustomerPointAcquireRepository mkpCustomerPointAcquireRepository;

	
	@Override
	protected JpaMkpCustomerPointAcquireRepository getRepository() 
	{
		return mkpCustomerPointAcquireRepository;
	}

	@Override
	protected String getId(MkpCustomerPointAcquire entity) 
	{
		return entity.getId();
	}
}