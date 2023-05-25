package com.otc.kws.core.gateway.web.filter;

import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.otc.kws.core.domain.ApplicationConfigProvider;
import com.otc.kws.core.domain.constant.KwsConst;
import com.otc.kws.core.domain.constant.value.AuthSessionStatusValue;
import com.otc.kws.core.domain.constant.value.MasterStatusValue;
import com.otc.kws.core.domain.entity.AuthSession;
import com.otc.kws.core.domain.entity.ResourceAccessControl;
import com.otc.kws.core.domain.entity.Api;
import com.otc.kws.core.domain.entity.Web;
import com.otc.kws.core.domain.model.UserProfile;
import com.otc.kws.core.domain.service.ResourceAccessControlService;
import com.otc.kws.core.domain.service.auth.AuthSessionService;
import com.otc.kws.core.domain.service.user.UserService;
import com.otc.kws.core.domain.util.JsonUtil;
import com.otc.kws.core.gateway.web.api.ResponseMessage;

public class AuthenFilter extends BaseFilter
{
	@Autowired
	protected ApplicationConfigProvider applicationConfigProvider;
	
	@Autowired
	protected AuthSessionService authSessionService;
	
	@Autowired
	protected UserService userService;
	
	@Autowired
	protected ResourceAccessControlService resourceAccessControlService;
	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException 
	{
		logger.debug("### AuthenFilter.doFilterInternal ###");
		
		Date now = new Date();
		AuthSession authSession = null;
		
		if(isAPI(request)) {
			if(request.getSession(false) != null) {
				String sessionId = request.getSession(false).getId();
				logger.debug("sessionId => " + sessionId);
				authSession = authSessionService.findBySessionId(sessionId);
			} else {
				String accessToken = request.getHeader(KwsConst.HTTP_HEADER_ACCESS_TOKEN);
				logger.debug("accessToken => " + accessToken);
				authSession = authSessionService.findByAccessToken(accessToken);
			}
			
			if(authSession == null) {
				response.setStatus(HttpStatus.UNAUTHORIZED.value());
		        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		        
		        ResponseMessage.Head head = new ResponseMessage.Head();
		        head.setStatus(ResponseMessage.Status.Error);
		        head.setStatusCode("401001");
		        head.setStatusMessage("Authentication Required");
		        
		        ResponseMessage<?> responseMessage = new ResponseMessage<>();
		        responseMessage.setHead(head);
		        
		        JsonUtil.writeValue(response.getWriter(), responseMessage);
		        
		        return;
			}
			
			if(authSession.getExpiredAt().getTime() < now.getTime()) {
				response.setStatus(HttpStatus.UNAUTHORIZED.value());
		        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		        
		        ResponseMessage.Head head = new ResponseMessage.Head();
		        head.setStatus(ResponseMessage.Status.Error);
		        head.setStatusCode("401002");
		        head.setStatusMessage(KwsConst.HTTP_HEADER_ACCESS_TOKEN + " expired");
		        
		        ResponseMessage<?> responseMessage = new ResponseMessage<>();
		        responseMessage.setHead(head);
		        
		        JsonUtil.writeValue(response.getWriter(), responseMessage);
		        
		        return;
			}
			
			if(authSession.getStatus() == AuthSessionStatusValue.Blocked) {
				response.setStatus(HttpStatus.UNAUTHORIZED.value());
		        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		        
		        ResponseMessage.Head head = new ResponseMessage.Head();
		        head.setStatus(ResponseMessage.Status.Error);
		        head.setStatusCode("401003");
		        head.setStatusMessage(KwsConst.HTTP_HEADER_ACCESS_TOKEN + " " + AuthSessionStatusValue.Blocked.name());
		        
		        ResponseMessage<?> responseMessage = new ResponseMessage<>();
		        responseMessage.setHead(head);

		        JsonUtil.writeValue(response.getWriter(), responseMessage);
		        
		        return;
			}
		} else if(isWeb(request)) {
			if(request.getSession(false) == null) {
				response.sendRedirect(request.getContextPath() + "/views/login?callbackURL=" + request.getRequestURI());
				return;
			}
			
			String sessionId = request.getSession().getId();
			logger.debug("sessionId => " + sessionId);
			
			authSession = authSessionService.findBySessionId(sessionId);
			
			if(authSession == null) {
				request.getSession(false).invalidate();
				response.sendRedirect(request.getContextPath() + "/views/login?callbackURL=" + request.getRequestURI());
				return;
			}
			
			if(authSession.getExpiredAt().getTime() < now.getTime()) {
				request.getSession(false).invalidate();
				response.sendRedirect(request.getContextPath() + "/views/login?callbackURL=" + request.getRequestURI());
				return;
			}
			
			if(authSession.getStatus() == AuthSessionStatusValue.Blocked) {
				request.getSession(false).invalidate();
				response.sendRedirect(request.getContextPath() + "/views/login?callbackURL=" + request.getRequestURI());
				return;
			}
		} else if(isResources(request)) {
			if(request.getSession(false) != null) {
				String sessionId = request.getSession().getId();
				logger.debug("sessionId => " + sessionId);
				authSession = authSessionService.findBySessionId(sessionId);
			} else if(request.getParameter(KwsConst.HTTP_PARAM_ACCESS_TOKEN) != null) {
				String kwsAccessToken = request.getParameter(KwsConst.HTTP_PARAM_ACCESS_TOKEN);
				logger.debug(KwsConst.HTTP_PARAM_ACCESS_TOKEN + " => " + kwsAccessToken);
				authSession = authSessionService.findByAccessToken(request.getParameter(KwsConst.HTTP_PARAM_ACCESS_TOKEN));
			}
			
			if(authSession == null) {
				if(verifyResourceAccessControl(request)) {
					filterChain.doFilter(request, response);
					return;
				}
				
				if(request.getParameter(KwsConst.HTTP_PARAM_ACCESS_TOKEN) != null) {
					response.setStatus(HttpStatus.UNAUTHORIZED.value());
			        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			        
			        ResponseMessage.Head head = new ResponseMessage.Head();
			        head.setStatus(ResponseMessage.Status.Error);
			        head.setStatusCode("401001");
			        head.setStatusMessage("Authentication Required");
			        
			        ResponseMessage<?> responseMessage = new ResponseMessage<>();
			        responseMessage.setHead(head);
			        
			        JsonUtil.writeValue(response.getWriter(), responseMessage);
			        
			        return;
				} else {
					request.getSession(false).invalidate();
					response.sendRedirect(request.getContextPath() + "/views/login?callbackURL=" + request.getRequestURI());
					return;
				}
			}
			
			if(authSession.getExpiredAt().getTime() < now.getTime()) {
				if(verifyResourceAccessControl(request)) {
					filterChain.doFilter(request, response);
					return;
				}
				
				if(request.getParameter(KwsConst.HTTP_PARAM_ACCESS_TOKEN) != null) {
					response.setStatus(HttpStatus.UNAUTHORIZED.value());
			        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			        
			        ResponseMessage.Head head = new ResponseMessage.Head();
			        head.setStatus(ResponseMessage.Status.Error);
			        head.setStatusCode("401002");
			        head.setStatusMessage(KwsConst.HTTP_HEADER_ACCESS_TOKEN + " expired");
			        
			        ResponseMessage<?> responseMessage = new ResponseMessage<>();
			        responseMessage.setHead(head);
			        
			        JsonUtil.writeValue(response.getWriter(), responseMessage);
			        
			        return;
				} else {
					request.getSession(false).invalidate();
					response.sendRedirect(request.getContextPath() + "/views/login?callbackURL=" + request.getRequestURI());
					return;
				}
			}
			
			if(authSession.getStatus() == AuthSessionStatusValue.Blocked) {
				if(verifyResourceAccessControl(request)) {
					filterChain.doFilter(request, response);
					return;
				}
				
				if(request.getParameter(KwsConst.HTTP_PARAM_ACCESS_TOKEN) != null) {
					response.setStatus(HttpStatus.UNAUTHORIZED.value());
			        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			        
			        ResponseMessage.Head head = new ResponseMessage.Head();
			        head.setStatus(ResponseMessage.Status.Error);
			        head.setStatusCode("401003");
			        head.setStatusMessage(KwsConst.HTTP_HEADER_ACCESS_TOKEN + " " + AuthSessionStatusValue.Blocked.name());
			        
			        ResponseMessage<?> responseMessage = new ResponseMessage<>();
			        responseMessage.setHead(head);

			        JsonUtil.writeValue(response.getWriter(), responseMessage);
			        
			        return;
				} else {
					request.getSession(false).invalidate();
					response.sendRedirect(request.getContextPath() + "/views/login?callbackURL=" + request.getRequestURI());
					return;
				}
			}
		}
		
		try {
			UserProfile userProfile = JsonUtil.parse(authSession.getUserProfileInfo(), UserProfile.class);
			
			/*
			if(authSession.getLastVerifiedAt().getTime() < now.getTime()) {
				AuthSession _authSession = authSession;
				
				userProfile = userService.buildUserProfile(req -> {
					req.setSearchCriteria(UserService.BuildUserProfileCommand.SearchCriteria.USER_ID);
					req.setUsername(_authSession.getUserId());
				}).getUserProfile();
				
				if(userProfile == null) {
					if(isAPI(request)) {
						
					} else if(isWeb(request)) {
						request.getSession(false).invalidate();
						//response.sendRedirect(request.getContextPath() + "/views/login");
						response.sendRedirect(request.getContextPath() + "/views/login?callbackURL=" + request.getRequestURI());
						return;
					}
				}
				
				authSession.setUserProfileInfo(JsonUtil.stringify(userProfile));
			}
			*/
			
			authSession.setLastAccessedAt(now);
			authSession.setExpiredAt(new Date(now.getTime() + (applicationConfigProvider.getWebSessionTimeout() * 1_000)));
			authSession = authSessionService.update(authSession);
			logger.debug("updated authSession => " + authSession);
			
			request.setAttribute(KwsConst.HTTP_REQ_ATTR_USER_PROFILE, userProfile);
			request.setAttribute(KwsConst.HTTP_REQ_ATTR_AUTH_SESSION, authSession);
		} catch(Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw new ServletException(ex);
		}
		
		filterChain.doFilter(request, response);
	}
	
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException 
	{
		logger.debug("### AuthenFilter.shouldNotFilter ###");
		
		if(isAssets(request)) {
			return true;
		}
		
		if(isOpenAPIEndpoint(request)) {
			return true;
		}
		
		if(isAPI(request)) {
			Api currentAPI = getCurrentApi(request);
			if(! currentAPI.isShouldAuthen()) {
				return true;
			}
		}
		
		if(isWeb(request)) {
			Web web = getCurrentWeb(request);
			if(! web.isShouldAuthen()) {
				return true;
			}
		}
		
	    return false;
	}
	
	protected boolean verifyResourceAccessControl(HttpServletRequest request)
	{
		if(request.getParameter(KwsConst.HTTP_PARAM_RESOURCE_ACCESS_TOKEN) != null) {
			String resourceAccessToken = request.getParameter(KwsConst.HTTP_PARAM_RESOURCE_ACCESS_TOKEN);
			logger.debug("resourceAccessToken => " + resourceAccessToken);
			logger.debug("servletPath => {}", request.getServletPath());
			
			ResourceAccessControl resourceAccessControl = resourceAccessControlService.findByAccessToken(resourceAccessToken.trim());
			logger.debug("resourceAccessControl => {}", resourceAccessControl);
			
			return resourceAccessControl != null && resourceAccessControl.getServletPath().equals(request.getServletPath()) && resourceAccessControl.getStatus() == MasterStatusValue.Active && System.currentTimeMillis() <= resourceAccessControl.getExpiredAt().getTime();
		}
		
		return false;
	}
}