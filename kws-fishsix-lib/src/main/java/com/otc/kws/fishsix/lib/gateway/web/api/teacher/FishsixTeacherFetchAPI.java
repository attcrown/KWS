package com.otc.kws.fishsix.lib.gateway.web.api.teacher;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.otc.kws.core.gateway.web.api.ResponseMessage;
import com.otc.kws.fishsix.lib.domain.model.TeacherProfile;
import com.otc.kws.fishsix.lib.domain.service.teacher.FishsixTeacherFetchCommand;
import com.otc.kws.fishsix.lib.domain.service.teacher.FishsixTeacherService;
import com.otc.kws.fishsix.lib.gateway.web.api.BaseKwsFishsixAPI;

import lombok.Data;

@RestController
@RequestMapping(BaseKwsFishsixAPI.PREFIX_PATH + "/teacher/fetch")
public class FishsixTeacherFetchAPI extends BaseKwsFishsixAPI
{
	@Autowired
	protected FishsixTeacherService teacherService;
	
	
	@PostMapping
	public ResponseEntity<?> fetchTeacher(HttpServletRequest request, @RequestBody(required = false) RequestMessage requestMessage)
	{
		logger.info("### {}.fetchTeacher ###", getClass().getSimpleName());
		logger.info("requestMessage => {}", requestMessage);
		
		FishsixTeacherFetchCommand.Response fetchTeacherResponse = teacherService.fetchTeacher(req -> {
			setupServiceRequest(request, req);
			
			if(requestMessage != null) {
				req.setCriteria(requestMessage.getCriteria());
			}
		});
		
		BodyResponseMessage bodyResponseMessage = new BodyResponseMessage();
		bodyResponseMessage.setTeacherProfiles(fetchTeacherResponse.getTeacherProfiles());
		
		return replySuccess(request, bodyResponseMessage);
	}
	
	
	@Data
	public static class RequestMessage 
	{
		protected FishsixTeacherFetchCommand.Request.Criteria criteria;
	}
	
	
	@Data
	public static class BodyResponseMessage extends ResponseMessage.BaseBody
	{
		protected List<TeacherProfile> teacherProfiles;
	}
}