package com.otc.kws.core.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.otc.kws.core.domain.repository.jpa.JpaBankRepository;

import com.otc.kws.core.domain.entity.Bank;

@Service
public class BankService extends CacheKwsService<Bank, String>
{
	@Autowired
	protected JpaBankRepository bankRepository;

	
	@Override
	public List<Bank> loadDatas() 
	{
		return bankRepository.findAll();
	}

	@Override
	protected String extractId(Bank entity) 
	{
		return entity.getId();
	}
}