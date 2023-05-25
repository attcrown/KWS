package com.otc.kws.esc.lib.domain.service.address;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.otc.kws.esc.lib.domain.entity.KwsEscAddress;
import com.otc.kws.esc.lib.domain.repository.jpa.JpaKwsEscAddressRepository;
import com.otc.kws.esc.lib.domain.service.BaseKwsEscService;

@Service
public class KwsEscAddressService extends BaseKwsEscService
{
	@Autowired
	protected JpaKwsEscAddressRepository kwsEscAddressRepository;
	
	
	public List<KwsEscAddress> findAll()
	{
		return kwsEscAddressRepository.findAll();
	}
}