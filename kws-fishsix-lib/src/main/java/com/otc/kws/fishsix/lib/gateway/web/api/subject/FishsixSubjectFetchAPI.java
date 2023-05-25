package com.otc.kws.fishsix.lib.gateway.web.api.subject;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.otc.kws.core.gateway.web.api.ResponseMessage;
import com.otc.kws.fishsix.lib.domain.model.SubjectProfile;
import com.otc.kws.fishsix.lib.domain.service.subject.FishsixSubjectFetchCommand;
import com.otc.kws.fishsix.lib.domain.service.subject.FishsixSubjectService;
import com.otc.kws.fishsix.lib.gateway.web.api.BaseKwsFishsixAPI;

import lombok.Data;

@RestController
@RequestMapping(BaseKwsFishsixAPI.PREFIX_PATH + "/subject/fetch")
public class FishsixSubjectFetchAPI extends BaseKwsFishsixAPI
{
	@Autowired
	protected FishsixSubjectService subjectService;
	
	
	@PostMapping
	public ResponseEntity<?> fetchSubject(HttpServletRequest request, @RequestBody(required = false) RequestMessage requestMessage)
	{
		logger.info("### {}.fetchSubject ###", getClass().getSimpleName());
		logger.info("requestMessage => {}", requestMessage);
		
		FishsixSubjectFetchCommand.Response fetchSubjectResponse = subjectService.fetchSubject(req -> {
			setupServiceRequest(request, req);
			
			if(requestMessage != null) {
				//req.setCriteria(requestMessage.getCriteria());
			}
		});
		
		BodyResponseMessage bodyResponseMessage = new BodyResponseMessage();
		bodyResponseMessage.setSubjectProfiles(fetchSubjectResponse.getSubjectProfiles());
		
		return replySuccess(request, bodyResponseMessage);
	}
	
	
	@Data
	public static class RequestMessage
	{
		
	}
	
	
	@Data
	public static class BodyResponseMessage extends ResponseMessage.BaseBody
	{
		protected List<SubjectProfile> subjectProfiles;
	}
}