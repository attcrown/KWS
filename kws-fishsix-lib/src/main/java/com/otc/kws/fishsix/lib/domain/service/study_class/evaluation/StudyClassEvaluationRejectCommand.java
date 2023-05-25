package com.otc.kws.fishsix.lib.domain.service.study_class.evaluation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.otc.kws.core.domain.exception.KwsRuntimeException;
import com.otc.kws.core.domain.service.BaseKwsCommandRequest;
import com.otc.kws.core.domain.service.BaseKwsCommandResponse;
import com.otc.kws.fishsix.lib.domain.entity.StudyClassEvaluation;
import com.otc.kws.fishsix.lib.domain.entity.StudyClassEvaluation.EvaluateStatus;
import com.otc.kws.fishsix.lib.domain.model.StudyClassEvaluationProfile;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaStudyClassEvaluationDetailRepository;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaStudyClassEvaluationRepository;

import lombok.Data;

@Component
public class StudyClassEvaluationRejectCommand extends BaseStudyClassEvaluationCommand
{
	@Autowired
	protected JpaStudyClassEvaluationRepository studyClassEvaluationRepository;
	
	@Autowired
	protected JpaStudyClassEvaluationDetailRepository studyClassEvaluationDetailRepository;
	
	
	public Response rejectStudyClassEvaluation(Request request)
	{
		logger.info("### {}.rejectStudyClassEvaluation ###", getClass().getSimpleName());
		
		StudyClassEvaluationProfile evaluationProfile = new StudyClassEvaluationProfile();
		
		StudyClassEvaluation evaluation = studyClassEvaluationRepository.findById(request.getEvaluationId()).orElse(null);
		
		if(evaluation == null) {
			throw new KwsRuntimeException("StudyClassEvaluation {id: "+request.getEvaluationId()+"} Not Found");
		}
		
		evaluation.setEvaluateStatus(EvaluateStatus.Rejected);
		evaluation.setEvaluateRejectedBy(request.getPerformedBy());
		evaluation.setEvaluateRejectedAt(request.getPerformedAt());
		evaluation.setEvaluateRejectedRemark(request.getRemark());
		evaluation.setLastModifiedBy(request.getPerformedBy());
		evaluation.setLastModifiedAt(request.getPerformedAt());
		
		evaluation = studyClassEvaluationRepository.save(evaluation);
		logger.info("Create StudyClassEvaluation => {}", evaluation);
		
		evaluationProfile.setStudyClassEvaluation(evaluation);
		evaluationProfile.setStudyClassEvaluationDetails(studyClassEvaluationDetailRepository.findByClassEvaluationId(evaluation.getId()));
		
		setTeacherHourAcquired(request, EvaluationOperation.REJECT, evaluation);
		
		Response response = new Response();
		response.setStudyClassEvaluationProfile(evaluationProfile);
		
		return response;
	}
	
	
	@Data
	public static class Request extends BaseKwsCommandRequest
	{
		protected String evaluationId;
		protected String remark;
	}
	
	
	@Data
	public static class Response extends BaseKwsCommandResponse
	{
		protected StudyClassEvaluationProfile studyClassEvaluationProfile;
	}
}