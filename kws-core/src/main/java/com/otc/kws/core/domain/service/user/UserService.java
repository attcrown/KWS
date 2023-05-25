package com.otc.kws.core.domain.service.user;

import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.otc.kws.core.domain.exception.user.UserAccountNotFoundException;
import com.otc.kws.core.domain.exception.user.UserPersonalInfoNotFoundException;
import com.otc.kws.core.domain.service.BaseKwsService;

@Service
public class UserService extends BaseKwsService
{
	@Autowired
	protected BuildUserProfileCommand buildUserProfileCommand;

	
	// ****************************** buildUserProfile ****************************** //
	public BuildUserProfileCommand.Response buildUserProfile(Consumer<BuildUserProfileCommand.Request> consumer) throws UserAccountNotFoundException, UserPersonalInfoNotFoundException
	{
		BuildUserProfileCommand.Request request = new BuildUserProfileCommand.Request();
		consumer.accept(request);
		return buildUserProfile(request);
	}
	
	public BuildUserProfileCommand.Response buildUserProfile(BuildUserProfileCommand.Request request) throws UserAccountNotFoundException, UserPersonalInfoNotFoundException
	{
		return buildUserProfileCommand.buildUserProfile(request);
	}
}