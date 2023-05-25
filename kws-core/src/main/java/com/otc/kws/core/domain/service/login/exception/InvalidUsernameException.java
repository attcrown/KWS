package com.otc.kws.core.domain.service.login.exception;

import com.otc.kws.core.domain.exception.KwsException;

public class InvalidUsernameException extends KwsException
{
	protected String username;
	
	public InvalidUsernameException(String username) {
		super("Invalid username: " + username);
		this.username = username;
	}
	
	public String getUsername() {
		return username;
	}
}