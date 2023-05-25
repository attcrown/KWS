package com.otc.kws.core.gateway.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.otc.kws.core.domain.constant.KwsConst;
import com.otc.kws.core.domain.entity.Web;
import com.otc.kws.core.domain.service.WebService;

public class WebSetupFilter extends BaseFilter
{
	@Autowired
	protected WebService webService;
	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException 
	{
		logger.debug("### WebSetupFilter.doFilterInternal ###");
		logger.debug("ServletPath => " + request.getServletPath());
		
		if(request.getServletPath().equals("/")) {
			response.sendRedirect(request.getContextPath() + "/views/workspace/home");
			return;
		}
		
		if(request.getServletPath().equals("/views/login")) {
			if(request.getSession(false) != null) {
				response.sendRedirect(request.getContextPath() + "/views/workspace/home");
				return;
			}
		}
		
		if(isWeb(request)) {
			Web currentWeb = null;
			if(isHomePageWeb(request)) {
				currentWeb = webService.getByRuntimeModuleAndMethodAndPath(request.getMethod().toUpperCase(), request.getServletPath());
			} else if(isWorkspaceWeb(request)) {
				currentWeb = webService.getByMethodAndPath(request.getMethod().toUpperCase(), request.getServletPath());
			} else {
				currentWeb = webService.getByRuntimeModuleAndMethodAndPath(request.getMethod().toUpperCase(), request.getServletPath());
			}
			
			if(currentWeb == null) {
				currentWeb = webService.getByMethodAndPath(request.getMethod().toUpperCase(), request.getServletPath());
			}
			
			logger.debug("currentWeb => " + currentWeb);
			
			if(currentWeb == null && request.getSession(false) != null) {
				response.sendRedirect(request.getContextPath() + "/views/workspace/core/error/404");
				return;
			}
			
			request.setAttribute(KwsConst.HTTP_REQ_ATTR_CURRENT_WEB, currentWeb);
		}
		
		filterChain.doFilter(request, response);
	}
	
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException 
	{
		logger.debug("### WebSetupFilter.shouldNotFilter ###");
		if(request.getServletPath().equals("/")) {
			return false;
		}
	    return !isWeb(request);
	}
}