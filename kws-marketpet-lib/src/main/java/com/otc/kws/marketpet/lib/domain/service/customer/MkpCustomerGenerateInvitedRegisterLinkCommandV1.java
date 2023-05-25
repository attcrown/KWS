package com.otc.kws.marketpet.lib.domain.service.customer;

import org.springframework.stereotype.Component;

import com.otc.kws.core.domain.constant.value.MasterStatusValue;
import com.otc.kws.core.domain.exception.KwsRuntimeException;
import com.otc.kws.marketpet.lib.domain.entity.MkpCustomer;

@Component
public class MkpCustomerGenerateInvitedRegisterLinkCommandV1 extends MkpCustomerGenerateInvitedRegisterLinkCommand
{
	@Override
	public Response generateInvitedRegisterLink(Request request) 
	{
		MkpCustomer mkpCustomer = mkpCustomerService.findById(request.getCustomerId());
		
		if(mkpCustomer == null) {
			throw new KwsRuntimeException("Customer id ["+request.getCustomerId()+"] Not Found");
		}
		
		if(mkpCustomer.getStatus() != MasterStatusValue.Active) {
			throw new KwsRuntimeException("Customer id ["+request.getCustomerId()+"] cannot generate invited registration link caused status not 'Active'");
		}
		
		String invitedRegisterLink = mkpConfigService.getRegistrationInvitedRegisterLink();
		invitedRegisterLink = invitedRegisterLink.replace("${server_url}", serverURLProvider.getServerURL());
		invitedRegisterLink = invitedRegisterLink.replace("${invited_customer_id}", request.getCustomerId());
		
		Response response = new Response();
		response.setInvitedRegisterLink(invitedRegisterLink);
		
		return response;
	}
}