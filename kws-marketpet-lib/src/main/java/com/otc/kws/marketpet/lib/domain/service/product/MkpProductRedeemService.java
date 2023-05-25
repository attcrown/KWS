package com.otc.kws.marketpet.lib.domain.service.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.otc.kws.marketpet.lib.domain.entity.MkpProductRedeem;
import com.otc.kws.marketpet.lib.domain.repository.jpa.JpaMkpProductRedeemRepository;
import com.otc.kws.marketpet.lib.domain.service.RepositoryKwsMarketPetService;

@Service
public class MkpProductRedeemService extends RepositoryKwsMarketPetService<JpaMkpProductRedeemRepository, MkpProductRedeem, String>
{
	@Autowired
	protected JpaMkpProductRedeemRepository mkpProductRedeemRepository;

	
	@Override
	protected JpaMkpProductRedeemRepository getRepository() 
	{
		return mkpProductRedeemRepository;
	}

	@Override
	protected String getId(MkpProductRedeem entity) 
	{
		return entity.getId();
	}
}