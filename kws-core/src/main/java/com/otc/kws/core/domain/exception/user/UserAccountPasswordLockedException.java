package com.otc.kws.core.domain.exception.user;

import com.otc.kws.core.domain.entity.UserAccount;
import com.otc.kws.core.domain.exception.KwsException;

public class UserAccountPasswordLockedException extends KwsException
{
	protected UserAccount userAccount;
	protected String userId;
	protected String username;
	
	public UserAccountPasswordLockedException(UserAccount userAccount) {
		this(userAccount.getId(), userAccount.getUsername());
		this.userAccount = userAccount;
	}
	
	public UserAccountPasswordLockedException(String userId, String username) {
		super("UserAccount {userId: "+userId+", username: "+username+"} Password Locked");
		this.userId = userId;
		this.username = username;
	}
	
	public UserAccount getUserAccount() {
		return userAccount;
	}
	
	public String getUserId() {
		return userId;
	}
	
	public String getUsername() {
		return username;
	}
}