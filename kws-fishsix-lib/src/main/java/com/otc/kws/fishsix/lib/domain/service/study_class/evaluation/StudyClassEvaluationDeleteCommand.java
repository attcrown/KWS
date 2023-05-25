package com.otc.kws.fishsix.lib.domain.service.study_class.evaluation;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.otc.kws.core.domain.exception.KwsRuntimeException;
import com.otc.kws.core.domain.service.BaseKwsCommandRequest;
import com.otc.kws.core.domain.service.BaseKwsCommandResponse;
import com.otc.kws.fishsix.lib.domain.entity.StudyClassEvaluation;
import com.otc.kws.fishsix.lib.domain.entity.StudyClassEvaluationDetail;
import com.otc.kws.fishsix.lib.domain.model.StudyClassEvaluationProfile;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaStudyClassEvaluationDetailRepository;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaStudyClassEvaluationRepository;

import lombok.Data;

@Component
public class StudyClassEvaluationDeleteCommand extends BaseStudyClassEvaluationCommand
{
	@Autowired
	protected JpaStudyClassEvaluationRepository studyClassEvaluationRepository;
	
	@Autowired
	protected JpaStudyClassEvaluationDetailRepository studyClassEvaluationDetailRepository;
	
	
	public Response deleteStudyClassEvaluation(Request request)
	{
		logger.info("### {}.deleteStudyClassEvaluation ###", getClass().getSimpleName());
		
		StudyClassEvaluation evaluation = studyClassEvaluationRepository.findById(request.getEvaluationId()).orElse(null);
		
		if(evaluation == null) {
			throw new KwsRuntimeException("StudyClassEvaluation {id: "+request.getEvaluationId()+"} Not Found");
		}
		
		studyClassEvaluationRepository.delete(evaluation);
		logger.info("Delete StudyClassEvaluation => {}", evaluation);
		
		List<StudyClassEvaluationDetail> evaluationDetails = studyClassEvaluationDetailRepository.removeByClassEvaluationIds(Arrays.asList(evaluation.getId()));
		logger.info("Delete StudyClassEvaluationDetails => {}", evaluationDetails);
		
		StudyClassEvaluationProfile evaluationProfile = new StudyClassEvaluationProfile();
		evaluationProfile.setStudyClassEvaluation(evaluation);
		evaluationProfile.setStudyClassEvaluationDetails(evaluationDetails);
		
		setTeacherHourAcquired(request, EvaluationOperation.DELETE, evaluation);
		
		Response response = new Response();
		response.setDeletedStudyClassEvaluationProfile(evaluationProfile);
		
		return response;
	}
	
	
	@Data
	public static class Request extends BaseKwsCommandRequest
	{
		protected String evaluationId;
	}
	
	
	@Data
	public static class Response extends BaseKwsCommandResponse
	{
		protected StudyClassEvaluationProfile deletedStudyClassEvaluationProfile;
	}
}