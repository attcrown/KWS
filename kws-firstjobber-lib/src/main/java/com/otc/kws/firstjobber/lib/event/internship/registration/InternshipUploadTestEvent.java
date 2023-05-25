package com.otc.kws.firstjobber.lib.event.internship.registration;

import org.springframework.context.ApplicationEvent;

import com.otc.kws.firstjobber.lib.domain.entity.FjbInternshipRegisterTest;
import com.otc.kws.firstjobber.lib.domain.entity.InternshipRegister;

public class InternshipUploadTestEvent extends ApplicationEvent
{
	protected InternshipRegister internshipRegister;
	protected FjbInternshipRegisterTest internshipRegisterTest;
	
	public InternshipUploadTestEvent(Object source, InternshipRegister internshipRegister, FjbInternshipRegisterTest internshipRegisterTest)
	{
		super(source);
		this.internshipRegister = internshipRegister;
		this.internshipRegisterTest = internshipRegisterTest;
	}
	
	public InternshipRegister getInternshipRegister()
	{
		return internshipRegister;
	}
	
	public FjbInternshipRegisterTest getInternshipRegisterTest()
	{
		return internshipRegisterTest;
	}
}