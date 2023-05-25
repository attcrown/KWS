package com.otc.kws.core.gateway.web.api.cache;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.otc.kws.core.domain.entity.Menu;
import com.otc.kws.core.domain.service.menu.MenuService;
import com.otc.kws.core.gateway.web.api.BaseKwsAPI;
import com.otc.kws.core.gateway.web.api.CacheReloadKwsAPI;

@RestController
@RequestMapping(BaseKwsAPI.PREFIX_PATH + "/menu")
public class MenuCacheReloadAPI extends CacheReloadKwsAPI<Menu>
{
	@Autowired
	protected MenuService menuService;

	@Override
	protected List<Menu> reloadCache() 
	{
		menuService.reloadCache();
		return menuService.getAll();
	}
}