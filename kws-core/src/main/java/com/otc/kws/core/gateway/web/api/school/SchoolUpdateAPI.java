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
public class SchoolUpdateAPI extends BaseKwsAPI
{
	@Autowired
	protected SchoolService schoolService;
	
	
	@PostMapping("/update")
	public ResponseEntity<?> update(HttpServletRequest request, @RequestBody(required = false) RequestMessage requestMessage) throws EntityNotFoundException
	{
		logger.info("### SchoolUpdateAPI.update ###");
		logger.info("requestMessage => " + requestMessage);
		
		School updatedSchool = schoolService.update(req -> {
			setupServiceRequest(request, req);
			req.setSchoolId(requestMessage.getSchoolId());
			req.setSchool(requestMessage.getSchool());
		}).getUpdatedSchool();
		
		BodyResponseMessage bodyResponseMessage = new BodyResponseMessage();
		bodyResponseMessage.setUpdatedSchool(updatedSchool);
		
		return replySuccess(request, bodyResponseMessage);
	}
	
	
	@Data
	public static class RequestMessage
	{
		protected String schoolId;
		protected School school;
	}
	
	
	@Data
	public static class BodyResponseMessage extends ResponseMessage.BaseBody
	{
		protected School updatedSchool;
	}
}