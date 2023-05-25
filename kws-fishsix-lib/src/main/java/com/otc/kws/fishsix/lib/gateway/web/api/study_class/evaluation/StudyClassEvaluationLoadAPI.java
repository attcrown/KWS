package com.otc.kws.fishsix.lib.gateway.web.api.study_class.evaluation;

import java.util.List;

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
import com.otc.kws.fishsix.lib.domain.service.study_class.evaluation.StudyClassEvaluationLoadCommand;
import com.otc.kws.fishsix.lib.gateway.web.api.BaseKwsFishsixAPI;

import lombok.Data;

@RestController
@RequestMapping(BaseKwsFishsixAPI.PREFIX_PATH + "/study-class/evaluation/load")
public class StudyClassEvaluationLoadAPI extends BaseKwsFishsixAPI
{
	@Autowired
	protected StudyClassService studyClassService;
	
	
	@PostMapping
	public ResponseEntity<?> loadStudyClassEvaluation(HttpServletRequest request, @RequestBody RequestMessage requestMessage)
	{
		logger.info("### {}.loadStudyClassEvaluation ###", getClass().getSimpleName());
		
		StudyClassEvaluationLoadCommand.Response loadEvaluationResponse = studyClassService.loadStudyClassEvaluation(req -> {
			setupServiceRequest(request, req);
			req.setClassScheduleIds(requestMessage.getClassScheduleIds());
		});
		
		BodyResponseMessage bodyResponseMessage = new BodyResponseMessage();
		bodyResponseMessage.setStudyClassEvaluationProfiles(loadEvaluationResponse.getStudyClassEvaluationProfiles());
		
		return replySuccess(request, bodyResponseMessage);
	}
	
	
	@Data
	public static class RequestMessage
	{
		protected List<String> classScheduleIds;
	}
	
	
	@Data
	public static class BodyResponseMessage extends ResponseMessage.BaseBody
	{
		protected List<StudyClassEvaluationProfile> studyClassEvaluationProfiles;
	}
}