package com.otc.kws.core.gateway.web.api.cache;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.otc.kws.core.domain.entity.UserGroup;
import com.otc.kws.core.domain.service.user.UserGroupService;
import com.otc.kws.core.gateway.web.api.BaseKwsAPI;
import com.otc.kws.core.gateway.web.api.CacheReloadKwsAPI;

@RestController
@RequestMapping(BaseKwsAPI.PREFIX_PATH + "/user-group")
public class UserGroupCacheReloadAPI extends CacheReloadKwsAPI<UserGroup>
{
	@Autowired
	protected UserGroupService userGroupService;

	@Override
	protected List<UserGroup> reloadCache() 
	{
		userGroupService.reloadCache();
		return userGroupService.getAll();
	}
}