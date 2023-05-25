package com.otc.kws.marketpet.lib.domain.service.supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.otc.kws.marketpet.lib.domain.entity.MkpSupplier;
import com.otc.kws.marketpet.lib.domain.repository.jpa.JpaMkpSupplierRepository;
import com.otc.kws.marketpet.lib.domain.service.RepositoryKwsMarketPetService;

@Service
public class MkpSupplierService extends RepositoryKwsMarketPetService<JpaMkpSupplierRepository, MkpSupplier, String>
{
	@Autowired
	protected JpaMkpSupplierRepository mkpSupplierRepository;

	
	@Override
	protected JpaMkpSupplierRepository getRepository() 
	{
		return mkpSupplierRepository;
	}

	@Override
	protected String getId(MkpSupplier entity) 
	{
		return entity.getId();
	};
}