package com.otc.kws.marketpet.lib.domain.repository.jpa;

import org.springframework.stereotype.Repository;

import com.otc.kws.marketpet.lib.domain.entity.MkpPet;

@Repository
public interface JpaMkpPetRepository extends KwsMarketPetJpaRepository<MkpPet, String>
{

}