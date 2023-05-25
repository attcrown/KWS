package com.otc.kws.core.gateway.web.api.salesman;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.otc.kws.core.domain.entity.Salesman;
import com.otc.kws.core.domain.service.salesman.SalesmanService;
import com.otc.kws.core.gateway.web.api.BaseKwsAPI;
import com.otc.kws.core.gateway.web.api.ResponseMessage;

import lombok.Data;

@RestController
@RequestMapping(BaseKwsAPI.PREFIX_PATH + "/core/salesman")
public class SalesmanRemoveAPI extends BaseKwsAPI
{
	@Autowired
	protected SalesmanService salesmanService;
	
	
	@PostMapping("remove")
	public ResponseEntity<?> remove(HttpServletRequest request, @RequestBody RequestMessage requestMessage)
	{
		logger.info("### {}.remove ###", getClass().getSimpleName());
		logger.info("requestMessage => {}", requestMessage);
		
		Salesman removedSalesman = salesmanService.remove(req -> {
			setupServiceRequest(request, req);
			req.setSalesmanId(requestMessage.getSalesmanId());
		}).getRemovedSalesman();
		
		BodyResponseMessage bodyResponseMessage = new BodyResponseMessage();
		bodyResponseMessage.setRemovedSalesman(removedSalesman);
		
		return replySuccess(request, bodyResponseMessage);
	}
	
	
	@Data
	public static class RequestMessage
	{
		protected String salesmanId;
	}
	
	
	@Data
	public static class BodyResponseMessage extends ResponseMessage.BaseBody
	{
		protected Salesman removedSalesman;
	}
}