package com.otc.kws.marketpet.lib.gateway.web.api.customer;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.otc.kws.core.gateway.web.api.ResponseMessage;
import com.otc.kws.marketpet.lib.domain.service.customer.MkpCustomerService;

import com.otc.kws.marketpet.lib.gateway.web.api.BaseKwsMarketPetAPI;

import lombok.Data;

@RestController
@RequestMapping(BaseKwsMarketPetAPI.PREFIX_PATH + "/customer")
public class MkpCustomerGenerateInvitedRegisterLinkAPI extends BaseKwsMarketPetAPI
{
	@Autowired
	protected MkpCustomerService mkpCustomerService;
	
	
	@PostMapping("/generate-invited-register-link")
	public ResponseEntity<?> generetInvitedRegisterLink(HttpServletRequest request, @RequestBody RequestMessage requestMessage)
	{
		logger.info("### MkpCustomerGenerateInvitedRegisterLinkAPI.generetInvitedRegisterLink ###");
		logger.info("requestMessage => " + requestMessage);
		
		String invitedRegisterLink = mkpCustomerService.generateInvitedRegisterLink(req -> {
			setupServiceRequest(request, req);
			req.setCustomerId(requestMessage.getCustomerId());
		}).getInvitedRegisterLink();
		
		logger.info("invitedRegisterLink => " + invitedRegisterLink);
		
		BodyResponseMessage bodyResponseMessage = new BodyResponseMessage();
		bodyResponseMessage.setInvitedRegisterLink(invitedRegisterLink);
		
		return replySuccess(request, bodyResponseMessage);
	}
	
	
	@Data
	public static class RequestMessage
	{
		protected String customerId;
	}
	
	
	@Data
	public static class BodyResponseMessage extends ResponseMessage.BaseBody
	{
		protected String invitedRegisterLink;
	}
}