package com.otc.kws.marketpet.lib.domain.repository.jpa;

import org.springframework.stereotype.Repository;

import com.otc.kws.marketpet.lib.domain.entity.MkpOrder;

@Repository
public interface JpaMkpOrderRepository extends KwsMarketPetJpaRepository<MkpOrder, String>
{

}