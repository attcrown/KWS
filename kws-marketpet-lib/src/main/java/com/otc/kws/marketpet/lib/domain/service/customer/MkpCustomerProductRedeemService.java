package com.otc.kws.marketpet.lib.domain.service.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.otc.kws.marketpet.lib.domain.entity.MkpCustomerProductRedeem;
import com.otc.kws.marketpet.lib.domain.repository.jpa.JpaMkpCustomerProductRedeemRepository;
import com.otc.kws.marketpet.lib.domain.service.RepositoryKwsMarketPetService;

@Service
public class MkpCustomerProductRedeemService extends RepositoryKwsMarketPetService<JpaMkpCustomerProductRedeemRepository, MkpCustomerProductRedeem, String>
{
	@Autowired
	protected JpaMkpCustomerProductRedeemRepository mkpCustomerProductRedeemRepository;

	
	@Override
	protected JpaMkpCustomerProductRedeemRepository getRepository() 
	{
		return mkpCustomerProductRedeemRepository;
	}

	@Override
	protected String getId(MkpCustomerProductRedeem entity) 
	{
		return entity.getId();
	}
}