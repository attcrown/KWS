package com.otc.kws.firstjobber.lib.gateway.web.api.register;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.otc.kws.core.gateway.web.api.ResponseMessage;
import com.otc.kws.firstjobber.lib.domain.service.InternshipRegisterService;
import com.otc.kws.firstjobber.lib.gateway.web.api.BaseKwsFirstJobberAPI;

import lombok.Data;

@RestController
@RequestMapping(BaseKwsFirstJobberAPI.PREFIX_PATH + "/registration/internship/pre-screen/reject")
public class FjbInternshipPreviewRejectAPI extends BaseKwsFirstJobberAPI
{
	@Autowired
	protected InternshipRegisterService internshipRegisterService;
	
	
	@PostMapping
	public ResponseEntity<?> preScreenReject(HttpServletRequest request, @ModelAttribute RequestMessage requestMessage) throws Exception
	{
		logger.info("### FjbInternshipPreviewRejectAPI.preScreenReject ###");
		logger.info("requestMessage => " + requestMessage);
		
		return replySuccess(request);
	}
	
	
	@Data
	public static class RequestMessage
	{
		protected String remark;
	}
	
	
	@Data
	public static class BodyResponseMessage extends ResponseMessage.BaseBody
	{
		
	}
}