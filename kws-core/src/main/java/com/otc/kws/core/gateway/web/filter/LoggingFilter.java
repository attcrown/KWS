package com.otc.kws.core.gateway.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoggingFilter extends BaseFilter
{
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException 
	{
		logger.debug("### LoggingFilter.doFilterInternal ###");
		logger.debug("applicationName => " + applicationName);
		logger.debug(request.getMethod() + " : " + request.getRequestURL());
		
		filterChain.doFilter(request, response);
	}
	
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException 
	{
		logger.debug("### LoggingFilter.shouldNotFilter ###");
	    return isAssets(request);
	}
}