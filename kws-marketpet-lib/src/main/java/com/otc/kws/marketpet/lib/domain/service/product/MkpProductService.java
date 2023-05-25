package com.otc.kws.marketpet.lib.domain.service.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.otc.kws.marketpet.lib.domain.entity.MkpProduct;
import com.otc.kws.marketpet.lib.domain.repository.jpa.JpaMkpProductRepository;
import com.otc.kws.marketpet.lib.domain.service.RepositoryKwsMarketPetService;

@Service
public class MkpProductService extends RepositoryKwsMarketPetService<JpaMkpProductRepository, MkpProduct, String>
{
	@Autowired
	protected JpaMkpProductRepository mkpProductRepository;

	
	@Override
	protected JpaMkpProductRepository getRepository() 
	{
		return mkpProductRepository;
	}

	@Override
	protected String getId(MkpProduct entity) 
	{
		return entity.getId();
	}
}