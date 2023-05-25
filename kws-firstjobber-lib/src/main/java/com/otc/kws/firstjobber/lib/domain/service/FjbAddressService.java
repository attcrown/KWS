package com.otc.kws.firstjobber.lib.domain.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.otc.kws.firstjobber.lib.domain.entity.FjbAddress;
import com.otc.kws.firstjobber.lib.domain.repository.jpa.JpaFjbAddressRepository;

@Service
public class FjbAddressService extends RepositoryKwsFirstJobberService<JpaFjbAddressRepository, FjbAddress, String>
{
	@Autowired
	protected JpaFjbAddressRepository fjbAddressRepository;

	
	@Override
	protected JpaFjbAddressRepository getRepository() 
	{
		return fjbAddressRepository;
	}

	@Override
	protected String getId(FjbAddress entity) 
	{
		return entity.getId();
	}
	
	
	public List<FjbAddress> findByAddressTypeAndAddressRefId(String addressType, String addressRefId)
	{
		return fjbAddressRepository.findByAddressTypeAndAddressRefId(addressType, addressRefId);
	}
	
	@Async
	public CompletableFuture<List<FjbAddress>> findByAddressTypeAndAddressRefIdAsync(String addressType, String addressRefId)
	{
		return CompletableFuture.completedFuture(findByAddressTypeAndAddressRefId(addressType, addressRefId));
	}
}