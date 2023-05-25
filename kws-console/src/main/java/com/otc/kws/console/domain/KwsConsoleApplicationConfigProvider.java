package com.otc.kws.console.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.otc.kws.console.domain.constant.KwsConsoleConst;
import com.otc.kws.core.domain.ApplicationConfigProvider;

@Component
public class KwsConsoleApplicationConfigProvider extends ApplicationConfigProvider
{
	@Value("${"+KwsConsoleConst.CONFIG_WEB_ENV+"}")
	protected String webEnv;
	
	@Value("${"+KwsConsoleConst.CONFIG_DB_ENV+"}")
	protected String dbEnv;

	@Override
	public String getWebEnvirontment() 
	{
		return webEnv;
	}

	@Override
	public String getDatabaseEnvirontment() 
	{
		return dbEnv;
	}
}