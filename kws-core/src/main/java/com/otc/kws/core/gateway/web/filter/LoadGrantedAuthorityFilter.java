package com.otc.kws.core.gateway.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.otc.kws.core.domain.constant.KwsConst;
import com.otc.kws.core.domain.entity.Api;
import com.otc.kws.core.domain.entity.Web;
import com.otc.kws.core.domain.model.GrantedAuthority;
import com.otc.kws.core.domain.service.auth.AuthService;

public class LoadGrantedAuthorityFilter extends BaseFilter
{
	@Autowired
	protected AuthService authService;

	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException 
	{
		logger.debug("### LoadGrantedAuthorityFilter.doFilterInternal ###");
		
		if(isAPI(request)) {
			Api currentAPI = getCurrentApi(request);
			
			GrantedAuthority currentGrantedAuthority = authService.fetchGrantedAuthority(req -> {
				req.setUserProfile(getUserProfile(request));
				req.setResourceRefId(currentAPI.getId());
			}).getGrantedAuthority();
			request.setAttribute(KwsConst.HTTP_REQ_ATTR_CURRENT_GRANTED_AUTHORITY, currentGrantedAuthority);
			
			GrantedAuthority allGrantedAuthority = authService.fetchGrantedAuthority(req -> {
				req.setUserProfile(getUserProfile(request));
			}).getGrantedAuthority();
			request.setAttribute(KwsConst.HTTP_REQ_ATTR_ALL_GRANTED_AUTHORITY, allGrantedAuthority);
		}
		
		if(isWeb(request)) {
			Web currentWeb = getCurrentWeb(request);
			
			GrantedAuthority currentGrantedAuthority = authService.fetchGrantedAuthority(req -> {
				req.setUserProfile(getUserProfile(request));
				req.setResourceRefId(currentWeb.getId());
			}).getGrantedAuthority();
			request.setAttribute(KwsConst.HTTP_REQ_ATTR_CURRENT_GRANTED_AUTHORITY, currentGrantedAuthority);
			
			GrantedAuthority allGrantedAuthority = authService.fetchGrantedAuthority(req -> {
				req.setUserProfile(getUserProfile(request));
			}).getGrantedAuthority();
			request.setAttribute(KwsConst.HTTP_REQ_ATTR_ALL_GRANTED_AUTHORITY, allGrantedAuthority);
		}
		
		filterChain.doFilter(request, response);
	}
	
	
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException 
	{
		logger.debug("### LoadGrantedAuthorityFilter.shouldNotFilter ###");
		
		if(isAssets(request)) {
			return true;
		}
		
		if(isOpenAPIEndpoint(request)) {
			return true;
		}
		
	    return false;
	}
}