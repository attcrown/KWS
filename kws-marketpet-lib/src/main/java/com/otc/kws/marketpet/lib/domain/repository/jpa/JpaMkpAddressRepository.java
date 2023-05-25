package com.otc.kws.marketpet.lib.domain.repository.jpa;

import org.springframework.stereotype.Repository;

import com.otc.kws.marketpet.lib.domain.entity.MkpAddress;

@Repository
public interface JpaMkpAddressRepository extends KwsMarketPetJpaRepository<MkpAddress, String>
{

}