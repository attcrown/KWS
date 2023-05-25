package com.otc.kws.core.event.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.otc.kws.core.domain.model.UserProfile;

@Component
public class LoginEventPublisher 
{
	@Autowired
    protected ApplicationEventPublisher applicationEventPublisher;
	
	
	public void publishLoginEvent(UserProfile userProfile) 
	{
        LoginEvent event = new LoginEvent(this, userProfile);
        applicationEventPublisher.publishEvent(event);
    }
}