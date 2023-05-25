package com.otc.kws.firstjobber.lib.event.internship.registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.otc.kws.core.domain.service.notification.NotificationSendNotifyCommand;
import com.otc.kws.firstjobber.lib.domain.service.InternshipRegisterService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class InternshipUploadTestEventListener 
{
	@Autowired
	protected InternshipRegisterService internshipRegisterService;
	
	
	@EventListener(InternshipUploadTestEvent.class)
	@Async
	public void handleInternshipUploadTestEvent(InternshipUploadTestEvent event)
	{
		log.info("### InternshipUploadTestEventListener.handleInternshipUploadTestEvent ###");
		
		NotificationSendNotifyCommand.Response notifyResponse = internshipRegisterService.notifyInternshipUploadTest(req -> {
			req.setEvent(InternshipUploadTestEvent.class.getName());
			req.setInternshipRegister(event.getInternshipRegister());
			req.setInternshipRegisterTest(event.getInternshipRegisterTest());
		}).getSendNotifyResponse();
		
		log.info("notifyResponse => {}", notifyResponse);
	}
}