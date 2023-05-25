package com.otc.kws.core.domain.repository.jpa;

import org.springframework.stereotype.Repository;

import com.otc.kws.core.domain.entity.Api;

@Repository
public interface JpaApiRepository extends KwsJpaRepository<Api, String>
{
	public Api findByMethodAndPath(String method, String path);
}