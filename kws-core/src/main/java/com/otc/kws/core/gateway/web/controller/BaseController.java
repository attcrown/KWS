package com.otc.kws.core.gateway.web.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.otc.kws.core.domain.ApplicationConfigProvider;
import com.otc.kws.core.domain.ModuleProvider;
import com.otc.kws.core.domain.ServerURLProvider;
import com.otc.kws.core.domain.constant.KwsConst;
import com.otc.kws.core.domain.entity.AuthSession;
import com.otc.kws.core.domain.entity.Web;
import com.otc.kws.core.domain.model.GrantedAuthority;
import com.otc.kws.core.domain.model.UserProfile;
import com.otc.kws.core.domain.service.BaseKwsCommandRequest;
import com.otc.kws.core.domain.service.ConfigService;

public abstract class BaseController 
{
	public static final String PREFIX_PATH = "/views";

	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	
	@Autowired
	protected ConfigService configService;
	
	@Autowired
	protected ApplicationConfigProvider applicationConfigProvider;
	
	@Autowired
	protected ModuleProvider moduleProvider;
	
	@Autowired
	protected ServerURLProvider serverURLProvider;
	
	
	protected String getServerURL(HttpServletRequest request)
    {
        return serverURLProvider.getServerURL();
    }
	
	protected String getModuleName(HttpServletRequest request)
	{
		return KwsConst.MODULE_NAME;
	}
	
	protected String getRuntimeModuleName(HttpServletRequest request)
	{
		return moduleProvider.getRuntimeModuleName();
	}
	
	protected String getCurrentWebTemplate(HttpServletRequest request)
	{
		return configService.getWebTemplate(getRuntimeModuleName(request));
	}
	
	protected String getPrefixViewFilePath(HttpServletRequest request)
	{
		return getModuleName(request) + "/" + getCurrentWebTemplate(request);
	}
	
	protected String getRuntimePrefixViewFilePath(HttpServletRequest request)
	{
		return getRuntimeModuleName(request) + "/" + getCurrentWebTemplate(request);
	}
    
    protected void setupServiceRequest(HttpServletRequest request, BaseKwsCommandRequest commandRequest)
    {
        AuthSession authSession = getAuthSession(request);
        UserProfile userProfile = getUserProfile(request);
        
        commandRequest.setPerformedAPI(request.getRequestURI());
        if(authSession != null) {
        	commandRequest.setPerformedPlatform(authSession.getPlatform());
        	commandRequest.setPerformedAuthSession(authSession);
        }
        if(userProfile != null) {
        	commandRequest.setPerformedBy(userProfile.getUser().getId());
        	commandRequest.setPerformedUserProfile(userProfile);
        }
        commandRequest.setPerformedAt(new Date());
    }
    
    protected UserProfile getUserProfile(HttpServletRequest request)
    {
        UserProfile userProfile = null;
        if(request.getAttribute(KwsConst.HTTP_REQ_ATTR_USER_PROFILE) != null) {
            userProfile = (UserProfile) request.getAttribute(KwsConst.HTTP_REQ_ATTR_USER_PROFILE);
        }
        return userProfile;
    }
    
    protected AuthSession getAuthSession(HttpServletRequest request)
    {
        AuthSession authSession = null;
        if(request.getAttribute(KwsConst.HTTP_REQ_ATTR_AUTH_SESSION) != null) {
        	authSession = (AuthSession) request.getAttribute(KwsConst.HTTP_REQ_ATTR_AUTH_SESSION);
        }
        return authSession;
    }
    
    protected String getSessionId(HttpServletRequest request)
    {
        HttpSession session = request.getSession(false);
        return session != null ? session.getId() : null;
    }
    
    protected Web getCurrentWeb(HttpServletRequest request)
    {
    	Web web = null;
        if(request.getAttribute(KwsConst.HTTP_REQ_ATTR_CURRENT_WEB) != null) {
            web = (Web) request.getAttribute(KwsConst.HTTP_REQ_ATTR_CURRENT_WEB);
        }
        return web;
    }
    
    protected GrantedAuthority getCurrentGrantedAuthority(HttpServletRequest request)
    {
    	GrantedAuthority grantedAuthority = null;
    	if(request.getAttribute(KwsConst.HTTP_REQ_ATTR_CURRENT_GRANTED_AUTHORITY) != null) {
    		grantedAuthority = (GrantedAuthority) request.getAttribute(KwsConst.HTTP_REQ_ATTR_CURRENT_GRANTED_AUTHORITY);
    	}
    	return grantedAuthority;
    }
    
    protected GrantedAuthority getAllGrantedAuthority(HttpServletRequest request)
    {
    	GrantedAuthority grantedAuthority = null;
    	if(request.getAttribute(KwsConst.HTTP_REQ_ATTR_ALL_GRANTED_AUTHORITY) != null) {
    		grantedAuthority = (GrantedAuthority) request.getAttribute(KwsConst.HTTP_REQ_ATTR_ALL_GRANTED_AUTHORITY);
    	}
    	return grantedAuthority;
    }
    
    protected String getCurrentLabelLanguageId(HttpServletRequest request)
    {
    	AuthSession authSession = getAuthSession(request);
    	if(authSession != null) {
    		return authSession.getLabelLanguageId();
    	} else {
    		return configService.getDefaultLabelLanguageId();
    	}
    }
    
    protected String getWebEnv(HttpServletRequest request)
    {
    	return applicationConfigProvider.getWebEnvirontment();
    }
    
    protected String getDatabaseEnv(HttpServletRequest request)
    {
    	return applicationConfigProvider.getDatabaseEnvirontment();
    }
}