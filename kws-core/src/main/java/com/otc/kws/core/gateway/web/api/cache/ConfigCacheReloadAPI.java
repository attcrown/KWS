package com.otc.kws.core.gateway.web.api.cache;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.otc.kws.core.domain.entity.Config;
import com.otc.kws.core.domain.service.ConfigService;
import com.otc.kws.core.gateway.web.api.BaseKwsAPI;
import com.otc.kws.core.gateway.web.api.CacheReloadKwsAPI;

@RestController
@RequestMapping(BaseKwsAPI.PREFIX_PATH + "/config")
public class ConfigCacheReloadAPI extends CacheReloadKwsAPI<Config>
{
	@Autowired
	protected ConfigService configService;

	@Override
	protected List<Config> reloadCache() 
	{
		configService.reloadCache();
		return configService.getAll();
	}
}