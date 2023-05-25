package com.otc.kws.core.gateway.web.api.school;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.otc.kws.core.domain.entity.School;
import com.otc.kws.core.domain.exception.EntityNotFoundException;
import com.otc.kws.core.domain.service.education.SchoolService;
import com.otc.kws.core.gateway.web.api.BaseKwsAPI;
import com.otc.kws.core.gateway.web.api.ResponseMessage;

import lombok.Data;

@RestController
@RequestMapping(BaseKwsAPI.PREFIX_PATH + "/core/education/school")
public class SchoolRemoveAPI extends BaseKwsAPI
{
	@Autowired
	protected SchoolService schoolService;
	
	
	@PostMapping("/remove")
	public ResponseEntity<?> remove(HttpServletRequest request, @RequestBody(required = false) RequestMessage requestMessage) throws EntityNotFoundException
	{
		logger.info("### SchoolRemoveAPI.remove ###");
		logger.info("requestMessage => " + requestMessage);
		
		School removedSchool = schoolService.remove(req -> {
			setupServiceRequest(request, req);
			req.setSchoolId(requestMessage.getSchoolId());
		}).getRemovedSchool();
		
		BodyResponseMessage bodyResponseMessage = new BodyResponseMessage();
		bodyResponseMessage.setRemovedSchool(removedSchool);
		
		return replySuccess(request, bodyResponseMessage);
	}
	
	
	@Data
	public static class RequestMessage
	{
		protected String schoolId;
	}
	
	
	@Data
	public static class BodyResponseMessage extends ResponseMessage.BaseBody
	{
		protected School removedSchool;
	}
}