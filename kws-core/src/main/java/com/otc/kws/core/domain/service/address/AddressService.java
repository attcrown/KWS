package com.otc.kws.core.domain.service.address;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.otc.kws.core.domain.entity.Address;
import com.otc.kws.core.domain.repository.jpa.JpaAddressRepository;
import com.otc.kws.core.domain.service.RepositoryKwsService;

@Service
public class AddressService extends RepositoryKwsService<JpaAddressRepository, Address, String>
{
	@Autowired
	protected JpaAddressRepository addressRepository;


	@Override
	protected JpaAddressRepository getRepository() 
	{
		return addressRepository;
	}


	@Override
	protected String getId(Address entity) 
	{
		return entity.getId();
	}
}