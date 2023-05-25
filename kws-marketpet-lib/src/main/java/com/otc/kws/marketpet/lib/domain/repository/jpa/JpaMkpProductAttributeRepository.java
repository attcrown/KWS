package com.otc.kws.marketpet.lib.domain.repository.jpa;

import org.springframework.stereotype.Repository;

import com.otc.kws.marketpet.lib.domain.entity.MkpProductAttribute;

@Repository
public interface JpaMkpProductAttributeRepository extends KwsMarketPetJpaRepository<MkpProductAttribute, String>
{

}