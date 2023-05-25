package com.otc.kws.marketpet.lib.domain.service.customer;

import org.springframework.beans.factory.annotation.Autowired;

import com.otc.kws.core.domain.ServerURLProvider;
import com.otc.kws.core.domain.service.BaseKwsCommand;
import com.otc.kws.core.domain.service.BaseKwsCommandRequest;
import com.otc.kws.core.domain.service.BaseKwsCommandResponse;
import com.otc.kws.marketpet.lib.domain.service.MkpConfigService;

import lombok.Data;

public abstract class MkpCustomerGenerateInvitedRegisterLinkCommand extends BaseKwsCommand
{
	@Autowired
	protected ServerURLProvider serverURLProvider;
	
	@Autowired
	protected MkpConfigService mkpConfigService;
	
	@Autowired
	protected MkpCustomerService mkpCustomerService;
	
	
	public abstract Response generateInvitedRegisterLink(Request request);
	
	
	@Data
	public static class Request extends BaseKwsCommandRequest
	{
		protected String customerId;
	}
	
	
	@Data
	public static class Response extends BaseKwsCommandResponse
	{
		protected String invitedRegisterLink;
	}
}