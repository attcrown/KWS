package com.otc.kws.core.domain.repository.jpa;

import org.springframework.stereotype.Repository;

import com.otc.kws.core.domain.entity.Bank;

@Repository
public interface JpaBankRepository extends KwsJpaRepository<Bank, String>
{

}