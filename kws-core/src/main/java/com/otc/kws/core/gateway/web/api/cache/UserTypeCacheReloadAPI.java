package com.otc.kws.core.gateway.web.api.cache;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.otc.kws.core.domain.entity.UserType;
import com.otc.kws.core.domain.service.user.UserTypeService;
import com.otc.kws.core.gateway.web.api.BaseKwsAPI;
import com.otc.kws.core.gateway.web.api.CacheReloadKwsAPI;

@RestController
@RequestMapping(BaseKwsAPI.PREFIX_PATH + "/user-type")
public class UserTypeCacheReloadAPI extends CacheReloadKwsAPI<UserType>
{
	@Autowired
	protected UserTypeService userTypeService;

	@Override
	protected List<UserType> reloadCache() 
	{
		userTypeService.reloadCache();
		return userTypeService.getAll();
	}
}