package com.otc.kws.console.gateway.web.filter;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import com.otc.kws.core.gateway.web.filter.AuthorizeFilter;

@Configuration
@Order(6)
public class KwsConsoleAuthorizeFilter extends AuthorizeFilter
{

}