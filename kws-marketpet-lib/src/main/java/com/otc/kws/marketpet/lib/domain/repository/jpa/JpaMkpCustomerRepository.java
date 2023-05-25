package com.otc.kws.marketpet.lib.domain.repository.jpa;

import org.springframework.stereotype.Repository;

import com.otc.kws.marketpet.lib.domain.entity.MkpCustomer;

@Repository
public interface JpaMkpCustomerRepository extends KwsMarketPetJpaRepository<MkpCustomer, String>
{

}