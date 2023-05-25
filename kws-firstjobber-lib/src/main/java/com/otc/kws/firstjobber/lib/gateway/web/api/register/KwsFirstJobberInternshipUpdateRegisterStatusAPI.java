package com.otc.kws.firstjobber.lib.gateway.web.api.register;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.otc.kws.core.domain.exception.EntityNotFoundException;
import com.otc.kws.core.gateway.web.api.ResponseMessage;
import com.otc.kws.firstjobber.lib.domain.constant.value.InternshipRegisterStatusValue;
import com.otc.kws.firstjobber.lib.domain.entity.InternshipRegister;
import com.otc.kws.firstjobber.lib.domain.service.InternshipRegisterService;
import com.otc.kws.firstjobber.lib.gateway.web.api.BaseKwsFirstJobberAPI;

import lombok.Data;

@RestController
@RequestMapping(BaseKwsFirstJobberAPI.PREFIX_PATH + "/registration/internship")
public class KwsFirstJobberInternshipUpdateRegisterStatusAPI extends BaseKwsFirstJobberAPI
{
	@Autowired
	protected InternshipRegisterService internshipRegisterService;
	
	
	@PostMapping("/update-register-status")
	public ResponseEntity<?> updateRegisterStatus(HttpServletRequest request, @RequestBody RequestMessage requestMessage) throws EntityNotFoundException
	{
		logger.info("### KwsFirstJobberInternshipUpdateRegisterStatusAPI.updateRegisterStatus ###");
		logger.info("requestMessage => " + requestMessage);
		
		InternshipRegister updatedInternshipRegister = internshipRegisterService.updateRegisterStatus(req -> {
			setupServiceRequest(request, req);
			req.setInternshipRegisterId(requestMessage.getInternshipRegisterId());
			req.setInternshipRegisterStatusId(requestMessage.getInternshipRegisterStatusId());
			req.setRemark(requestMessage.getRemark());
		}).getUpdatedInternshipRegister();
		
		BodyResponseMessage bodyResponseMessage = new BodyResponseMessage();
		bodyResponseMessage.setUpdatedInternshipRegister(updatedInternshipRegister);
		
		return replySuccess(request, bodyResponseMessage);
	}
	
	
	@Data
	public static class RequestMessage
	{
		protected String internshipRegisterId;
		protected InternshipRegisterStatusValue internshipRegisterStatusId;
		protected String remark;
	}
	
	
	@Data
	public static class BodyResponseMessage extends ResponseMessage.BaseBody
	{
		protected InternshipRegister updatedInternshipRegister;
	}
}