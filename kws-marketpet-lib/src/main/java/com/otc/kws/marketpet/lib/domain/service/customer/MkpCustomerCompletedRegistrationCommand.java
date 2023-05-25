package com.otc.kws.marketpet.lib.domain.service.customer;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.otc.kws.core.domain.constant.value.GenderValue;
import com.otc.kws.core.domain.service.BaseKwsCommand;
import com.otc.kws.core.domain.service.BaseKwsCommandRequest;
import com.otc.kws.core.domain.service.BaseKwsCommandResponse;
import com.otc.kws.core.domain.service.EmailService;
import com.otc.kws.core.domain.service.notification.EmailTemplateService;
import com.otc.kws.marketpet.lib.domain.entity.MkpCustomer;
import com.otc.kws.marketpet.lib.domain.entity.MkpCustomerRegister;

import lombok.Data;

public abstract class MkpCustomerCompletedRegistrationCommand extends BaseKwsCommand
{
	@Autowired
	protected MkpCustomerRegisterService mkpCustomerRegisterService;
	
	@Autowired
	protected MkpCustomerService mkpCustomerService;
	
	@Autowired
	protected EmailTemplateService emailTemplateService;
	
	@Autowired
	protected EmailService emailService;
	
	
	public abstract Response completedRegistration(Request request);
	
	
	@Data
	public static class Request extends BaseKwsCommandRequest
	{
		protected String customerRegisterId;
		protected GenderValue gender;
		protected String firstName;
		protected String lastName;
		protected Date birthDate;
		protected String mobileNo;
	}
	
	
	@Data
	public static class Response extends BaseKwsCommandResponse
	{
		protected MkpCustomerRegister mkpCustomerRegister;
		protected MkpCustomer mkpCustomer;
	}
}