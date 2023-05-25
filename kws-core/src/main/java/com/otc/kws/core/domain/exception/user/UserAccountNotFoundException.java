package com.otc.kws.core.domain.exception.user;

import com.otc.kws.core.domain.exception.KwsException;

public class UserAccountNotFoundException extends KwsException
{
	protected FindBy findBy;
	protected String userId;
	protected String username;
	
	public UserAccountNotFoundException(FindBy findBy, String userId, String username)
	{
		super(findBy == FindBy.USER_ID ? "User {userId: "+userId+"} Not Found" : "User {username: "+username+"} Not Found");
		this.findBy = findBy;
		this.userId = userId;
		this.username = username;
	}
	
	public FindBy getFindBy() {
		return findBy;
	}
	
	public String getUserId() {
		return userId;
	}
	
	public String getUsername() {
		return username;
	}
	
	public static enum FindBy
	{
		USER_ID, USERNAME
	}
}