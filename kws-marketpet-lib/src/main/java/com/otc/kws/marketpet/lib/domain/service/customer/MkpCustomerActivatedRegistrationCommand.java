package com.otc.kws.marketpet.lib.domain.service.customer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.otc.kws.core.domain.ServerURLProvider;
import com.otc.kws.core.domain.entity.UserAccount;
import com.otc.kws.core.domain.entity.UserRoleMapper;
import com.otc.kws.core.domain.service.BaseKwsCommand;
import com.otc.kws.core.domain.service.BaseKwsCommandRequest;
import com.otc.kws.core.domain.service.BaseKwsCommandResponse;
import com.otc.kws.core.domain.service.EmailService;
import com.otc.kws.core.domain.service.notification.EmailTemplateService;
import com.otc.kws.core.domain.service.user.UserAccountService;
import com.otc.kws.core.domain.service.user.UserRoleMapperService;
import com.otc.kws.marketpet.lib.domain.entity.MkpCustomer;
import com.otc.kws.marketpet.lib.domain.entity.MkpCustomerRegister;

import lombok.Data;

public abstract class MkpCustomerActivatedRegistrationCommand extends BaseKwsCommand
{
	@Autowired
	protected MkpCustomerService mkpCustomerService;
	
	@Autowired
	protected MkpCustomerRegisterService mkpCustomerRegisterService;
	
	@Autowired
	protected UserAccountService userAccountService;
	
	@Autowired
	protected UserRoleMapperService userRoleMapperService;
	
	@Autowired
	protected ServerURLProvider serverURLProvider;
	
	@Autowired
	protected EmailTemplateService emailTemplateService;
	
	@Autowired
	protected EmailService emailService;
	
	
	public abstract Response activatedRegistration(Request request);
	
	
	@Data
	public static class Request extends BaseKwsCommandRequest
	{
		protected String customerRegisterId;
	}
	
	
	@Data
	public static class Response extends BaseKwsCommandResponse
	{
		protected MkpCustomerRegister mkpCustomerRegister;
		protected MkpCustomer mkpCustomer;
		protected UserAccount userAccount;
		protected List<UserRoleMapper> userRoleMappers;
	}
}