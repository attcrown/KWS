package com.otc.kws.core.gateway.web.filter;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.web.filter.OncePerRequestFilter;

import com.otc.kws.core.domain.constant.KwsConst;
import com.otc.kws.core.domain.entity.Api;
import com.otc.kws.core.domain.entity.Web;
import com.otc.kws.core.domain.model.UserProfile;

public abstract class BaseFilter extends OncePerRequestFilter
{
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Value("${spring.application.name}")
	protected String applicationName;
	
	
	protected boolean isAssets(HttpServletRequest request)
	{
		String path = request.getServletPath();
		return request.getMethod().equals(HttpMethod.GET.name()) && (path.startsWith("/assets"));
	}
	
	protected boolean isResources(HttpServletRequest request)
	{
		String path = request.getServletPath();
		return request.getMethod().equals(HttpMethod.GET.name()) && (path.startsWith("/resources"));
	}
	
	protected boolean isOpenAPIEndpoint(HttpServletRequest request)
	{
		String path = request.getServletPath();
		return request.getMethod().equals(HttpMethod.GET.name()) && (path.startsWith("/swagger-ui") || path.startsWith("/v3/api-docs") || path.startsWith("/openapi"));
		//return request.getMethod().equals(HttpMethod.GET.name()) && (path.startsWith("/openapi"));
	}
	
	protected boolean isAPI(HttpServletRequest request)
	{
		String path = request.getServletPath();
		return path.startsWith("/api");
	}
	
	protected boolean isWeb(HttpServletRequest request)
	{
		String path = request.getServletPath();
		return request.getMethod().equals(HttpMethod.GET.name()) && (path.startsWith("/views"));
	}
	
	protected boolean isHomePageWeb(HttpServletRequest request)
	{
		String path = request.getServletPath();
		return request.getMethod().equals(HttpMethod.GET.name()) && (path.equals("/views/workspace/home"));
	}
	
	protected boolean isWorkspaceWeb(HttpServletRequest request)
	{
		String path = request.getServletPath();
		return request.getMethod().equals(HttpMethod.GET.name()) && (path.startsWith("/views/workspace"));
	}
	
	protected Api getCurrentApi(HttpServletRequest request)
	{
		return (Api) request.getAttribute(KwsConst.HTTP_REQ_ATTR_CURRENT_API);
	}
	
	protected Web getCurrentWeb(HttpServletRequest request)
	{
		return (Web) request.getAttribute(KwsConst.HTTP_REQ_ATTR_CURRENT_WEB);
	}
	
	protected UserProfile getUserProfile(HttpServletRequest request)
	{
		return (UserProfile) request.getAttribute(KwsConst.HTTP_REQ_ATTR_USER_PROFILE);
	}
}