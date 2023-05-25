package com.otc.kws.fishsix.lib.gateway.web.api.teacher;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.otc.kws.core.gateway.web.api.ResponseMessage;
import com.otc.kws.fishsix.lib.domain.model.TeacherProfile;
import com.otc.kws.fishsix.lib.domain.service.teacher.FishsixTeacherService;
import com.otc.kws.fishsix.lib.gateway.web.api.BaseKwsFishsixAPI;

import lombok.Data;

@RestController
@RequestMapping(BaseKwsFishsixAPI.PREFIX_PATH + "/teacher/build-profile")
public class FishsixTeacherBuildProfileAPI extends BaseKwsFishsixAPI
{
	@Autowired
	protected FishsixTeacherService teacherService;
	
	
	@PostMapping
	public ResponseEntity<?> buildTeacherProfile(HttpServletRequest request, @RequestBody RequestMessage requestMessage) throws Exception
	{
		logger.info("### {}.buildTeacherProfile ###", getClass().getSimpleName());
		logger.info("requestMessage => " + requestMessage);
		
		TeacherProfile teacherProfile = teacherService.buildTeacherProfile(req -> {
			setupServiceRequest(request, req);
			req.setTeacherId(requestMessage.getTeacherId());
		}).getTeacherProfile();
		
		BodyResponseMessage bodyResponseMessage = new BodyResponseMessage();
		bodyResponseMessage.setTeacherProfile(teacherProfile);
		
		return replySuccess(request, bodyResponseMessage);
	}
	
	
	@Data
	public static class RequestMessage
	{
		protected String teacherId;
	}
	
	
	@Data
	public static class BodyResponseMessage extends ResponseMessage.BaseBody
	{
		protected TeacherProfile teacherProfile;
	}
}