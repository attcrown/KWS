package com.otc.kws.core.domain.repository.jpa;

import org.springframework.stereotype.Repository;

import com.otc.kws.core.domain.entity.Role;

@Repository
public interface JpaRoleRepository extends KwsJpaRepository<Role, String>
{

}