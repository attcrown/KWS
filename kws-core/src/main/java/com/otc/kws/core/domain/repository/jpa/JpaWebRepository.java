package com.otc.kws.core.domain.repository.jpa;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.otc.kws.core.domain.entity.Web;

@Repository
public interface JpaWebRepository extends KwsJpaRepository<Web, String>
{
	public List<Web> findByMethodAndPath(String method, String path);
	public Web findByModuleAndMethodAndPath(String module, String method, String path);
}