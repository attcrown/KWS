package com.otc.kws.marketpet.lib.domain.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.otc.kws.core.domain.entity.Config;
import com.otc.kws.core.domain.service.ConfigService;
import com.otc.kws.marketpet.lib.domain.constant.KwsMarketPetConst;

@Service
public class MkpConfigService extends BaseKwsMarketPetService
{
	@Autowired
	protected ConfigService configService;
	
	
	protected Config getByCategoryAndName(String category, String name)
	{
		return configService.getByModuleAndCategoryAndName(KwsMarketPetConst.MODULE_NAME, category, name);
	}
	
	protected String getValueByCategoryAndName(String category, String name)
	{
		Config config = getByCategoryAndName(category, name);
		if(config != null) {
			return config.getValue();
		}
		return null;
	}
	
	public String getRegistrationInvitedRegisterLink()
	{
		return getValueByCategoryAndName("registration", "invited_register_link");
	}
	
	public String getRegistrationActivationLink()
	{
		return getValueByCategoryAndName("registration", "activation_link");
	}
	
	public int getPointMaxHierachyAcquire()
	{
		return Integer.parseInt(getValueByCategoryAndName("point", "max_hierachy_acquire"));
	}
	
	public BigDecimal getPointPricePerPoint()
	{
		return new BigDecimal(getValueByCategoryAndName("point", "price_per_point"));
	}
}