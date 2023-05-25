package com.otc.kws.fishsix.lib.domain.service.study_class.evaluation;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.otc.kws.core.domain.exception.KwsRuntimeException;
import com.otc.kws.core.domain.service.BaseKwsCommandRequest;
import com.otc.kws.core.domain.service.BaseKwsCommandResponse;
import com.otc.kws.fishsix.lib.domain.entity.StudyClassEvaluation;
import com.otc.kws.fishsix.lib.domain.entity.StudyClassEvaluationDetail;
import com.otc.kws.fishsix.lib.domain.entity.StudyClassEvaluation.EvaluateStatus;
import com.otc.kws.fishsix.lib.domain.model.StudyClassEvaluationProfile;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaStudyClassEvaluationDetailRepository;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaStudyClassEvaluationRepository;

import lombok.Data;

@Component
public class StudyClassEvaluationUpdateCommand extends BaseStudyClassEvaluationCommand
{
	@Autowired
	protected JpaStudyClassEvaluationRepository studyClassEvaluationRepository;
	
	@Autowired
	protected JpaStudyClassEvaluationDetailRepository studyClassEvaluationDetailRepository;
	
	
	public Response updateStudyClassEvaluation(Request request)
	{
		logger.info("### {}.updateStudyClassEvaluation ###", getClass().getSimpleName());
		
		StudyClassEvaluationProfile evaluationProfile = new StudyClassEvaluationProfile();
		
		StudyClassEvaluation evaluation = request.getStudyClassEvaluationProfile().getStudyClassEvaluation();
		List<StudyClassEvaluationDetail> evaluationDetails = request.getStudyClassEvaluationProfile().getStudyClassEvaluationDetails();
		
		if(studyClassEvaluationRepository.findById(evaluation.getId()).isEmpty()) {
			throw new KwsRuntimeException("StudyClassEvaluation {id: "+evaluation.getId()+"} Not Found");
		}
		
		
		// ****************************** evaluation ****************************** //
		if(evaluation.getEvaluateStatus() == EvaluateStatus.Rejected) {
			evaluation.setEvaluateRejectedBy(request.getPerformedBy());
			evaluation.setEvaluateRejectedAt(request.getPerformedAt());
		}
		
		if(evaluation.getEvaluateStatus() == EvaluateStatus.Approved) {
			evaluation.setEvaluateApprovedBy(request.getPerformedBy());
			evaluation.setEvaluateApprovedAt(request.getPerformedAt());
		}
		
		evaluation.setLastModifiedBy(request.getPerformedBy());
		evaluation.setLastModifiedAt(request.getPerformedAt());
		
		evaluation = studyClassEvaluationRepository.save(evaluation);
		logger.info("Update StudyClassEvaluation => {}", evaluation);
		evaluationProfile.setStudyClassEvaluation(evaluation);
		// ****************************** evaluation ****************************** //
		
		
		// ****************************** evaluationDetails ****************************** //
		List<StudyClassEvaluationDetail> removedEvaluationDetails = studyClassEvaluationDetailRepository.removeByClassEvaluationIds(Arrays.asList(evaluation.getId()));
		logger.info("Delete StudyClassEvaluationDetails => {}", removedEvaluationDetails);
		studyClassEvaluationDetailRepository.flush();
		
		if(evaluationDetails != null && !evaluationDetails.isEmpty()) {
			for(StudyClassEvaluationDetail evaluationDetail : evaluationDetails) {
				evaluationDetail.setId(UUID.randomUUID().toString());
				evaluationDetail.setClassEvaluationId(evaluation.getId());
				evaluationDetail.setCreatedBy(evaluation.getCreatedBy());
				evaluationDetail.setCreatedAt(evaluation.getCreatedAt());
				evaluationDetail.setLastModifiedBy(request.getPerformedBy());
				evaluationDetail.setLastModifiedAt(request.getPerformedAt());
			}
			
			evaluationDetails = studyClassEvaluationDetailRepository.saveAll(evaluationDetails);
			logger.info("Create StudyClassEvaluationDetails => {}", evaluationDetails);
			evaluationProfile.setStudyClassEvaluationDetails(evaluationDetails);
		}
		// ****************************** evaluationDetails ****************************** //
		
		
		setTeacherHourAcquired(request, EvaluationOperation.UPDATE, evaluation);
		
		
		Response response = new Response();
		response.setUpdatedStudyClassEvaluationProfile(evaluationProfile);
		
		return response;
	}
	
	
	@Data
	public static class Request extends BaseKwsCommandRequest
	{
		protected StudyClassEvaluationProfile studyClassEvaluationProfile;
	}
	
	
	@Data
	public static class Response extends BaseKwsCommandResponse
	{
		protected StudyClassEvaluationProfile updatedStudyClassEvaluationProfile;
	}
}