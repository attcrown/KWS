package com.otc.kws.core.gateway.web.api.auth;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.otc.kws.core.domain.model.GrantedAuthority;
import com.otc.kws.core.domain.service.auth.AuthService;
import com.otc.kws.core.gateway.web.api.BaseKwsAPI;
import com.otc.kws.core.gateway.web.api.ResponseMessage;

import lombok.Data;

@RestController
@RequestMapping(BaseKwsAPI.PREFIX_PATH + "/auth")
public class FetchMyGrantedAuthority extends BaseKwsAPI
{
	@Autowired
	protected AuthService authService;
	
	
	@PostMapping("/fetch-my-granted-authority")
	public ResponseEntity<?> fetchMyGrantedAuthority(HttpServletRequest request, @RequestBody(required = false) RequestMessage requestMessage)
	{
		logger.info("### FetchMyGrantedAuthority.fetchMyGrantedAuthority ###");
		logger.info("requestMessage => " + requestMessage);
		
		GrantedAuthority grantedAuthority = authService.fetchGrantedAuthority(req -> {
			setupServiceRequest(request, req);
			req.setUserProfile(getUserProfile(request));
			if(requestMessage != null) {
				req.setResourceRefId(requestMessage.getResourceRefId());
			}
		}).getGrantedAuthority();
		
		BodyResponseMessage bodyResponseMessage = new BodyResponseMessage();
		bodyResponseMessage.setGrantedAuthority(grantedAuthority);
		
		return replySuccess(request, bodyResponseMessage);
	}
	
	
	@Data
	public static class RequestMessage
	{
		protected String resourceRefId;
	}
	
	
	@Data
	public static class BodyResponseMessage extends ResponseMessage.BaseBody
	{
		protected GrantedAuthority grantedAuthority;
	}
}