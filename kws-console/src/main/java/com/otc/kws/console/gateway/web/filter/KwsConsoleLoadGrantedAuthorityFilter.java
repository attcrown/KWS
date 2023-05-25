package com.otc.kws.console.gateway.web.filter;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import com.otc.kws.core.gateway.web.filter.LoadGrantedAuthorityFilter;

@Configuration
@Order(5)
public class KwsConsoleLoadGrantedAuthorityFilter extends LoadGrantedAuthorityFilter
{

}