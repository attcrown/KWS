package com.otc.kws.core.domain.service.login;

import java.util.Date;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.otc.kws.core.domain.constant.value.AuthSessionStatusValue;
import com.otc.kws.core.domain.constant.value.MasterStatusValue;
import com.otc.kws.core.domain.entity.AuthSession;
import com.otc.kws.core.domain.exception.KwsRuntimeException;
import com.otc.kws.core.domain.exception.user.UserAccountExpiredException;
import com.otc.kws.core.domain.exception.user.UserAccountLockedException;
import com.otc.kws.core.domain.exception.user.UserAccountNotFoundException;
import com.otc.kws.core.domain.exception.user.UserAccountPasswordExpiredException;
import com.otc.kws.core.domain.exception.user.UserAccountPasswordLockedException;
import com.otc.kws.core.domain.exception.user.UserAccountStatusInactivatedException;
import com.otc.kws.core.domain.exception.user.UserPersonalInfoNotFoundException;
import com.otc.kws.core.domain.model.UserProfile;
import com.otc.kws.core.domain.service.login.exception.InvalidUserPasswordException;
import com.otc.kws.core.domain.service.login.exception.InvalidUsernameException;
import com.otc.kws.core.domain.service.user.BuildUserProfileCommand;

//@Component
public class LoginCommandV1 extends LoginService.LoginCommand
{
	@Override
	public Response login(Request request) throws 
			InvalidUsernameException,
			InvalidUserPasswordException,
			UserAccountLockedException,
			UserAccountPasswordLockedException,
			UserAccountExpiredException,
			UserAccountPasswordExpiredException,
			UserAccountStatusInactivatedException,
			UserPersonalInfoNotFoundException
	{
		logger.debug("### " + getClass().getSimpleName() + ".login ###");
		logger.debug("request => " + request);
		
		UserProfile userProfile = null;
		
		try {
			userProfile = userService.buildUserProfile(req -> {
				req.copyFrom(request);
				req.setSearchCriteria(BuildUserProfileCommand.SearchCriteria.USERNAME);
				req.setUsername(request.getUsername());
			}).getUserProfile();
		} catch(UserAccountNotFoundException ex) {
			throw new InvalidUsernameException(request.getUsername());
		} catch(UserPersonalInfoNotFoundException ex) {
			throw new InvalidUsernameException(request.getUsername());
		}
		
		logger.debug("userProfile => " + userProfile);
		
		if(! userProfile.getUser().getPassword().equals(request.getPassword())) {
			throw new InvalidUserPasswordException(request.getUsername(), request.getPassword());
		}
		
		if(userProfile.getUser().isAccountLocked()) {
			throw new UserAccountLockedException(userProfile.getUser().getId(), userProfile.getUser().getUsername());
		}
		
		if(userProfile.getUser().isPasswordLocked()) {
			throw new UserAccountPasswordLockedException(userProfile.getUser().getId(), userProfile.getUser().getUsername());
		}
		
		if(userProfile.getUser().getExpiredDate() != null && userProfile.getUser().getExpiredDate().getTime() < request.getPerformedAt().getTime()) {
			throw new UserAccountExpiredException(userProfile.getUser().getId(), userProfile.getUser().getUsername());
		}
		
		if(userProfile.getUser().getStatus() != MasterStatusValue.Active) {
			throw new UserAccountStatusInactivatedException(userProfile.getUser().getId(), userProfile.getUser().getUsername());
		}
		
		AuthSession authSession = null;
		
		if(request.isCreateAuthSession()) {
			authSession = new AuthSession();
			authSession.setId(UUID.randomUUID().toString());
			authSession.setPlatform(request.getPerformedPlatform());
			authSession.setUserId(userProfile.getUser().getId());
			authSession.setAccessToken(UUID.randomUUID().toString());
			if(request.getLabelLanguageId() == null) {
				authSession.setLabelLanguageId(configService.getDefaultLabelLanguageId());
			} else {
				authSession.setLabelLanguageId(request.getLabelLanguageId());
			}
			try {
				ObjectMapper objectMapper = new ObjectMapper();
				authSession.setUserProfileInfo(objectMapper.writeValueAsString(userProfile));
			} catch(Exception ex) {
				throw new KwsRuntimeException(ex);
			}
			authSession.setStatus(AuthSessionStatusValue.Registered);
			authSession.setCreatedAt(request.getPerformedAt());
			authSession.setLastVerifiedAt(request.getPerformedAt());
			authSession.setLastAccessedAt(request.getPerformedAt());
			authSession.setExpiredAt(new Date(request.getPerformedAt().getTime() + (applicationConfigProvider.getWebSessionTimeout() * 1_000)));
			
			authSession = authSessionService.create(authSession);
			logger.debug("created authSession => " + authSession);
		}
		
		loginEventPublisher.publishLoginEvent(userProfile);
		
		Response response = new Response();
		response.setUserProfile(userProfile);
		response.setAuthSession(authSession);
		
		return response;
	}
}