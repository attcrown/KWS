package com.otc.kws.marketpet.lib.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.otc.kws.marketpet.lib.domain.entity.MkpPaymentType;
import com.otc.kws.marketpet.lib.domain.repository.jpa.JpaMkpPaymentTypeRepository;

@Service
public class MkpPaymentTypeService extends CacheKwsMarketPetService<MkpPaymentType, String>
{
	@Autowired
	protected JpaMkpPaymentTypeRepository mkpPaymentTypeRepository;

	
	@Override
	public List<MkpPaymentType> loadDatas() 
	{
		return mkpPaymentTypeRepository.findAll();
	}

	@Override
	protected String extractId(MkpPaymentType entity) 
	{
		return entity.getId();
	}
}