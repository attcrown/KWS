package com.otc.kws.marketpet.lib.domain.service.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.otc.kws.core.domain.ServerURLProvider;
import com.otc.kws.core.domain.service.BaseKwsCommand;
import com.otc.kws.core.domain.service.BaseKwsCommandRequest;
import com.otc.kws.core.domain.service.BaseKwsCommandResponse;
import com.otc.kws.core.domain.service.EmailService;
import com.otc.kws.core.domain.service.notification.EmailTemplateService;
import com.otc.kws.marketpet.lib.domain.constant.value.MkpCustomerActivatedChannelValue;
import com.otc.kws.marketpet.lib.domain.entity.MkpCustomerRegister;
import com.otc.kws.marketpet.lib.domain.service.MkpConfigService;

import lombok.Data;

public abstract class MkpCustomerRegisterCommand extends BaseKwsCommand
{
	@Autowired
	protected MkpCustomerRegisterService mkpCustomerRegisterService;
	
	@Autowired
	protected MkpConfigService mkpConfigService;
	
	@Autowired
	protected ServerURLProvider serverURLProvider;
	
	@Autowired
	protected PasswordEncoder passwordEncoder;
	
	@Autowired
	protected EmailService emailService;
	
	@Autowired
	protected EmailTemplateService emailTemplateService;
	
	
	public abstract Response register(Request request);
	
	
	@Data
	public static class Request extends BaseKwsCommandRequest
	{
		protected String invitedCustomerId;
		protected String mobileNo;
		protected String email;
		protected String password;
		protected MkpCustomerActivatedChannelValue activatedChannel = MkpCustomerActivatedChannelValue.Email;
	}
	
	
	@Data
	public static class Response extends BaseKwsCommandResponse
	{
		protected MkpCustomerRegister mkpCustomerRegister;
		protected String activationLink;
	}
}