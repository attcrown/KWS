package com.otc.kws.marketpet.lib.gateway.web.api.customer;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.otc.kws.core.gateway.web.api.ResponseMessage;
import com.otc.kws.marketpet.lib.domain.constant.value.MkpCustomerActivatedChannelValue;
import com.otc.kws.marketpet.lib.domain.entity.MkpCustomerRegister;
import com.otc.kws.marketpet.lib.domain.service.customer.MkpCustomerRegisterCommand;
import com.otc.kws.marketpet.lib.domain.service.customer.MkpCustomerRegisterService;
import com.otc.kws.marketpet.lib.gateway.web.api.BaseKwsMarketPetAPI;

import lombok.Data;

@RestController
@RequestMapping(BaseKwsMarketPetAPI.PREFIX_PATH + "/customer/registration")
public class MkpCustomerRegisterAPI extends BaseKwsMarketPetAPI
{
	@Autowired
	protected MkpCustomerRegisterService mkpCustomerRegisterService;
	
	
	@PostMapping("/register")
	public ResponseEntity<?> register(HttpServletRequest request, @RequestBody RequestMessage requestMessage)
	{
		logger.info("### MkpCustomerRegisterAPI.register ###");
		logger.info("requestMessage => " + requestMessage);
		
		MkpCustomerRegisterCommand.Response registerResponse = mkpCustomerRegisterService.register(req -> {
			setupServiceRequest(request, req);
			req.setInvitedCustomerId(requestMessage.getInvitedCustomerId());
			req.setMobileNo(requestMessage.getMobileNo());
			req.setEmail(requestMessage.getEmail());
			req.setPassword(requestMessage.getPassword());
			req.setActivatedChannel(requestMessage.getActivatedChannel());
		});
		
		BodyResponseMessage bodyResponseMessage = new BodyResponseMessage();
		bodyResponseMessage.setMkpCustomerRegister(registerResponse.getMkpCustomerRegister());
		bodyResponseMessage.setActivationLink(registerResponse.getActivationLink());
		
		return replySuccess(request, bodyResponseMessage);
	}
	
	
	@Data
	public static class RequestMessage
	{
		protected String invitedCustomerId;
		protected String mobileNo;
		protected String email;
		protected String password;
		protected MkpCustomerActivatedChannelValue activatedChannel;
	}
	
	
	@Data
	public static class BodyResponseMessage extends ResponseMessage.BaseBody
	{
		protected MkpCustomerRegister mkpCustomerRegister;
		protected String activationLink;
	}
}