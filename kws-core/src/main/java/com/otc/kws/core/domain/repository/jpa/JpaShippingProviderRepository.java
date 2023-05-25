package com.otc.kws.core.domain.repository.jpa;

import org.springframework.stereotype.Repository;

import com.otc.kws.core.domain.entity.ShippingProvider;

@Repository
public interface JpaShippingProviderRepository extends KwsJpaRepository<ShippingProvider, String>
{

}