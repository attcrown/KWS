package com.otc.kws.core.domain.repository.jpa;

import org.springframework.stereotype.Repository;

import com.otc.kws.core.domain.entity.UserGroup;

@Repository
public interface JpaUserGroupRepository extends KwsJpaRepository<UserGroup, String>
{

}