package com.otc.kws.firstjobber.lib.event.internship.registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.otc.kws.firstjobber.lib.domain.entity.FjbInternshipRegisterTest;
import com.otc.kws.firstjobber.lib.domain.entity.InternshipRegister;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class InternshipUploadTestEventPublisher 
{
	@Autowired
    protected ApplicationEventPublisher applicationEventPublisher;
	
	
	public void publishInternshipUploadTestEvent(InternshipRegister internshipRegister, FjbInternshipRegisterTest internshipRegisterTest)
	{
		log.info("### InternshipUploadTestEventPublisher.publishInternshipUploadTestEvent ###");
		InternshipUploadTestEvent event = new InternshipUploadTestEvent(this, internshipRegister, internshipRegisterTest);
		applicationEventPublisher.publishEvent(event);
	}
}