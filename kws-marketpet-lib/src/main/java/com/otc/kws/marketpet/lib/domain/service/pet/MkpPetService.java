package com.otc.kws.marketpet.lib.domain.service.pet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.otc.kws.marketpet.lib.domain.entity.MkpPet;
import com.otc.kws.marketpet.lib.domain.repository.jpa.JpaMkpPetRepository;
import com.otc.kws.marketpet.lib.domain.service.RepositoryKwsMarketPetService;

@Service
public class MkpPetService extends RepositoryKwsMarketPetService<JpaMkpPetRepository, MkpPet, String>
{
	@Autowired
	protected JpaMkpPetRepository mkpPetRepository;

	
	@Override
	protected JpaMkpPetRepository getRepository() 
	{
		return mkpPetRepository;
	}

	@Override
	protected String getId(MkpPet entity) 
	{
		return entity.getId();
	}
}