package com.otc.kws.core.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.otc.kws.core.domain.repository.jpa.JpaShippingProviderRepository;

import com.otc.kws.core.domain.entity.ShippingProvider;

@Service
public class ShippingProviderService extends CacheKwsService<ShippingProvider, String>
{
	@Autowired
	protected JpaShippingProviderRepository shippingProviderRepository;

	
	@Override
	public List<ShippingProvider> loadDatas() 
	{
		return shippingProviderRepository.findAll();
	}

	@Override
	protected String extractId(ShippingProvider entity) 
	{
		return entity.getId();
	}
}