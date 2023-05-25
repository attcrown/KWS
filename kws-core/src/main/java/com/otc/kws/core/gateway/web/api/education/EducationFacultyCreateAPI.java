package com.otc.kws.core.gateway.web.api.education;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.otc.kws.core.domain.entity.EducationFaculty;
import com.otc.kws.core.domain.exception.DuplicateEntityException;
import com.otc.kws.core.domain.service.education.EducationFacultyService;
import com.otc.kws.core.gateway.web.api.BaseKwsAPI;
import com.otc.kws.core.gateway.web.api.ResponseMessage;

import lombok.Data;

@RestController
@RequestMapping(BaseKwsAPI.PREFIX_PATH + "/core/education/faculty")
public class EducationFacultyCreateAPI extends BaseKwsAPI
{
	@Autowired
	protected EducationFacultyService educationFacultyService;
	
	
	@PostMapping("/create")
	public ResponseEntity create(HttpServletRequest request, @RequestBody RequestMessage requestMessage) throws DuplicateEntityException
	{
		logger.info("### EducationFacultyCreateAPI.create ###");
		logger.info("requestMessage => " + requestMessage);
		
		EducationFaculty createdEducationFaculty = educationFacultyService.create(req -> {
			setupServiceRequest(request, req);
			req.setEducationFaculty(requestMessage.getEducationFaculty());
		}).getCreatedEducationFaculty();
		
		BodyResponseMessage bodyResponseMessage = new BodyResponseMessage();
		bodyResponseMessage.setCreatedEducationFaculty(createdEducationFaculty);
		
		return replySuccess(request, bodyResponseMessage);
	}
	
	
	@Data
	public static class RequestMessage
	{
		protected EducationFaculty educationFaculty;
	}
	
	
	@Data
	public static class BodyResponseMessage extends ResponseMessage.BaseBody
	{
		protected EducationFaculty createdEducationFaculty;
	}
}