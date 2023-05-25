package com.otc.kws.core.domain.repository.jpa;

import org.springframework.stereotype.Repository;

import com.otc.kws.core.domain.entity.Config;

@Repository
public interface JpaConfigRepository extends KwsJpaRepository<Config, Config.CompositeId>
{

}