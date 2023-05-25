package com.otc.kws.core.gateway.web.api.cache;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.otc.kws.core.domain.entity.LineNotifyChannel;
import com.otc.kws.core.domain.service.notification.LineNotifyChannelService;
import com.otc.kws.core.gateway.web.api.BaseKwsAPI;
import com.otc.kws.core.gateway.web.api.CacheReloadKwsAPI;

@RestController
@RequestMapping(BaseKwsAPI.PREFIX_PATH + "/line-notify-channel")
public class LineNotifyChannelCacheReloadAPI extends CacheReloadKwsAPI<LineNotifyChannel>
{
	@Autowired
	protected LineNotifyChannelService lineNotifyChannelService;

	@Override
	protected List<LineNotifyChannel> reloadCache() 
	{
		lineNotifyChannelService.reloadCache();
		return lineNotifyChannelService.getAll();
	}
}