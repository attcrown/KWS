package com.otc.kws.console.gateway.web.filter;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import com.otc.kws.core.gateway.web.filter.WebSetupFilter;

@Configuration
@Order(3)
public class KwsConsoleWebSetupFilter extends WebSetupFilter
{

}