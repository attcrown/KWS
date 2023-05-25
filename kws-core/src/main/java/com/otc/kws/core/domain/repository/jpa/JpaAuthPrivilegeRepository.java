package com.otc.kws.core.domain.repository.jpa;

import org.springframework.stereotype.Repository;

import com.otc.kws.core.domain.entity.AuthPrivilege;

@Repository
public interface JpaAuthPrivilegeRepository extends KwsJpaRepository<AuthPrivilege, String>
{

}