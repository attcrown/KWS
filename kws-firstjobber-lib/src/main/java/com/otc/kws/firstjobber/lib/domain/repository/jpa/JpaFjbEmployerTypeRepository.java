package com.otc.kws.firstjobber.lib.domain.repository.jpa;

import org.springframework.stereotype.Repository;

import com.otc.kws.firstjobber.lib.domain.entity.FjbEmployerType;

@Repository
public interface JpaFjbEmployerTypeRepository extends KwsFirstJobberJpaRepository<FjbEmployerType, String>
{

}