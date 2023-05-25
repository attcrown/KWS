package com.otc.kws.core.domain.repository.jpa;

import com.otc.kws.core.domain.entity.Module;

import org.springframework.stereotype.Repository;

@Repository
public interface JpaModuleRepository extends KwsJpaRepository<Module, String>
{

}