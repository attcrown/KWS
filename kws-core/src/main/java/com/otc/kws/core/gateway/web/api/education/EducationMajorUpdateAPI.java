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
public class EducationMajorUpdateAPI extends BaseKwsAPI
{
	@Autowired
	protected EducationMajorService educationMajorService;
	
	
	@PostMapping("/update")
	public ResponseEntity update(HttpServletRequest request, @RequestBody RequestMessage requestMessage) throws EntityNotFoundException
	{
		logger.info("### EducationMajorUpdateAPI.update ###");
		logger.info("requestMessage => " + requestMessage);
		
		EducationMajor updatedEducationMajor = educationMajorService.update(req -> {
			setupServiceRequest(request, req);
			req.setEducationMajorId(requestMessage.getEducationMajorId());
			req.setEducationMajor(requestMessage.getEducationMajor());
		}).getUpdatedEducationMajor();
		
		BodyResponseMessage bodyResponseMessage = new BodyResponseMessage();
		bodyResponseMessage.setUpdatedEducationMajor(updatedEducationMajor);
		
		return replySuccess(request, bodyResponseMessage);
	}
	
	
	@Data
	public static class RequestMessage
	{
		protected String educationMajorId;
		protected EducationMajor educationMajor;
	}
	
	
	@Data
	public static class BodyResponseMessage extends ResponseMessage.BaseBody
	{
		protected EducationMajor updatedEducationMajor;
	}
}