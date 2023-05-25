package com.otc.kws.core.domain.repository.jpa;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.otc.kws.core.domain.constant.value.MasterStatusValue;
import com.otc.kws.core.domain.constant.value.PlatformValue;
import com.otc.kws.core.domain.entity.Menu;

@Repository
public interface JpaMenuRepository extends KwsJpaRepository<Menu, String>
{
	public List<Menu> findByPlatformAndStatus(PlatformValue platform, MasterStatusValue status);
	
	public default List<Menu> findAllActiveByPlatform(PlatformValue platform)
	{
		return findByPlatformAndStatus(platform, MasterStatusValue.Active);
	}
}