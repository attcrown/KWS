package com.otc.kws.marketpet.lib.domain.service.customer;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.otc.kws.core.domain.entity.EmailTemplate;
import com.otc.kws.core.domain.exception.KwsRuntimeException;
import com.otc.kws.marketpet.lib.domain.constant.value.MkpCustomerActivatedChannelValue;
import com.otc.kws.marketpet.lib.domain.constant.value.MkpCustomerRegisterStatusValue;
import com.otc.kws.marketpet.lib.domain.entity.MkpCustomerRegister;

@Component
public class MkpCustomerRegisterCommandV1 extends MkpCustomerRegisterCommand
{
	@Override
	public Response register(Request request) 
	{
		MkpCustomerRegister mkpCustomerRegister = new MkpCustomerRegister();
		mkpCustomerRegister.setId(UUID.randomUUID().toString());
		mkpCustomerRegister.setInvitedCustomerId(request.getInvitedCustomerId());
		mkpCustomerRegister.setMobileNo(request.getMobileNo());
		mkpCustomerRegister.setEmail(request.getEmail());
		mkpCustomerRegister.setPassword(passwordEncoder.encode(request.getPassword()));
		mkpCustomerRegister.setActivatedChannel(request.getActivatedChannel());
		mkpCustomerRegister.setRegisterStatus(MkpCustomerRegisterStatusValue.Pending);
		mkpCustomerRegister.setRegisteredAt(request.getPerformedAt());
		
		try {
			mkpCustomerRegister = mkpCustomerRegisterService.create(mkpCustomerRegister);
			logger.info("create mkpCustomerRegister => [" + mkpCustomerRegister + "]");
		} catch(Exception ex) {
			throw new KwsRuntimeException(ex);
		}
		
		String activationLink = mkpConfigService.getRegistrationActivationLink();
		activationLink = activationLink.replace("${server_url}", serverURLProvider.getServerURL());
		activationLink = activationLink.replace("${customer_register_id}", mkpCustomerRegister.getId());
		logger.info("activationLink => " + activationLink);
		
		if(mkpCustomerRegister.getActivatedChannel() == MkpCustomerActivatedChannelValue.Email) {
			sendActivationEmail(mkpCustomerRegister, activationLink);
		}
		
		Response response = new Response();
		response.setMkpCustomerRegister(mkpCustomerRegister);
		response.setActivationLink(activationLink);
		
		return response;
	}
	
	
	protected void sendActivationEmail(MkpCustomerRegister mkpCustomerRegister, String activationLink)
	{
		logger.info("### " + getClass().getSimpleName() + ".sendActivationEmail ###");
		
		EmailTemplate emailTemplate = emailTemplateService.findById("mkp.customer.registration.register");
		
		if(emailTemplate != null) {
			String fromEmail = emailTemplate.getFromEmail();
			String fromName = emailTemplate.getFromName();
			List<String> mailTos = Arrays.asList(mkpCustomerRegister.getEmail());
			String subject = emailTemplate.getSubjectTemplate();
			String content = emailTemplate.getContentTemplate().replace("${activationLink}", activationLink);
			
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