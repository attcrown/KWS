package com.otc.kws.firstjobber.lib.event.internship.registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.otc.kws.firstjobber.lib.domain.entity.InternshipRegister;

@Component
public class InternshipRegisterEventPublisher 
{
	@Autowired
    protected ApplicationEventPublisher applicationEventPublisher;
	
	
	public void publishInternshipRegisterEvent(InternshipRegister internshipRegister)
	{
		InternshipRegisterEvent event = new InternshipRegisterEvent(this, internshipRegister);
		applicationEventPublisher.publishEvent(event);
	}
}