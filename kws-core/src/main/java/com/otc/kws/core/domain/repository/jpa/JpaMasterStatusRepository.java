package com.otc.kws.core.domain.repository.jpa;

import org.springframework.stereotype.Repository;

import com.otc.kws.core.domain.entity.MasterStatus;

@Repository
public interface JpaMasterStatusRepository extends KwsJpaRepository<MasterStatus, String>
{

}