package com.otc.kws.core.domain.service.login.exception;

import com.otc.kws.core.domain.exception.KwsException;

public class InvalidUserPasswordException extends KwsException
{
	protected String username;
	protected String password;
	
	public InvalidUserPasswordException(String username, String password) {
		super("Invalid {password: "+password+"} for UserAccount {username: "+username+"}");
		this.username = username;
		this.password = password;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}
}