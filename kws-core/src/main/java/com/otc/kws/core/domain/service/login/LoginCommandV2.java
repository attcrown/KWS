package com.otc.kws.core.domain.service.login;

import java.util.Date;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.otc.kws.core.domain.constant.value.AuthSessionStatusValue;
import com.otc.kws.core.domain.constant.value.MasterStatusValue;
import com.otc.kws.core.domain.entity.AuthSession;
import com.otc.kws.core.domain.entity.UserAccount;
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

@Component
public class LoginCommandV2 extends LoginService.LoginCommand
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
		
		UserAccount userAccount = userAccountService.findByUsername(request.getUsername());
		
		if(userAccount == null) {
			throw new InvalidUsernameException(request.getUsername());
		}
		
		if(!userAccount.getPassword().equals(request.getPassword()) && !passwordEncoder.matches(request.getPassword(), userAccount.getPassword())) {
			throw new InvalidUserPasswordException(request.getUsername(), request.getPassword());
		}
		
		if(userAccount.isAccountLocked()) {
			throw new UserAccountLockedException(userAccount.getId(), userAccount.getUsername());
		}
		
		if(userAccount.isPasswordLocked()) {
			throw new UserAccountPasswordLockedException(userAccount.getId(), userAccount.getUsername());
		}
		
		if(userAccount.getAccountExpiredAt() != null && userAccount.getAccountExpiredAt().getTime() < request.getPerformedAt().getTime()) {
			throw new UserAccountExpiredException(userAccount.getId(), userAccount.getUsername());
		}
		
		if(userAccount.getPasswordExpiredAt() != null && userAccount.getPasswordExpiredAt().getTime() < request.getPerformedAt().getTime()) {
			throw new UserAccountPasswordExpiredException(userAccount);
		}
		
		if(userAccount.getStatus() != MasterStatusValue.Active) {
			throw new UserAccountStatusInactivatedException(userAccount.getId(), userAccount.getUsername());
		}
		
		UserProfile userProfile = null;
		
		try {
			userProfile = userService.buildUserProfile(req -> {
				req.copyFrom(request);
				req.setSearchCriteria(BuildUserProfileCommand.SearchCriteria.USER_ACCOUNT);
				req.setUserAccount(userAccount);
			}).getUserProfile();
		} catch(UserAccountNotFoundException ex) {
			throw new InvalidUsernameException(request.getUsername());
		} catch(UserPersonalInfoNotFoundException ex) {
			throw new InvalidUsernameException(request.getUsername());
		}
		
		logger.debug("userProfile => " + userProfile);
		
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