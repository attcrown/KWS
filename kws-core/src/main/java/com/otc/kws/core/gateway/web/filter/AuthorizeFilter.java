package com.otc.kws.core.gateway.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.otc.kws.core.domain.constant.value.AuthPermissionValue;
import com.otc.kws.core.domain.constant.value.ResourceTypeValue;
import com.otc.kws.core.domain.entity.Api;
import com.otc.kws.core.domain.entity.Web;
import com.otc.kws.core.domain.service.auth.AuthService;
import com.otc.kws.core.domain.util.JsonUtil;
import com.otc.kws.core.gateway.web.api.ResponseMessage;

public class AuthorizeFilter extends BaseFilter
{
	@Autowired
	protected AuthService authService;
	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException 
	{
		logger.debug("### AuthorizeFilter.doFilterInternal ###");
		
		if(isAPI(request)) {
			Api currentAPI = getCurrentApi(request);
			
			AuthPermissionValue permission = authService.checkAccessPermission(req -> {
				req.setUserProfile(getUserProfile(request));
				req.setResourceType(ResourceTypeValue.api);
				req.setResourceRefId(currentAPI.getId());
			}).getPermission();
			
			if(permission == AuthPermissionValue.Denied) {
				response.setStatus(HttpStatus.UNAUTHORIZED.value());
		        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		        
		        ResponseMessage.Head head = new ResponseMessage.Head();
		        head.setStatus(ResponseMessage.Status.Error);
		        head.setStatusCode("402001");
		        head.setStatusMessage("No Granted Authorize For Access This API");
		        
		        ResponseMessage<?> responseMessage = new ResponseMessage<>();
		        responseMessage.setHead(head);
		        
		        JsonUtil.writeValue(response.getWriter(), responseMessage);
		        
		        return;
			}
		}
		
		if(isWeb(request)) {
			Web currentWeb = getCurrentWeb(request);
			
			AuthPermissionValue permission = authService.checkAccessPermission(req -> {
				req.setUserProfile(getUserProfile(request));
				req.setResourceType(ResourceTypeValue.web);
				req.setResourceRefId(currentWeb.getId());
			}).getPermission();
			
			if(permission == AuthPermissionValue.Denied) {
				response.sendRedirect(request.getContextPath() + "/views/workspace/core/error/401");
				return;
			}
		}
		
		filterChain.doFilter(request, response);
	}
	
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException 
	{
		logger.debug("### AuthorizeFilter.shouldNotFilter ###");
		
		if(isAssets(request)) {
			return true;
		}
		
		if(isOpenAPIEndpoint(request)) {
			return true;
		}
		
		if(isAPI(request)) {
			Api currentAPI = getCurrentApi(request);
			if(! currentAPI.isShouldAuthorize()) {
				return true;
			}
		}
		
		if(isWeb(request)) {
			Web web = getCurrentWeb(request);
			if(! web.isShouldAuthorize()) {
				return true;
			}
		}
		
		if(isResources(request)) {
			return true;
		}
		
	    return false;
	}
}