package com.otc.kws.esc.lib.gateway.web.api.address;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.otc.kws.esc.lib.domain.service.address.KwsEscAddressService;
import com.otc.kws.esc.lib.gateway.web.api.BaseKwsEscAPI;

@RestController
@RequestMapping("/api/address/kws-esc/v1")
public class FetchAllKwsEscAddressAPI extends BaseKwsEscAPI
{
	@Autowired
	protected KwsEscAddressService kwsEscAddressService;
	
	
	@GetMapping
	public Object fetchAllAddress()
	{
		return kwsEscAddressService.findAll();
	}
}