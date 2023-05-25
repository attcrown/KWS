package com.otc.kws.core.gateway.web.api.cache;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.otc.kws.core.domain.entity.Api;
import com.otc.kws.core.domain.service.ApiService;
import com.otc.kws.core.gateway.web.api.BaseKwsAPI;
import com.otc.kws.core.gateway.web.api.CacheReloadKwsAPI;

@RestController
@RequestMapping(BaseKwsAPI.PREFIX_PATH + "/api")
public class ApiCacheReloadAPI extends CacheReloadKwsAPI<Api>
{
	@Autowired
	protected ApiService apiService;

	@Override
	protected List<Api> reloadCache() 
	{
		apiService.reloadCache();
		return apiService.getAll();
	}
}