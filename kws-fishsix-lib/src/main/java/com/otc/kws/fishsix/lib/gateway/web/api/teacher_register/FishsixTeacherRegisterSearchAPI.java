package com.otc.kws.fishsix.lib.gateway.web.api.teacher_register;

import java.util.List;

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
@RequestMapping(BaseKwsFishsixAPI.PREFIX_PATH + "/teacher-register/search")
public class FishsixTeacherRegisterSearchAPI extends BaseKwsFishsixAPI
{
	@Autowired
	protected FishsixTeacherRegisterService teacherRegisterService;
	
	
	@PostMapping
	public ResponseEntity<?> search(HttpServletRequest request, @RequestBody(required = false) RequestMessage requestMessage)
	{
		logger.info("### {}.search ###", getClass().getSimpleName());
		logger.info("requestMessage => {}", requestMessage);
		
		List<TeacherRegister> teacherRegisters = teacherRegisterService.search(req -> {
			setupServiceRequest(request, req);
		}).getTeacherRegisters();
		
		BodyResponseMessage bodyResponseMessage = new BodyResponseMessage();
		bodyResponseMessage.setTeacherRegisters(teacherRegisters);
		
		return replySuccess(request, bodyResponseMessage);
	}
	
	
	@Data
	public static class RequestMessage 
	{
		
	}
	
	
	@Data
	public static class BodyResponseMessage extends ResponseMessage.BaseBody
	{
		protected List<TeacherRegister> teacherRegisters;
	}
}