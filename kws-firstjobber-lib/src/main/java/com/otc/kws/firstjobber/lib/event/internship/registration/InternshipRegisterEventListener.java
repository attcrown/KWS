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
public class InternshipRegisterEventListener 
{	
	@Autowired
	protected InternshipRegisterService internshipRegisterService;
	
	
	@EventListener(InternshipRegisterEvent.class)
	@Async
	public void handleInternshipRegisterEvent(InternshipRegisterEvent event)
	{
		log.info("### InternshipRegisterEventListener.handleInternshipRegisterEvent ###");
		
		NotificationSendNotifyCommand.Response notifyResponse = internshipRegisterService.notifyInternshipRegister(req -> {
			req.setEvent(InternshipRegisterEvent.class.getName());
			req.setInternshipRegister(event.getInternshipRegister());
		}).getSendNotifyResponse();
		
		log.info("notifyResponse => {}", notifyResponse);
	}
}