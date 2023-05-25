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
import com.otc.kws.fishsix.lib.domain.service.student.FishsixStudentCreateCommand;
import com.otc.kws.fishsix.lib.domain.service.student.FishsixStudentService;
import com.otc.kws.fishsix.lib.gateway.web.api.BaseKwsFishsixAPI;

import lombok.Data;

@RestController
@RequestMapping(BaseKwsFishsixAPI.PREFIX_PATH + "/student/register")
public class FishsixStudentRegisterAPI extends BaseKwsFishsixAPI
{
	@Autowired
	protected FishsixStudentService studentService;
	
	
	@PostMapping
	public ResponseEntity<?> registerStudent(HttpServletRequest request, @RequestBody(required = false) RequestMessage requestMessage)
	{
		logger.info("### {}.registerStudent ###", getClass().getSimpleName());
		logger.info("requestMessage => {}", requestMessage);
		
		FishsixStudentCreateCommand.Response createStudentResponse = studentService.createStudent(req -> {
			setupServiceRequest(request, req);
			req.setStudentProfile(requestMessage.getStudentProfile());
		});
		
		BodyResponseMessage bodyResponseMessage = new BodyResponseMessage();
		bodyResponseMessage.setCreatedStudentProfile(createStudentResponse.getCreatedStudentProfile());
		
		return replySuccess(request, bodyResponseMessage);
	}
	
	
	@Data
	public static class RequestMessage 
	{
		protected StudentProfile studentProfile;
	}
	
	
	@Data
	public static class BodyResponseMessage extends ResponseMessage.BaseBody
	{
		protected StudentProfile createdStudentProfile;
	}
}