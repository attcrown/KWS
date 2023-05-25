package com.otc.kws.core.gateway.web.api.education;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.otc.kws.core.domain.entity.EducationFaculty;
import com.otc.kws.core.domain.exception.EntityNotFoundException;
import com.otc.kws.core.domain.service.education.EducationFacultyService;
import com.otc.kws.core.gateway.web.api.BaseKwsAPI;
import com.otc.kws.core.gateway.web.api.ResponseMessage;

import lombok.Data;

@RestController
@RequestMapping(BaseKwsAPI.PREFIX_PATH + "/core/education/faculty")
public class EducationFacultyRemoveAPI extends BaseKwsAPI
{
	@Autowired
	protected EducationFacultyService educationFacultyService;
	
	
	@PostMapping("/remove")
	public ResponseEntity remove(HttpServletRequest request, @RequestBody RequestMessage requestMessage) throws EntityNotFoundException
	{
		logger.info("### EducationFacultyRemoveAPI.remove ###");
		logger.info("requestMessage => " + requestMessage);
		
		EducationFaculty removedEducationFaculty = educationFacultyService.remove(req -> {
			setupServiceRequest(request, req);
			req.setEducationFacultyId(requestMessage.getEducationFacultyId());
		}).getRemovedEducationFaculty();
		
		BodyResponseMessage bodyResponseMessage = new BodyResponseMessage();
		bodyResponseMessage.setRemovedEducationFaculty(removedEducationFaculty);
		
		return replySuccess(request, bodyResponseMessage);
	}
	
	
	@Data
	public static class RequestMessage
	{
		protected String educationFacultyId;
	}
	
	
	@Data
	public static class BodyResponseMessage extends ResponseMessage.BaseBody
	{
		protected EducationFaculty removedEducationFaculty;
	}
}