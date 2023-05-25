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
import com.otc.kws.fishsix.lib.domain.service.study_class.evaluation.StudyClassEvaluationDeleteCommand;
import com.otc.kws.fishsix.lib.gateway.web.api.BaseKwsFishsixAPI;

import lombok.Data;

@RestController
@RequestMapping(BaseKwsFishsixAPI.PREFIX_PATH + "/study-class/evaluation/delete")
public class StudyClassEvaluationDeleteAPI extends BaseKwsFishsixAPI
{
	@Autowired
	protected StudyClassService studyClassService;
	
	
	@PostMapping
	public ResponseEntity<?> deleteStudyClassEvaluation(HttpServletRequest request, @RequestBody RequestMessage requestMessage)
	{
		logger.info("### {}.deleteStudyClassEvaluation ###", getClass().getSimpleName());
		
		StudyClassEvaluationDeleteCommand.Response deleteEvaluationResponse = studyClassService.deleteStudyClassEvaluation(req -> {
			setupServiceRequest(request, req);
			req.setEvaluationId(requestMessage.getEvaluationId());
		});
		
		BodyResponseMessage bodyResponseMessage = new BodyResponseMessage();
		bodyResponseMessage.setDeletedStudyClassEvaluationProfile(deleteEvaluationResponse.getDeletedStudyClassEvaluationProfile());
		
		return replySuccess(request, bodyResponseMessage);
	}
	
	
	@Data
	public static class RequestMessage
	{
		protected String evaluationId;
	}
	
	
	@Data
	public static class BodyResponseMessage extends ResponseMessage.BaseBody
	{
		protected StudyClassEvaluationProfile deletedStudyClassEvaluationProfile;
	}
}