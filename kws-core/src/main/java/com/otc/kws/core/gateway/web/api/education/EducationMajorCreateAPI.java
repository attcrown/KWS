package com.otc.kws.core.gateway.web.api.education;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.otc.kws.core.domain.entity.EducationMajor;
import com.otc.kws.core.domain.exception.DuplicateEntityException;
import com.otc.kws.core.domain.service.education.EducationMajorService;
import com.otc.kws.core.gateway.web.api.BaseKwsAPI;
import com.otc.kws.core.gateway.web.api.ResponseMessage;

import lombok.Data;

@RestController
@RequestMapping(BaseKwsAPI.PREFIX_PATH + "/core/education/major")
public class EducationMajorCreateAPI extends BaseKwsAPI
{
	@Autowired
	protected EducationMajorService educationMajorService;
	
	
	@PostMapping("/create")
	public ResponseEntity create(HttpServletRequest request, @RequestBody RequestMessage requestMessage) throws DuplicateEntityException
	{
		logger.info("### EducationMajorCreateAPI.create ###");
		logger.info("requestMessage => " + requestMessage);
		
		EducationMajor createdEducationMajor = educationMajorService.create(req -> {
			setupServiceRequest(request, req);
			req.setEducationMajor(requestMessage.getEducationMajor());
		}).getCreatedEducationMajor();
		
		BodyResponseMessage bodyResponseMessage = new BodyResponseMessage();
		bodyResponseMessage.setCreatedEducationMajor(createdEducationMajor);
		
		return replySuccess(request, bodyResponseMessage);
	}
	
	
	@Data
	public static class RequestMessage
	{
		protected EducationMajor educationMajor;
	}
	
	
	@Data
	public static class BodyResponseMessage extends ResponseMessage.BaseBody
	{
		protected EducationMajor createdEducationMajor;
	}
}