package com.otc.kws.marketpet.lib.domain.service.customer;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.otc.kws.core.domain.constant.value.MasterStatusValue;
import com.otc.kws.marketpet.lib.domain.entity.MkpCustomerPointAcquireConfig;
import com.otc.kws.marketpet.lib.domain.repository.jpa.JpaMkpCustomerPointAcquireConfigRepository;
import com.otc.kws.marketpet.lib.domain.service.CacheKwsMarketPetService;

@Service
public class MkpCustomerPointAcquireConfigService extends CacheKwsMarketPetService<MkpCustomerPointAcquireConfig, String>
{
	@Autowired
	protected JpaMkpCustomerPointAcquireConfigRepository mkpCustomerPointAcquireConfigRepository;

	
	@Override
	public List<MkpCustomerPointAcquireConfig> loadDatas() 
	{
		return mkpCustomerPointAcquireConfigRepository.findAll();
	}

	@Override
	protected String extractId(MkpCustomerPointAcquireConfig entity) 
	{
		return entity.getId();
	}
	
	
	public List<MkpCustomerPointAcquireConfig> getAllActive()
	{
		return getAll().stream().filter(e -> e.getStatus() == MasterStatusValue.Active).collect(Collectors.toList());
	}
}