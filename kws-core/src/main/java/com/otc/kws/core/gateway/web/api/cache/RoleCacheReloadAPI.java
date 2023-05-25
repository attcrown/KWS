package com.otc.kws.core.gateway.web.api.cache;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.otc.kws.core.domain.entity.Role;
import com.otc.kws.core.domain.service.RoleService;
import com.otc.kws.core.gateway.web.api.BaseKwsAPI;
import com.otc.kws.core.gateway.web.api.CacheReloadKwsAPI;

@RestController
@RequestMapping(BaseKwsAPI.PREFIX_PATH + "/role")
public class RoleCacheReloadAPI extends CacheReloadKwsAPI<Role>
{
	@Autowired
	protected RoleService roleService;

	@Override
	protected List<Role> reloadCache() 
	{
		roleService.reloadCache();
		return roleService.getAll();
	}
}