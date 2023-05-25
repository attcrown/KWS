package com.otc.kws.marketpet.lib.domain.repository.jpa;

import org.springframework.stereotype.Repository;

import com.otc.kws.marketpet.lib.domain.entity.MkpOrderItem;

@Repository
public interface JpaMkpOrderItemRepository extends KwsMarketPetJpaRepository<MkpOrderItem, String>
{

}