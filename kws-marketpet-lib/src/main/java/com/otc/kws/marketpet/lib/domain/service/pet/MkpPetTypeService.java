package com.otc.kws.marketpet.lib.domain.service.pet;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.otc.kws.marketpet.lib.domain.entity.MkpPetType;
import com.otc.kws.marketpet.lib.domain.repository.jpa.JpaMkpPetTypeRepository;
import com.otc.kws.marketpet.lib.domain.service.CacheKwsMarketPetService;

@Service
public class MkpPetTypeService extends CacheKwsMarketPetService<MkpPetType, String>
{
	@Autowired
	protected JpaMkpPetTypeRepository mkpPetTypeRepository;

	
	@Override
	public List<MkpPetType> loadDatas() 
	{
		return mkpPetTypeRepository.findAll();
	}

	@Override
	protected String extractId(MkpPetType entity) 
	{
		return entity.getId();
	}
}