package com.otc.kws.core.gateway.web.api.education;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.otc.kws.core.domain.entity.EducationMajor;
import com.otc.kws.core.domain.exception.EntityNotFoundException;
import com.otc.kws.core.domain.service.education.EducationMajorService;
import com.otc.kws.core.gateway.web.api.BaseKwsAPI;
import com.otc.kws.core.gateway.web.api.ResponseMessage;

import lombok.Data;

@RestController
@RequestMapping(BaseKwsAPI.PREFIX_PATH + "/core/education/major")
public class EducationMajorRemoveAPI extends BaseKwsAPI
{
	@Autowired
	protected EducationMajorService educationMajorService;
	
	
	@PostMapping("/remove")
	public ResponseEntity remove(HttpServletRequest request, @RequestBody RequestMessage requestMessage) throws EntityNotFoundException
	{
		logger.info("### EducationMajorRemoveAPI.remove ###");
		logger.info("requestMessage => " + requestMessage);
		
		EducationMajor removedEducationMajor = educationMajorService.remove(req -> {
			setupServiceRequest(request, req);
			req.setEducationMajorId(requestMessage.getEducationMajorId());
		}).getRemovedEducationMajor();
		
		BodyResponseMessage bodyResponseMessage = new BodyResponseMessage();
		bodyResponseMessage.setRemovedEducationMajor(removedEducationMajor);
		
		return replySuccess(request, bodyResponseMessage);
	}
	
	
	@Data
	public static class RequestMessage
	{
		protected String educationMajorId;
	}
	
	
	@Data
	public static class BodyResponseMessage extends ResponseMessage.BaseBody
	{
		protected EducationMajor removedEducationMajor;
	}
}