package com.otc.kws.core.domain.repository.jpa;

import org.springframework.stereotype.Repository;

import com.otc.kws.core.domain.entity.AuthPolicy;

@Repository
public interface JpaAuthPolicyRepository extends KwsJpaRepository<AuthPolicy, String>
{

}