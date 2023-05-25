package com.otc.kws.marketpet.lib.domain.repository.jpa;

import org.springframework.stereotype.Repository;

import com.otc.kws.marketpet.lib.domain.entity.MkpSupplier;

@Repository
public interface JpaMkpSupplierRepository extends KwsMarketPetJpaRepository<MkpSupplier, String>
{

}