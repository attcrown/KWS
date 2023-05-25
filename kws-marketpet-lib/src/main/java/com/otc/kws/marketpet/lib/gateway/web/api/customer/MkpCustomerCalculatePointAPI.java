package com.otc.kws.marketpet.lib.gateway.web.api.customer;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.otc.kws.core.gateway.web.api.ResponseMessage;
import com.otc.kws.marketpet.lib.domain.service.customer.MkpCustomerCalculatePointCommand;
import com.otc.kws.marketpet.lib.domain.service.customer.MkpCustomerService;
import com.otc.kws.marketpet.lib.gateway.web.api.BaseKwsMarketPetAPI;

import lombok.Data;

@RestController
@RequestMapping(BaseKwsMarketPetAPI.PREFIX_PATH + "/customer")
public class MkpCustomerCalculatePointAPI extends BaseKwsMarketPetAPI
{
	@Autowired
	protected MkpCustomerService mkpCustomerService;
	
	
	@PostMapping("/calculate-point")
	public ResponseEntity<?> calculatePoint(HttpServletRequest request, @RequestBody RequestMessage requestMessage)
	{
		logger.info("### MkpCustomerCalculatePointAPI.calculatePoint ###");
		logger.info("requestMessage => " + requestMessage);
		
		MkpCustomerCalculatePointCommand.Response calculatePointResponse = mkpCustomerService.calculatePoint(req -> {
			setupServiceRequest(request, req);
			req.setCustomerId(requestMessage.getCustomerId());
			req.setPrice(requestMessage.getPrice());
		});
		
		BodyResponseMessage bodyResponseMessage = new BodyResponseMessage();
		bodyResponseMessage.setPointAcquires(calculatePointResponse.getPointAcquires());
		
		return replySuccess(request, bodyResponseMessage);
	}
	
	
	@Data
	public static class RequestMessage
	{
		protected String customerId;
		protected BigDecimal price;
	}
	
	
	public static class BodyResponseMessage extends MkpCustomerCalculatePointCommand.Response implements ResponseMessage.Body
	{
		
	}
}