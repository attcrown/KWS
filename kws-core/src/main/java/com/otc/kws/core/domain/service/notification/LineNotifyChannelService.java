package com.otc.kws.core.domain.service.notification;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.otc.kws.core.domain.entity.LineNotifyChannel;
import com.otc.kws.core.domain.repository.jpa.JpaLineNotifyChannelRepository;
import com.otc.kws.core.domain.service.CacheKwsService;

@Service
public class LineNotifyChannelService extends CacheKwsService<LineNotifyChannel, String>
{
	public static final String ID_KWS_ADMIN = "kws.admin";
	public static final String ID_FJB_ADMIN = "firstjobber.admin";
	
	
	@Autowired
	protected JpaLineNotifyChannelRepository lineNotifyChannelRepository;

	
	@Override
	public List<LineNotifyChannel> loadDatas() 
	{
		return lineNotifyChannelRepository.findAll();
	}

	@Override
	protected String extractId(LineNotifyChannel entity) 
	{
		return entity.getId();
	}
	
	
	public LineNotifyChannel getKwsAdminChannel()
	{
		return getAll().stream().filter(e -> e.getId().equals(ID_KWS_ADMIN)).findFirst().orElse(null);
	}
	
	
	public LineNotifyChannel getFirstJobberAdminChannel()
	{
		return getAll().stream().filter(e -> e.getId().equals(ID_FJB_ADMIN)).findFirst().orElse(null);
	}
}