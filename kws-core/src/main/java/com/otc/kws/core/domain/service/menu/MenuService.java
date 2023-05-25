package com.otc.kws.core.domain.service.menu;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.otc.kws.core.domain.constant.value.MasterStatusValue;
import com.otc.kws.core.domain.constant.value.PlatformValue;
import com.otc.kws.core.domain.entity.Menu;
import com.otc.kws.core.domain.repository.jpa.JpaMenuRepository;
import com.otc.kws.core.domain.service.CacheKwsService;

@Service
public class MenuService extends CacheKwsService<Menu, String>
{
	@Autowired
	protected JpaMenuRepository menuRepository;
	
	@Autowired
	protected FetchMenuCommand fetchMenuCommand;
	

	@Override
	public List<Menu> loadDatas() 
	{
		return menuRepository.findAll();
	}

	@Override
	protected String extractId(Menu entity) 
	{
		return entity.getId();
	}
	
	
	public List<Menu> getAllActiveWeb()
	{
		return getAllActiveByPlatform(PlatformValue.web);
	}
	
	public List<Menu> getAllActiveMobile()
	{
		return getAllActiveByPlatform(PlatformValue.mobile);
	}
	
	public List<Menu> getAllActiveByPlatform(PlatformValue platform)
	{
		return getAll().
				stream().
				filter(e -> e.getStatus() == MasterStatusValue.Active).
				filter(e -> e.getPlatform() == platform).
				collect(Collectors.toList());
	}
	
	
	// ****************************** fetchMenu ****************************** //
	public FetchMenuCommand.Response fetchMenu(Consumer<FetchMenuCommand.Request> consumer)
	{
		FetchMenuCommand.Request request = new FetchMenuCommand.Request();
		consumer.accept(request);
		return fetchMenu(request);
	}
	
	public FetchMenuCommand.Response fetchMenu(FetchMenuCommand.Request request)
	{
		return fetchMenuCommand.fetchMenu(request);
	}
	// ****************************** fetchMenu ****************************** //
}