package com.otc.kws.core.domain.service.login;

import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.otc.kws.core.domain.ApplicationConfigProvider;
import com.otc.kws.core.domain.entity.AuthSession;
import com.otc.kws.core.domain.exception.user.UserAccountExpiredException;
import com.otc.kws.core.domain.exception.user.UserAccountLockedException;
import com.otc.kws.core.domain.exception.user.UserAccountPasswordExpiredException;
import com.otc.kws.core.domain.exception.user.UserAccountPasswordLockedException;
import com.otc.kws.core.domain.exception.user.UserAccountStatusInactivatedException;
import com.otc.kws.core.domain.exception.user.UserPersonalInfoNotFoundException;
import com.otc.kws.core.domain.model.UserProfile;
import com.otc.kws.core.domain.service.BaseKwsCommand;
import com.otc.kws.core.domain.service.BaseKwsCommandRequest;
import com.otc.kws.core.domain.service.BaseKwsCommandResponse;
import com.otc.kws.core.domain.service.BaseKwsService;
import com.otc.kws.core.domain.service.ConfigService;
import com.otc.kws.core.domain.service.auth.AuthSessionService;
import com.otc.kws.core.domain.service.login.exception.InvalidUserPasswordException;
import com.otc.kws.core.domain.service.login.exception.InvalidUsernameException;
import com.otc.kws.core.domain.service.user.UserAccountService;
import com.otc.kws.core.domain.service.user.UserService;
import com.otc.kws.core.event.login.LoginEventPublisher;

import lombok.Data;

@Service
public class LoginService extends BaseKwsService
{
	@Autowired
	protected LoginCommand loginCommand;
	
	
	@Transactional
	public LoginCommand.Response login(Consumer<LoginCommand.Request> consumer) throws 
			InvalidUsernameException,
			InvalidUserPasswordException,
			UserAccountLockedException,
			UserAccountPasswordLockedException,
			UserAccountExpiredException,
			UserAccountPasswordExpiredException,
			UserAccountStatusInactivatedException,
			UserPersonalInfoNotFoundException
	{
		LoginCommand.Request request = new LoginCommand.Request();
		consumer.accept(request);
		return login(request);
	}
	
	@Transactional
	public LoginCommand.Response login(LoginCommand.Request request) throws 
			InvalidUsernameException,
			InvalidUserPasswordException,
			UserAccountLockedException,
			UserAccountPasswordLockedException,
			UserAccountExpiredException,
			UserAccountPasswordExpiredException,
			UserAccountStatusInactivatedException,
			UserPersonalInfoNotFoundException
	{
		return loginCommand.login(request);
	}
	
	
	public static abstract class LoginCommand extends BaseKwsCommand
	{
		@Autowired
		protected ConfigService configService;
		
		@Autowired
		protected ApplicationConfigProvider applicationConfigProvider;
		
		@Autowired
	    protected PasswordEncoder passwordEncoder;
		
		@Autowired
		protected UserAccountService userAccountService;
		
		@Autowired
		protected UserService userService;
		
		@Autowired
		protected AuthSessionService authSessionService;
		
		@Autowired
		protected LoginEventPublisher loginEventPublisher;
		
		public abstract Response login(Request request) throws 
				InvalidUsernameException,
				InvalidUserPasswordException,
				UserAccountLockedException,
				UserAccountPasswordLockedException,
				UserAccountExpiredException,
				UserAccountPasswordExpiredException,
				UserAccountStatusInactivatedException,
				UserPersonalInfoNotFoundException;
		
		@Data
		public static class Request extends BaseKwsCommandRequest
		{
			protected String username;
			protected String password;
			protected String labelLanguageId;
			protected boolean createAuthSession = false;
		}
		
		@Data
		public static class Response extends BaseKwsCommandResponse
		{
			protected UserProfile userProfile;
			protected AuthSession authSession;
		}
	}
}