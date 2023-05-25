package com.otc.kws.marketpet.lib.domain.repository.jpa;

import org.springframework.stereotype.Repository;

import com.otc.kws.marketpet.lib.domain.entity.MkpPetType;

@Repository
public interface JpaMkpPetTypeRepository extends KwsMarketPetJpaRepository<MkpPetType, String>
{

}