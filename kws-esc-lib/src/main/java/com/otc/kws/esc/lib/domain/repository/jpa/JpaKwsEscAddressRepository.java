package com.otc.kws.esc.lib.domain.repository.jpa;

import org.springframework.stereotype.Repository;

import com.otc.kws.esc.lib.domain.entity.KwsEscAddress;

@Repository
public interface JpaKwsEscAddressRepository extends KwsEscJpaRepository<KwsEscAddress, String>
{

}