package com.otc.kws.marketpet.lib.domain.repository.jpa;

import org.springframework.stereotype.Repository;

import com.otc.kws.marketpet.lib.domain.entity.MkpPetCategory;

@Repository
public interface JpaMkpPetCategoryRepository extends KwsMarketPetJpaRepository<MkpPetCategory, String>
{

}