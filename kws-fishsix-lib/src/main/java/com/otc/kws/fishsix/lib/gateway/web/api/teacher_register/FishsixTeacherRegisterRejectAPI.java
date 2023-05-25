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
import com.otc.kws.fishsix.lib.domain.service.teacher_register.FishsixTeacherRegisterService;
import com.otc.kws.fishsix.lib.gateway.web.api.BaseKwsFishsixAPI;

import lombok.Data;

@RestController
@RequestMapping(BaseKwsFishsixAPI.PREFIX_PATH + "/teacher-register/reject")
public class FishsixTeacherRegisterRejectAPI extends BaseKwsFishsixAPI
{
	@Autowired
	protected FishsixTeacherRegisterService teacherRegisterService;
	
	
	@PostMapping
	public ResponseEntity<?> reject(HttpServletRequest request, @RequestBody(required = false) RequestMessage requestMessage)
	{
		logger.info("### {}.reject ###", getClass().getSimpleName());
		logger.info("requestMessage => {}", requestMessage);
		
		TeacherRegister rejectedTeacherRegister = teacherRegisterService.reject(req -> {
			setupServiceRequest(request, req);
			req.setTeacherRegister(requestMessage.getTeacherRegister());
		}).getRejectedTeacherRegister();
		
		BodyResponseMessage bodyResponseMessage = new BodyResponseMessage();
		bodyResponseMessage.setRejectedTeacherRegister(rejectedTeacherRegister);
		
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
		protected TeacherRegister rejectedTeacherRegister;
	}
}