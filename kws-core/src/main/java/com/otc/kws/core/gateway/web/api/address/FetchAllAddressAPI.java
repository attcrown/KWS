package com.otc.kws.core.gateway.web.api.address;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.otc.kws.core.domain.service.address.AddressService;
import com.otc.kws.core.gateway.web.api.BaseKwsAPI;

@RestController
@RequestMapping("/api/address/v1")
public class FetchAllAddressAPI extends BaseKwsAPI
{
	@Autowired
	protected AddressService addressService;
	
	
	@GetMapping
	public Object fetchAllAddress()
	{
		return addressService.findAll();
	}
}