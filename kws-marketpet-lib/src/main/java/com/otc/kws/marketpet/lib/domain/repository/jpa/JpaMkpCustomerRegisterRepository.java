package com.otc.kws.marketpet.lib.domain.repository.jpa;

import org.springframework.stereotype.Repository;

import com.otc.kws.marketpet.lib.domain.entity.MkpCustomerRegister;

@Repository
public interface JpaMkpCustomerRegisterRepository extends KwsMarketPetJpaRepository<MkpCustomerRegister, String>
{

}