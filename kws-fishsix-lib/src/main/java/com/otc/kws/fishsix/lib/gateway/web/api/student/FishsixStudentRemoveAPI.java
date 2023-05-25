package com.otc.kws.fishsix.lib.gateway.web.api.student;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.otc.kws.core.gateway.web.api.ResponseMessage;
import com.otc.kws.fishsix.lib.domain.model.StudentProfile;
import com.otc.kws.fishsix.lib.domain.service.student.FishsixStudentService;
import com.otc.kws.fishsix.lib.domain.service.student.FishsixStudentRemoveCommand;
import com.otc.kws.fishsix.lib.gateway.web.api.BaseKwsFishsixAPI;

import lombok.Data;

@RestController
@RequestMapping(BaseKwsFishsixAPI.PREFIX_PATH + "/student/remove")
public class FishsixStudentRemoveAPI extends BaseKwsFishsixAPI
{
	@Autowired
	protected FishsixStudentService studentService;
	
	
	@PostMapping
	public ResponseEntity<?> removeStudent(HttpServletRequest request, @RequestBody(required = false) RequestMessage requestMessage)
	{
		logger.info("### {}.removeStudent ###", getClass().getSimpleName());
		logger.info("requestMessage => {}", requestMessage);
		
		FishsixStudentRemoveCommand.Response removeStudentResponse = studentService.removeStudent(req -> {
			setupServiceRequest(request, req);
			req.setStudentId(requestMessage.getStudentId());
		});
		
		BodyResponseMessage bodyResponseMessage = new BodyResponseMessage();
		bodyResponseMessage.setRemovedStudentProfile(removeStudentResponse.getRemovedStudentProfile());
		
		return replySuccess(request, bodyResponseMessage);
	}
	
	
	@Data
	public static class RequestMessage 
	{
		protected String studentId;
	}
	
	
	@Data
	public static class BodyResponseMessage extends ResponseMessage.BaseBody
	{
		protected StudentProfile removedStudentProfile;
	}
}