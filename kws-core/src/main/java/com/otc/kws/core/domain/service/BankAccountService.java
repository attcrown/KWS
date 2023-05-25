package com.otc.kws.core.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.otc.kws.core.domain.entity.BankAccount;
import com.otc.kws.core.domain.repository.jpa.JpaBankAccountRepository;

@Service
public class BankAccountService extends CacheKwsService<BankAccount, String>
{
	@Autowired
	protected JpaBankAccountRepository bankAccountRepository;

	
	@Override
	public List<BankAccount> loadDatas() 
	{
		return bankAccountRepository.findAll();
	}

	@Override
	protected String extractId(BankAccount entity) 
	{
		return entity.getId();
	}
}