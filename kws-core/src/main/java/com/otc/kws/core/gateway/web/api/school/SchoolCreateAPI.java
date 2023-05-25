package com.otc.kws.core.gateway.web.api.school;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.otc.kws.core.domain.entity.School;
import com.otc.kws.core.domain.exception.DuplicateEntityException;
import com.otc.kws.core.domain.service.education.SchoolService;
import com.otc.kws.core.gateway.web.api.BaseKwsAPI;
import com.otc.kws.core.gateway.web.api.ResponseMessage;

import lombok.Data;

@RestController
@RequestMapping(BaseKwsAPI.PREFIX_PATH + "/core/education/school")
public class SchoolCreateAPI extends BaseKwsAPI
{
	@Autowired
	protected SchoolService schoolService;
	
	
	@PostMapping("/create")
	public ResponseEntity<?> create(HttpServletRequest request, @RequestBody(required = false) RequestMessage requestMessage) throws DuplicateEntityException
	{
		logger.info("### SchoolCreateAPI.create ###");
		logger.info("requestMessage => " + requestMessage);
		
		School createdSchool = schoolService.create(req -> {
			setupServiceRequest(request, req);
			req.setSchool(requestMessage.getSchool());
		}).getCreatedSchool();
		
		BodyResponseMessage bodyResponseMessage = new BodyResponseMessage();
		bodyResponseMessage.setCreatedSchool(createdSchool);
		
		return replySuccess(request, bodyResponseMessage);
	}
	
	
	@Data
	public static class RequestMessage
	{
		protected School school;
	}
	
	
	@Data
	public static class BodyResponseMessage extends ResponseMessage.BaseBody
	{
		protected School createdSchool;
	}
}