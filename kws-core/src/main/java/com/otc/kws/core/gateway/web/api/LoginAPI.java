package com.otc.kws.core.gateway.web.api;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.otc.kws.core.domain.ApplicationConfigProvider;
import com.otc.kws.core.domain.constant.value.PlatformValue;
import com.otc.kws.core.domain.entity.AuthSession;
import com.otc.kws.core.domain.exception.user.UserAccountExpiredException;
import com.otc.kws.core.domain.exception.user.UserAccountLockedException;
import com.otc.kws.core.domain.exception.user.UserAccountPasswordExpiredException;
import com.otc.kws.core.domain.exception.user.UserAccountPasswordLockedException;
import com.otc.kws.core.domain.exception.user.UserAccountStatusInactivatedException;
import com.otc.kws.core.domain.exception.user.UserPersonalInfoNotFoundException;
import com.otc.kws.core.domain.model.UserProfile;
import com.otc.kws.core.domain.service.auth.AuthSessionService;
import com.otc.kws.core.domain.service.login.LoginService;
import com.otc.kws.core.domain.service.login.exception.InvalidUserPasswordException;
import com.otc.kws.core.domain.service.login.exception.InvalidUsernameException;

import lombok.Data;

@RestController
@RequestMapping(BaseKwsAPI.PREFIX_PATH + "/login")
public class LoginAPI extends BaseKwsAPI
{
	@Autowired
	protected ApplicationConfigProvider applicationConfigProvider;
	
	@Autowired
	protected LoginService loginService;
	
	@Autowired
	protected AuthSessionService authSessionService;
	
	
	@PostMapping
	public ResponseEntity<ResponseMessage<BodyResponseMessage>> login(
			HttpServletRequest request, 
			@RequestBody RequestMessage requestMessage) throws 
				InvalidUsernameException, 
				InvalidUserPasswordException, 
				UserAccountLockedException, 
				UserAccountPasswordLockedException, 
				UserAccountExpiredException, 
				UserAccountPasswordExpiredException, 
				UserAccountStatusInactivatedException,
				UserPersonalInfoNotFoundException
	{
		logger.info("### LoginAPI.login ###");
		logger.info("requestMessage => " + requestMessage);
		
		LoginService.LoginCommand.Response loginResponse = loginService.login(req -> {
			setupServiceRequest(request, req);
			req.setPerformedPlatform(requestMessage.getPlatform());
			req.setLabelLanguageId(requestMessage.getLabelLanguageId());
			req.setUsername(requestMessage.getUsername());
			req.setPassword(requestMessage.getPassword());
			req.setCreateAuthSession(true);
		});
		
		if(requestMessage.getPlatform() == PlatformValue.web) {
			request.getSession().setMaxInactiveInterval((int) applicationConfigProvider.getWebSessionTimeout());
			AuthSession authSession = loginResponse.getAuthSession();
			authSession.setSessionId(request.getSession().getId());
			authSessionService.update(authSession);
			logger.debug("create session {id: "+request.getSession().getId()+"}");
			logger.debug("updated authSession => " + authSession);
		}
		
		BodyResponseMessage bodyResponseMessage = new BodyResponseMessage();
		bodyResponseMessage.setSuccess(true);
		bodyResponseMessage.setAccessToken(loginResponse.getAuthSession().getAccessToken());
		bodyResponseMessage.setUserProfile(loginResponse.getUserProfile());
		
		return replySuccess(request, bodyResponseMessage);
	}
	
	
	@Data
	public static class RequestMessage
	{
		protected PlatformValue platform;
		protected String labelLanguageId;
		protected String username;
		protected String password;
	}
	
	
	@Data
	public static class BodyResponseMessage extends ResponseMessage.BaseBody
	{
		protected boolean success;
		protected String accessToken;
		protected UserProfile userProfile;
	}
}