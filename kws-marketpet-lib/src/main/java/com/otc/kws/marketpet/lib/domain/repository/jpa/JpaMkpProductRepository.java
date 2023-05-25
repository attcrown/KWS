package com.otc.kws.marketpet.lib.domain.repository.jpa;

import org.springframework.stereotype.Repository;

import com.otc.kws.marketpet.lib.domain.entity.MkpProduct;

@Repository
public interface JpaMkpProductRepository extends KwsMarketPetJpaRepository<MkpProduct, String>
{

}