package com.otc.kws.core.domain.repository.jpa;

import org.springframework.stereotype.Repository;

import com.otc.kws.core.domain.entity.UserType;

@Repository
public interface JpaUserTypeRepository extends KwsJpaRepository<UserType, String>
{

}