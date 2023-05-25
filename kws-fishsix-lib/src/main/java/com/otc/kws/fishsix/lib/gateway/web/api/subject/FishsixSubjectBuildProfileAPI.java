package com.otc.kws.fishsix.lib.gateway.web.api.subject;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.otc.kws.core.gateway.web.api.ResponseMessage;
import com.otc.kws.fishsix.lib.domain.model.SubjectProfile;
import com.otc.kws.fishsix.lib.domain.service.subject.FishsixSubjectService;
import com.otc.kws.fishsix.lib.gateway.web.api.BaseKwsFishsixAPI;

import lombok.Data;

@RestController
@RequestMapping(BaseKwsFishsixAPI.PREFIX_PATH + "/subject/build-profile")
public class FishsixSubjectBuildProfileAPI extends BaseKwsFishsixAPI
{
	@Autowired
	protected FishsixSubjectService subjectService;
	
	
	@PostMapping
	public ResponseEntity<?> buildSubjectProfile(HttpServletRequest request, @RequestBody RequestMessage requestMessage)
	{
		logger.info("### {}.buildSubjectProfile ###", getClass().getSimpleName());
		logger.info("requestMessage => {}", requestMessage);
		
		SubjectProfile subjectProfile = subjectService.buildSubjectProfile(req -> {
			setupServiceRequest(request, req);
			req.setSubjectId(requestMessage.getSubjectId());
		}).getSubjectProfile();
		
		BodyResponseMessage bodyResponseMessage = new BodyResponseMessage();
		bodyResponseMessage.setSubjectProfile(subjectProfile);
		
		return replySuccess(request, bodyResponseMessage);
	}
	
	
	@Data
	public static class RequestMessage
	{
		protected String subjectId;
	}
	
	
	@Data
	public static class BodyResponseMessage extends ResponseMessage.BaseBody
	{
		protected SubjectProfile subjectProfile;
	}
}