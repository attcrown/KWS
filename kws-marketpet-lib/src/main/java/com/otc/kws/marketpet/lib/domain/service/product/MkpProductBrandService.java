package com.otc.kws.marketpet.lib.domain.service.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.otc.kws.marketpet.lib.domain.entity.MkpProductBrand;
import com.otc.kws.marketpet.lib.domain.repository.jpa.JpaMkpProductBrandRepository;
import com.otc.kws.marketpet.lib.domain.service.RepositoryKwsMarketPetService;

@Service
public class MkpProductBrandService extends RepositoryKwsMarketPetService<JpaMkpProductBrandRepository, MkpProductBrand, String>
{
	@Autowired
	protected JpaMkpProductBrandRepository mkpProductBrandRepository;

	
	@Override
	protected JpaMkpProductBrandRepository getRepository() 
	{
		return mkpProductBrandRepository;
	}

	@Override
	protected String getId(MkpProductBrand entity) 
	{
		return entity.getId();
	}
}