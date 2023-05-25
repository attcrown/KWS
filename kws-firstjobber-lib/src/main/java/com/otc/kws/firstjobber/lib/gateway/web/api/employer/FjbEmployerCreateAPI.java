package com.otc.kws.firstjobber.lib.gateway.web.api.employer;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.otc.kws.core.gateway.web.api.ResponseMessage;
import com.otc.kws.firstjobber.lib.domain.profile.FjbEmployerProfile;
import com.otc.kws.firstjobber.lib.domain.service.employer.FjbEmployerCreateCommand;
import com.otc.kws.firstjobber.lib.domain.service.employer.FjbEmployerService;
import com.otc.kws.firstjobber.lib.gateway.web.api.BaseKwsFirstJobberAPI;

import lombok.Data;

@RestController
@RequestMapping(BaseKwsFirstJobberAPI.PREFIX_PATH + "/employer/create")
public class FjbEmployerCreateAPI extends BaseKwsFirstJobberAPI
{
	@Autowired
	protected FjbEmployerService fjbEmployerService;
	
	
	@PostMapping
	public ResponseEntity<?> create(HttpServletRequest request, @RequestBody RequestMessage requestMessage)
	{
		logger.info("### FjbEmployerCreateAPI.create ###");
		logger.info("requestMessage => " + requestMessage);
		
		FjbEmployerCreateCommand.Response createEmployerResponse = fjbEmployerService.createEmployer(req -> {
			setupServiceRequest(request, req);
			req.setEmployerProfile(requestMessage.getEmployerProfile());
		});
		
		BodyResponseMessage bodyResponseMessage = new BodyResponseMessage();
		bodyResponseMessage.setEmployerProfile(createEmployerResponse.getEmployerProfile());
		
		return replySuccess(request, bodyResponseMessage);
	}
	
	
	@Data
	public static class RequestMessage
	{
		protected FjbEmployerProfile employerProfile;
	}
	
	
	@Data
	public static class BodyResponseMessage extends ResponseMessage.BaseBody
	{
		protected FjbEmployerProfile employerProfile;
	}
}