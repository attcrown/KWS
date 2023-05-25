package com.otc.kws.marketpet.lib.domain.service.pet;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.otc.kws.marketpet.lib.domain.entity.MkpPetCategory;
import com.otc.kws.marketpet.lib.domain.repository.jpa.JpaMkpPetCategoryRepository;

import com.otc.kws.marketpet.lib.domain.service.CacheKwsMarketPetService;

@Service
public class MkpPetCategoryService extends CacheKwsMarketPetService<MkpPetCategory, String>
{
	@Autowired
	protected JpaMkpPetCategoryRepository mkpPetCategoryRepository;

	
	@Override
	public List<MkpPetCategory> loadDatas() 
	{
		return mkpPetCategoryRepository.findAll();
	}

	@Override
	protected String extractId(MkpPetCategory entity) 
	{
		return entity.getId();
	}
}