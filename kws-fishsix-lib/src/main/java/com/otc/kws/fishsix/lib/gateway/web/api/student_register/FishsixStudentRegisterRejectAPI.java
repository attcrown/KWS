package com.otc.kws.fishsix.lib.gateway.web.api.student_register;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.otc.kws.core.gateway.web.api.ResponseMessage;
import com.otc.kws.fishsix.lib.domain.entity.StudentRegister;
import com.otc.kws.fishsix.lib.domain.service.student_register.FishsixStudentRegisterService;
import com.otc.kws.fishsix.lib.gateway.web.api.BaseKwsFishsixAPI;

import lombok.Data;

@RestController
@RequestMapping(BaseKwsFishsixAPI.PREFIX_PATH + "/student-register/reject")
public class FishsixStudentRegisterRejectAPI extends BaseKwsFishsixAPI
{
	@Autowired
	protected FishsixStudentRegisterService studentRegisterService;
	
	
	@PostMapping
	public ResponseEntity<?> reject(HttpServletRequest request, @RequestBody(required = false) RequestMessage requestMessage)
	{
		logger.info("### {}.reject ###", getClass().getSimpleName());
		logger.info("requestMessage => {}", requestMessage);
		
		StudentRegister rejectedStudentRegister = studentRegisterService.reject(req -> {
			setupServiceRequest(request, req);
			req.setStudentRegister(requestMessage.getStudentRegister());
		}).getRejectedStudentRegister();
		
		BodyResponseMessage bodyResponseMessage = new BodyResponseMessage();
		bodyResponseMessage.setRejectedStudentRegister(rejectedStudentRegister);
		
		return replySuccess(request, bodyResponseMessage);
	}
	
	
	@Data
	public static class RequestMessage 
	{
		protected StudentRegister studentRegister;
	}
	
	
	@Data
	public static class BodyResponseMessage extends ResponseMessage.BaseBody
	{
		protected StudentRegister rejectedStudentRegister;
	}
}