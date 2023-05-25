package com.otc.kws.marketpet.lib.domain.service.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.otc.kws.marketpet.lib.domain.entity.MkpOrder;
import com.otc.kws.marketpet.lib.domain.repository.jpa.JpaMkpOrderRepository;
import com.otc.kws.marketpet.lib.domain.service.RepositoryKwsMarketPetService;

@Service
public class MkpOrderService extends RepositoryKwsMarketPetService<JpaMkpOrderRepository, MkpOrder, String>
{
	@Autowired
	protected JpaMkpOrderRepository mkpOrderRepository;

	
	@Override
	protected JpaMkpOrderRepository getRepository() 
	{
		return mkpOrderRepository;
	}

	@Override
	protected String getId(MkpOrder entity) 
	{
		return entity.getId();
	}
}