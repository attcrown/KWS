package com.otc.kws.core.domain.repository.jpa;

import org.springframework.stereotype.Repository;

import com.otc.kws.core.domain.entity.Address;

@Repository
public interface JpaAddressRepository extends KwsJpaRepository<Address, String>
{

}