package com.otc.kws.console.domain;

import org.springframework.stereotype.Component;

import com.otc.kws.console.domain.constant.KwsConsoleConst;
import com.otc.kws.core.domain.ModuleProvider;

@Component
public class KwsConsoleModuleProvider extends ModuleProvider
{
	@Override
	public String getRuntimeModuleName() 
	{
		return KwsConsoleConst.MODULE_NAME;
	}
}