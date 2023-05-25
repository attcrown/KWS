package com.otc.kws.marketpet.lib.domain.repository.jpa;

import org.springframework.stereotype.Repository;

import com.otc.kws.marketpet.lib.domain.entity.MkpPaymentType;

@Repository
public interface JpaMkpPaymentTypeRepository extends KwsMarketPetJpaRepository<MkpPaymentType, String>
{

}