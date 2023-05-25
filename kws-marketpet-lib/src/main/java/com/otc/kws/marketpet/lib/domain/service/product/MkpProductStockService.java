package com.otc.kws.marketpet.lib.domain.service.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.otc.kws.marketpet.lib.domain.entity.MkpProductStock;
import com.otc.kws.marketpet.lib.domain.repository.jpa.JpaMkpProductStockRepository;
import com.otc.kws.marketpet.lib.domain.service.RepositoryKwsMarketPetService;

@Service
public class MkpProductStockService extends RepositoryKwsMarketPetService<JpaMkpProductStockRepository, MkpProductStock, String>
{
	@Autowired
	protected JpaMkpProductStockRepository mkpProductStockRepository;

	
	@Override
	protected JpaMkpProductStockRepository getRepository() 
	{
		return mkpProductStockRepository;
	}

	@Override
	protected String getId(MkpProductStock entity) 
	{
		return entity.getId();
	}
}