package com.otc.kws.marketpet.lib.domain.service.pet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.otc.kws.marketpet.lib.domain.entity.MkpPetAttribute;
import com.otc.kws.marketpet.lib.domain.repository.jpa.JpaMkpPetAttributeRepository;
import com.otc.kws.marketpet.lib.domain.service.RepositoryKwsMarketPetService;

@Service
public class MkpPetAttributeService extends RepositoryKwsMarketPetService<JpaMkpPetAttributeRepository, MkpPetAttribute, String>
{
	@Autowired
	protected JpaMkpPetAttributeRepository mkpPetAttributeRepository;
	
	
	@Override
	protected JpaMkpPetAttributeRepository getRepository() 
	{
		return mkpPetAttributeRepository;
	}

	@Override
	protected String getId(MkpPetAttribute entity) 
	{
		return entity.getId();
	}
}