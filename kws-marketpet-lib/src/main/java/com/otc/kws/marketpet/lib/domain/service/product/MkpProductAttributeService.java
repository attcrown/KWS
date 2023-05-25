package com.otc.kws.marketpet.lib.domain.service.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.otc.kws.marketpet.lib.domain.entity.MkpProductAttribute;
import com.otc.kws.marketpet.lib.domain.repository.jpa.JpaMkpProductAttributeRepository;
import com.otc.kws.marketpet.lib.domain.service.RepositoryKwsMarketPetService;

@Service
public class MkpProductAttributeService extends RepositoryKwsMarketPetService<JpaMkpProductAttributeRepository, MkpProductAttribute, String>
{
	@Autowired
	protected JpaMkpProductAttributeRepository mkpProductAttributeRepository;
	
	
	@Override
	protected JpaMkpProductAttributeRepository getRepository() 
	{
		return mkpProductAttributeRepository;
	}

	@Override
	protected String getId(MkpProductAttribute entity) 
	{
		return entity.getId();
	}
}