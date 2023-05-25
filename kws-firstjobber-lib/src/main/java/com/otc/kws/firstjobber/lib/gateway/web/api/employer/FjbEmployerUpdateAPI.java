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
import com.otc.kws.firstjobber.lib.domain.service.employer.FjbEmployerService;
import com.otc.kws.firstjobber.lib.gateway.web.api.BaseKwsFirstJobberAPI;

import lombok.Data;

@RestController
@RequestMapping(BaseKwsFirstJobberAPI.PREFIX_PATH + "/employer/update")
public class FjbEmployerUpdateAPI extends BaseKwsFirstJobberAPI
{
	@Autowired
	protected FjbEmployerService fjbEmployerService;
	
	
	@PostMapping
	public ResponseEntity<?> update(HttpServletRequest request, @RequestBody RequestMessage requestMessage)
	{
		logger.info("### FjbEmployerUpdateAPI.update ###");
		logger.info("requestMessage => " + requestMessage);
		
		FjbEmployerProfile employerProfile = fjbEmployerService.updateEmployer(req -> {
			setupServiceRequest(request, req);
			req.setEmployerProfile(requestMessage.getEmployerProfile());
		}).getEmployerProfile();
		
		BodyResponseMessage bodyResponseMessage = new BodyResponseMessage();
		bodyResponseMessage.setEmployerProfile(employerProfile);
		
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