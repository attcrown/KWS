package com.otc.kws.marketpet.lib.domain.repository.jpa;

import org.springframework.stereotype.Repository;

import com.otc.kws.marketpet.lib.domain.entity.MkpProductBrand;

@Repository
public interface JpaMkpProductBrandRepository extends KwsMarketPetJpaRepository<MkpProductBrand, String>
{

}