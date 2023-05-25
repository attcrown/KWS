package com.otc.kws.firstjobber.lib.event.internship.registration;

import org.springframework.context.ApplicationEvent;

import com.otc.kws.firstjobber.lib.domain.entity.InternshipRegister;

public class InternshipRegisterEvent extends ApplicationEvent
{
	protected InternshipRegister internshipRegister;
	
	public InternshipRegisterEvent(Object source, InternshipRegister internshipRegister)
	{
		super(source);
		this.internshipRegister = internshipRegister;
	}
	
	public InternshipRegister getInternshipRegister()
	{
		return internshipRegister;
	}
}