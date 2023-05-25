package com.otc.kws.marketpet.lib.gateway.web.api.customer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.otc.kws.core.domain.constant.KwsConst;
import com.otc.kws.core.domain.constant.value.GenderValue;
import com.otc.kws.core.domain.exception.KwsRuntimeException;
import com.otc.kws.core.gateway.web.api.ResponseMessage;
import com.otc.kws.marketpet.lib.domain.entity.MkpCustomer;
import com.otc.kws.marketpet.lib.domain.entity.MkpCustomerRegister;
import com.otc.kws.marketpet.lib.domain.service.customer.MkpCustomerCompletedRegistrationCommand;
import com.otc.kws.marketpet.lib.domain.service.customer.MkpCustomerRegisterService;
import com.otc.kws.marketpet.lib.gateway.web.api.BaseKwsMarketPetAPI;

import lombok.Data;

@RestController
@RequestMapping(BaseKwsMarketPetAPI.PREFIX_PATH + "/customer/registration")
public class MkpCustomerCompletedRegistrationAPI extends BaseKwsMarketPetAPI
{
	protected final SimpleDateFormat dateFormat = new SimpleDateFormat(KwsConst.DEFAULT_DATE_FORMAT, Locale.ENGLISH);
	
	
	@Autowired
	protected MkpCustomerRegisterService mkpCustomerRegisterService;
	
	
	@PostMapping("/completed")
	public ResponseEntity<?> completedRegistration(HttpServletRequest request, @RequestBody RequestMessage requestMessage)
	{
		logger.info("### MkpCustomerCompletedRegistrationAPI.completedRegistration ###");
		logger.info("requestMessage => " + requestMessage);
		
		MkpCustomerCompletedRegistrationCommand.Response completedRegistrationResponse = mkpCustomerRegisterService.completedRegistration(req -> {
			Date birthDate = null;
			try {
				birthDate = dateFormat.parse(requestMessage.getBirthDate());
			} catch(Exception ex) {
				throw new KwsRuntimeException(ex);
			}
			
			setupServiceRequest(request, req);
			req.setCustomerRegisterId(requestMessage.getCustomerRegisterId());
			req.setGender(requestMessage.getGender());
			req.setFirstName(requestMessage.getFirstName());
			req.setLastName(requestMessage.getLastName());
			req.setBirthDate(birthDate);
			req.setMobileNo(requestMessage.getMobileNo());
		});
		
		BodyResponseMessage bodyResponseMessage = new BodyResponseMessage();
		bodyResponseMessage.setMkpCustomerRegister(completedRegistrationResponse.getMkpCustomerRegister());
		bodyResponseMessage.setMkpCustomer(completedRegistrationResponse.getMkpCustomer());
		
		return replySuccess(request, bodyResponseMessage);
	}
	
	
	@Data
	public static class RequestMessage
	{
		protected String customerRegisterId;
		protected GenderValue gender;
		protected String firstName;
		protected String lastName;
		protected String birthDate;
		protected String mobileNo;
	}
	
	
	@Data
	public static class BodyResponseMessage extends ResponseMessage.BaseBody
	{
		protected MkpCustomerRegister mkpCustomerRegister;
		protected MkpCustomer mkpCustomer;
	}
}