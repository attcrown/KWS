package com.otc.kws.fishsix.lib.gateway.web.api.student;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.otc.kws.core.gateway.web.api.ResponseMessage;
import com.otc.kws.fishsix.lib.domain.model.StudentProfile;
import com.otc.kws.fishsix.lib.domain.service.student.FishsixStudentFetchCommand;
import com.otc.kws.fishsix.lib.domain.service.student.FishsixStudentService;
import com.otc.kws.fishsix.lib.gateway.web.api.BaseKwsFishsixAPI;

import lombok.Data;

@RestController
@RequestMapping(BaseKwsFishsixAPI.PREFIX_PATH + "/student/fetch")
public class FishsixStudentFetchAPI extends BaseKwsFishsixAPI
{
	@Autowired
	protected FishsixStudentService studentService;
	
	
	@PostMapping
	public ResponseEntity<?> fetchStudent(HttpServletRequest request, @RequestBody(required = false) RequestMessage requestMessage)
	{
		logger.info("### {}.fetchStudent ###", getClass().getSimpleName());
		logger.info("requestMessage => {}", requestMessage);
		
		FishsixStudentFetchCommand.Response fetchStudentResponse = studentService.fetchStudent(req -> {
			setupServiceRequest(request, req);
			
			if(requestMessage != null) {
				req.setCriteria(requestMessage.getCriteria());
			}
		});
		
		BodyResponseMessage bodyResponseMessage = new BodyResponseMessage();
		bodyResponseMessage.setStudentProfiles(fetchStudentResponse.getStudentProfiles());
		
		return replySuccess(request, bodyResponseMessage);
	}
	
	
	@Data
	public static class RequestMessage 
	{
		protected FishsixStudentFetchCommand.Request.Criteria criteria;
	}
	
	
	@Data
	public static class BodyResponseMessage extends ResponseMessage.BaseBody
	{
		protected List<StudentProfile> studentProfiles;
	}
}