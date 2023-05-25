package com.otc.kws.fishsix.lib.gateway.web.api.teacher_register;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.otc.kws.core.gateway.web.api.ResponseMessage;
import com.otc.kws.fishsix.lib.domain.entity.TeacherRegister;
import com.otc.kws.fishsix.lib.domain.model.TeacherProfile;
import com.otc.kws.fishsix.lib.domain.service.teacher_register.FishsixTeacherRegisterApproveCommand;
import com.otc.kws.fishsix.lib.domain.service.teacher_register.FishsixTeacherRegisterService;
import com.otc.kws.fishsix.lib.gateway.web.api.BaseKwsFishsixAPI;

import lombok.Data;

@RestController
@RequestMapping(BaseKwsFishsixAPI.PREFIX_PATH + "/teacher-register/approve")
public class FishsixTeacherRegisterApproveAPI extends BaseKwsFishsixAPI
{
	@Autowired
	protected FishsixTeacherRegisterService teacherRegisterService;
	
	
	@PostMapping
	public ResponseEntity<?> approve(HttpServletRequest request, @RequestBody(required = false) RequestMessage requestMessage)
	{
		logger.info("### {}.approve ###", getClass().getSimpleName());
		logger.info("requestMessage => {}", requestMessage);
		
		FishsixTeacherRegisterApproveCommand.Response approveTeacherRegisterResponse = teacherRegisterService.approve(req -> {
			setupServiceRequest(request, req);
			req.setTeacherRegister(requestMessage.getTeacherRegister());
		});
		
		BodyResponseMessage bodyResponseMessage = new BodyResponseMessage();
		bodyResponseMessage.setApprovedTeacherRegister(approveTeacherRegisterResponse.getApprovedTeacherRegister());
		bodyResponseMessage.setCreatedTeacherProfile(approveTeacherRegisterResponse.getCreatedTeacherProfile());
		
		return replySuccess(request, bodyResponseMessage);
	}
	
	
	@Data
	public static class RequestMessage 
	{
		protected TeacherRegister teacherRegister;
	}
	
	
	@Data
	public static class BodyResponseMessage extends ResponseMessage.BaseBody
	{
		protected TeacherRegister approvedTeacherRegister;
		protected TeacherProfile createdTeacherProfile;
	}
}