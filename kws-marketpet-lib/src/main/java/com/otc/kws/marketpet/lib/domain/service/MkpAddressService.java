package com.otc.kws.marketpet.lib.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.otc.kws.marketpet.lib.domain.entity.MkpAddress;
import com.otc.kws.marketpet.lib.domain.repository.jpa.JpaMkpAddressRepository;

@Service
public class MkpAddressService extends RepositoryKwsMarketPetService<JpaMkpAddressRepository, MkpAddress, String>
{
	@Autowired
	protected JpaMkpAddressRepository mkpAddressRepository;

	
	@Override
	protected JpaMkpAddressRepository getRepository() 
	{
		return mkpAddressRepository;
	}

	@Override
	protected String getId(MkpAddress entity) 
	{
		return entity.getId();
	}
}