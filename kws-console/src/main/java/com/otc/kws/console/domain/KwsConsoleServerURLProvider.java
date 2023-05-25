package com.otc.kws.console.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.otc.kws.console.domain.constant.KwsConsoleConst;
import com.otc.kws.core.domain.ServerURLProvider;

@Component
public class KwsConsoleServerURLProvider extends ServerURLProvider
{
	@Value("${"+KwsConsoleConst.CONFIG_SERVER_URL+"}")
	protected String serverURL;
	
	@Override
	public String getServerURL() 
	{
		return serverURL;
	}
}