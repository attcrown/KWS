package com.otc.kws.console.gateway.web.filter;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import com.otc.kws.core.gateway.web.filter.LoggingFilter;

@Configuration
@Order(1)
public class KwsConsoleLoggingFilter extends LoggingFilter
{

}