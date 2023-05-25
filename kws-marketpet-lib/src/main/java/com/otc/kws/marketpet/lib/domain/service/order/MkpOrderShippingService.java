package com.otc.kws.marketpet.lib.domain.service.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.otc.kws.marketpet.lib.domain.entity.MkpOrderShipping;
import com.otc.kws.marketpet.lib.domain.repository.jpa.JpaMkpOrderShippingRepository;
import com.otc.kws.marketpet.lib.domain.service.RepositoryKwsMarketPetService;

@Service
public class MkpOrderShippingService extends RepositoryKwsMarketPetService<JpaMkpOrderShippingRepository, MkpOrderShipping, String>
{
	@Autowired
	protected JpaMkpOrderShippingRepository mkpOrderShippingRepository;

	
	@Override
	protected JpaMkpOrderShippingRepository getRepository() 
	{
		return mkpOrderShippingRepository;
	}

	@Override
	protected String getId(MkpOrderShipping entity) 
	{
		return entity.getId();
	}
}