package com.otc.kws.marketpet.lib.domain.service.customer;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import com.otc.kws.core.domain.constant.value.MasterStatusValue;
import com.otc.kws.core.domain.entity.EmailTemplate;
import com.otc.kws.core.domain.exception.KwsRuntimeException;
import com.otc.kws.marketpet.lib.domain.constant.value.MkpCustomerRegisterStatusValue;
import com.otc.kws.marketpet.lib.domain.entity.MkpCustomer;
import com.otc.kws.marketpet.lib.domain.entity.MkpCustomerRegister;

@Component
public class MkpCustomerCompletedRegistrationCommandV1 extends MkpCustomerCompletedRegistrationCommand
{
	@Override
	public Response completedRegistration(Request request) 
	{
		MkpCustomerRegister mkpCustomerRegister = mkpCustomerRegisterService.findById(request.getCustomerRegisterId());
		
		if(mkpCustomerRegister == null) {
			throw new KwsRuntimeException("CustomerRegister id ["+request.getCustomerRegisterId()+"] Not Found");
		}
		
		MkpCustomer mkpCustomer = mkpCustomerService.findById(mkpCustomerRegister.getCustomerId());
		
		if(mkpCustomer == null) {
			throw new KwsRuntimeException("Customer id ["+mkpCustomerRegister.getCustomerId()+"] Not Found");
		}
		
		mkpCustomerRegister.setGender(request.getGender());
		mkpCustomerRegister.setFirstName(request.getFirstName());
		mkpCustomerRegister.setLastName(request.getLastName());
		mkpCustomerRegister.setBirthDate(request.getBirthDate());
		mkpCustomerRegister.setMobileNo(request.getMobileNo());
		mkpCustomerRegister.setRegisterStatus(MkpCustomerRegisterStatusValue.Completed);
		mkpCustomerRegister.setCompletedAt(request.getPerformedAt());
		try {
			mkpCustomerRegister = mkpCustomerRegisterService.update(mkpCustomerRegister);
			logger.info("update mkpCustomerRegister => [" + mkpCustomerRegister + "]");
		} catch(Exception ex) {
			throw new KwsRuntimeException(ex);
		}
		
		mkpCustomer.setGender(request.getGender());
		mkpCustomer.setFirstName(request.getFirstName());
		mkpCustomer.setLastName(request.getLastName());
		mkpCustomer.setBirthDate(request.getBirthDate());
		mkpCustomer.setMobileNo(request.getMobileNo());
		mkpCustomer.setStatus(MasterStatusValue.Active);
		mkpCustomer.setLastModifiedBy(mkpCustomer.getUserId());
		mkpCustomer.setLastModifiedAt(request.getPerformedAt());
		try {
			mkpCustomer = mkpCustomerService.update(mkpCustomer);
			logger.info("update mkpCustomer => [" + mkpCustomer + "]");
		} catch(Exception ex) {
			throw new KwsRuntimeException(ex);
		}
		
		sendEmail(mkpCustomerRegister, mkpCustomer);
		
		Response response = new Response();
		response.setMkpCustomerRegister(mkpCustomerRegister);
		response.setMkpCustomer(mkpCustomer);
		
		return response;
	}
	
	protected void sendEmail(MkpCustomerRegister mkpCustomerRegister, MkpCustomer mkpCustomer)
	{
		logger.info("### " + getClass().getSimpleName() + ".sendEmail ###");
		
		EmailTemplate emailTemplate = emailTemplateService.findById("mkp.customer.registration.completed");
		
		if(emailTemplate != null) {
			String fromEmail = emailTemplate.getFromEmail();
			String fromName = emailTemplate.getFromName();
			List<String> mailTos = Arrays.asList(mkpCustomerRegister.getEmail());
			String customerName = mkpCustomer.getFirstName() + " " + mkpCustomer.getLastName();
			String subject = emailTemplate.getSubjectTemplate();
			String content = emailTemplate.getContentTemplate().replace("${customerName}", customerName);
			
			logger.info("fromEmail => " + fromEmail);
			logger.info("fromName => " + fromName);
			logger.info("mailTos => " + mailTos);
			logger.info("subject => " + subject);
			logger.info("content => " + content);
			
			if(fromEmail != null && fromName != null) {
				emailService.sendMail(fromEmail, fromName, mailTos, subject, content);
			} else {
				emailService.sendMail(mailTos, subject, content);
			}
		}
	}
}