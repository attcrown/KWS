package com.otc.kws.marketpet.lib.gateway.web.api.customer;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.otc.kws.core.gateway.web.api.ResponseMessage;
import com.otc.kws.marketpet.lib.domain.entity.MkpCustomer;
import com.otc.kws.marketpet.lib.domain.entity.MkpCustomerRegister;
import com.otc.kws.marketpet.lib.domain.service.customer.MkpCustomerActivatedRegistrationCommand;
import com.otc.kws.marketpet.lib.domain.service.customer.MkpCustomerRegisterService;
import com.otc.kws.marketpet.lib.gateway.web.api.BaseKwsMarketPetAPI;

import lombok.Data;

@RestController
@RequestMapping(BaseKwsMarketPetAPI.PREFIX_PATH + "/customer/registration")
public class MkpCustomerActivatedRegistrationAPI extends BaseKwsMarketPetAPI
{
	@Autowired
	protected MkpCustomerRegisterService mkpCustomerRegisterService;
	
	
	@PostMapping("/activated")
	public ResponseEntity<?> activatedRegistration(HttpServletRequest request, @RequestBody RequestMessage requestMessage)
	{
		logger.info("### MkpCustomerActivatedRegistrationAPI.activatedRegistration ###");
		logger.info("requestMessage => " + requestMessage);
		
		MkpCustomerActivatedRegistrationCommand.Response activatedRegistrationResponse = mkpCustomerRegisterService.activatedRegistration(req -> {
			setupServiceRequest(request, req);
			req.setCustomerRegisterId(requestMessage.getCustomerRegisterId());
		});
		
		BodyResponseMessage bodyResponseMessage = new BodyResponseMessage();
		bodyResponseMessage.setMkpCustomer(activatedRegistrationResponse.getMkpCustomer());
		bodyResponseMessage.setMkpCustomerRegister(activatedRegistrationResponse.getMkpCustomerRegister());
		
		return replySuccess(request, bodyResponseMessage);
	}
	
	
	@Data
	public static class RequestMessage
	{
		protected String customerRegisterId;
	}
	
	
	@Data
	public static class BodyResponseMessage extends ResponseMessage.BaseBody
	{
		protected MkpCustomerRegister mkpCustomerRegister;
		protected MkpCustomer mkpCustomer;
	}
}