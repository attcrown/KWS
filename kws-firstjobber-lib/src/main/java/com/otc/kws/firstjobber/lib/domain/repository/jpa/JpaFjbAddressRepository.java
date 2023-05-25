package com.otc.kws.firstjobber.lib.domain.repository.jpa;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.otc.kws.firstjobber.lib.domain.entity.FjbAddress;

@Repository
public interface JpaFjbAddressRepository extends KwsFirstJobberJpaRepository<FjbAddress, String>
{
	public List<FjbAddress> findByAddressTypeAndAddressRefId(String addressType, String addressRefId);
}