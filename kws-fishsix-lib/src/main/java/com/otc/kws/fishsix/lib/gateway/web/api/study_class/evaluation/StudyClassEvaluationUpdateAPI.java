package com.otc.kws.fishsix.lib.gateway.web.api.study_class.evaluation;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.otc.kws.core.gateway.web.api.ResponseMessage;
import com.otc.kws.fishsix.lib.domain.model.StudyClassEvaluationProfile;
import com.otc.kws.fishsix.lib.domain.service.study_class.StudyClassService;
import com.otc.kws.fishsix.lib.domain.service.study_class.evaluation.StudyClassEvaluationUpdateCommand;
import com.otc.kws.fishsix.lib.gateway.web.api.BaseKwsFishsixAPI;

import lombok.Data;

@RestController
@RequestMapping(BaseKwsFishsixAPI.PREFIX_PATH + "/study-class/evaluation/update")
public class StudyClassEvaluationUpdateAPI extends BaseKwsFishsixAPI
{
	@Autowired
	protected StudyClassService studyClassService;
	
	
	@PostMapping
	public ResponseEntity<?> updateStudyClassEvaluation(HttpServletRequest request, @RequestBody RequestMessage requestMessage)
	{
		logger.info("### {}.updateStudyClassEvaluation ###", getClass().getSimpleName());
		
		StudyClassEvaluationUpdateCommand.Response updateEvaluationResponse = studyClassService.updateStudyClassEvaluation(req -> {
			setupServiceRequest(request, req);
			req.setStudyClassEvaluationProfile(requestMessage.getStudyClassEvaluationProfile());
		});
		
		BodyResponseMessage bodyResponseMessage = new BodyResponseMessage();
		bodyResponseMessage.setUpdatedStudyClassEvaluationProfile(updateEvaluationResponse.getUpdatedStudyClassEvaluationProfile());
		
		return replySuccess(request, bodyResponseMessage);
	}
	
	
	@Data
	public static class RequestMessage
	{
		protected StudyClassEvaluationProfile studyClassEvaluationProfile;
	}
	
	
	@Data
	public static class BodyResponseMessage extends ResponseMessage.BaseBody
	{
		protected StudyClassEvaluationProfile updatedStudyClassEvaluationProfile;
	}
}