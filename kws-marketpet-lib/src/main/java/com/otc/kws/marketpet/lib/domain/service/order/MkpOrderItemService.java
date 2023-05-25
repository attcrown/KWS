package com.otc.kws.marketpet.lib.domain.service.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.otc.kws.marketpet.lib.domain.entity.MkpOrderItem;
import com.otc.kws.marketpet.lib.domain.repository.jpa.JpaMkpOrderItemRepository;
import com.otc.kws.marketpet.lib.domain.service.RepositoryKwsMarketPetService;

@Repository
public class MkpOrderItemService extends RepositoryKwsMarketPetService<JpaMkpOrderItemRepository, MkpOrderItem, String>
{
	@Autowired
	protected JpaMkpOrderItemRepository mkpOrderItemRepository;

	
	@Override
	protected JpaMkpOrderItemRepository getRepository() 
	{
		return mkpOrderItemRepository;
	}

	@Override
	protected String getId(MkpOrderItem entity) 
	{
		return entity.getId();
	}
}