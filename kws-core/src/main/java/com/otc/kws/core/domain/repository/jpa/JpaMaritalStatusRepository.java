package com.otc.kws.core.domain.repository.jpa;

import org.springframework.stereotype.Repository;

import com.otc.kws.core.domain.entity.MaritalStatus;

@Repository
public interface JpaMaritalStatusRepository extends KwsJpaRepository<MaritalStatus, String>
{

}