package com.otc.kws.marketpet.lib.domain.repository.jpa;

import org.springframework.stereotype.Repository;

import com.otc.kws.marketpet.lib.domain.entity.MkpPetAttribute;

@Repository
public interface JpaMkpPetAttributeRepository extends KwsMarketPetJpaRepository<MkpPetAttribute, String>
{

}