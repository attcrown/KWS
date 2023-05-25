package com.otc.kws.core.domain.exception.user;

import com.otc.kws.core.domain.exception.KwsException;

public class UserPersonalInfoNotFoundException extends KwsException
{
	protected String userId;

	public UserPersonalInfoNotFoundException(String userId) {
		super("UserPersonalInfo {userId: "+userId+"} Not Found");
		this.userId = userId;
	}

	public String getUserId() {
		return userId;
	}
}