package com.otc.kws.core.gateway.web.api.cache;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.otc.kws.core.domain.entity.AuthPrivilege;
import com.otc.kws.core.domain.service.auth.AuthPrivilegeService;
import com.otc.kws.core.gateway.web.api.BaseKwsAPI;
import com.otc.kws.core.gateway.web.api.CacheReloadKwsAPI;

@RestController
@RequestMapping(BaseKwsAPI.PREFIX_PATH + "/auth-privilege")
public class AuthPrivilegeCacheReloadAPI extends CacheReloadKwsAPI<AuthPrivilege>
{
	@Autowired
	protected AuthPrivilegeService authPrivilegeService;

	@Override
	protected List<AuthPrivilege> reloadCache() 
	{
		authPrivilegeService.reloadCache();
		return authPrivilegeService.getAll();
	}
}