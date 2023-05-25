package com.otc.kws.core.event.login;

import org.springframework.context.ApplicationEvent;

import com.otc.kws.core.domain.model.UserProfile;

public class LoginEvent extends ApplicationEvent
{
	protected UserProfile userProfile;
	
	public LoginEvent(Object source, UserProfile userProfile) 
	{
        super(source);
        this.userProfile =  userProfile;
    }
	
	public UserProfile getUserProfile() {
		return userProfile;
	}
}