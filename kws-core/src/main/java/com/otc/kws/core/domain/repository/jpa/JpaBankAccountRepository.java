package com.otc.kws.core.domain.repository.jpa;

import org.springframework.stereotype.Repository;

import com.otc.kws.core.domain.entity.BankAccount;

@Repository
public interface JpaBankAccountRepository extends KwsJpaRepository<BankAccount, String>
{

}