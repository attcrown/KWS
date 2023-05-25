package com.otc.kws.marketpet.lib.domain.repository.jpa;

import org.springframework.stereotype.Repository;

import com.otc.kws.marketpet.lib.domain.entity.MkpProductStock;

@Repository
public interface JpaMkpProductStockRepository extends KwsMarketPetJpaRepository<MkpProductStock, String>
{

}