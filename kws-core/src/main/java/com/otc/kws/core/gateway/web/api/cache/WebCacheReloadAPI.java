package com.otc.kws.core.gateway.web.api.cache;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.otc.kws.core.domain.entity.Web;
import com.otc.kws.core.domain.service.WebService;
import com.otc.kws.core.gateway.web.api.BaseKwsAPI;
import com.otc.kws.core.gateway.web.api.CacheReloadKwsAPI;

@RestController
@RequestMapping(BaseKwsAPI.PREFIX_PATH + "/web")
public class WebCacheReloadAPI extends CacheReloadKwsAPI<Web>
{
	@Autowired
	protected WebService webService;
	
	@Override
	protected List<Web> reloadCache() 
	{
		webService.reloadCache();
		return webService.getAll();
	}
}