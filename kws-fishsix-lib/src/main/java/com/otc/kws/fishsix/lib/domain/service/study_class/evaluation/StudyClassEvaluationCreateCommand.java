package com.otc.kws.fishsix.lib.domain.service.study_class.evaluation;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.otc.kws.core.domain.exception.KwsRuntimeException;
import com.otc.kws.core.domain.service.BaseKwsCommandRequest;
import com.otc.kws.core.domain.service.BaseKwsCommandResponse;
import com.otc.kws.fishsix.lib.domain.entity.StudyClassEvaluation;
import com.otc.kws.fishsix.lib.domain.entity.StudyClassEvaluation.EvaluateStatus;
import com.otc.kws.fishsix.lib.domain.entity.StudyClassEvaluation.EvaluatorType;
import com.otc.kws.fishsix.lib.domain.entity.StudyClassEvaluationDetail;
import com.otc.kws.fishsix.lib.domain.entity.StudyClassSchedule;
import com.otc.kws.fishsix.lib.domain.model.StudyClassEvaluationProfile;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaStudyClassEvaluationDetailRepository;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaStudyClassEvaluationRepository;

import lombok.Data;

@Component
public class StudyClassEvaluationCreateCommand extends BaseStudyClassEvaluationCommand
{
	@Autowired
	protected JpaStudyClassEvaluationRepository studyClassEvaluationRepository;
	
	@Autowired
	protected JpaStudyClassEvaluationDetailRepository studyClassEvaluationDetailRepository;
	
	
	public Response createStudyClassEvaluation(Request request)
	{
		logger.info("### {}.createStudyClassEvaluation ###", getClass().getSimpleName());
		
		StudyClassEvaluationProfile evaluationProfile = new StudyClassEvaluationProfile();
		
		StudyClassEvaluation evaluation = request.getStudyClassEvaluationProfile().getStudyClassEvaluation();
		List<StudyClassEvaluationDetail> evaluationDetails = request.getStudyClassEvaluationProfile().getStudyClassEvaluationDetails();
		
		StudyClassSchedule studyClassSchedule = studyClassScheduleRepository.findById(evaluation.getClassScheduleId()).orElse(null);
		
		if(studyClassSchedule == null) {
			throw new KwsRuntimeException("StudyClassSchedule {id: "+evaluation.getClassScheduleId()+"} Not Found");
		}
		
		
		// ****************************** evaluation ****************************** //
		evaluation.setId(UUID.randomUUID().toString());
		
		if(evaluation.getEvaluateFromRefId() == null) {
			if(evaluation.getEvaluateFromType() == EvaluatorType.Teacher) {
				evaluation.setEvaluateFromRefId(studyClassSchedule.getTeacherId());
			} else if(evaluation.getEvaluateFromType() == EvaluatorType.Student) {
				evaluation.setEvaluateFromRefId(studyClassSchedule.getStudentId());
			}
		}
		
		if(evaluation.getEvaluateToRefId() == null) {
			if(evaluation.getEvaluateToType() == EvaluatorType.Teacher) {
				evaluation.setEvaluateToRefId(studyClassSchedule.getTeacherId());
			} else if(evaluation.getEvaluateToType() == EvaluatorType.Student) {
				evaluation.setEvaluateToRefId(studyClassSchedule.getStudentId());
			}
		}
		
		if(evaluation.getEvaluateStatus() == null) {
			evaluation.setEvaluateStatus(EvaluateStatus.Pending);
		}
		
		if(evaluation.getEvaluateStatus() == EvaluateStatus.Rejected) {
			evaluation.setEvaluateRejectedBy(request.getPerformedBy());
			evaluation.setEvaluateRejectedAt(request.getPerformedAt());
		}
		
		if(evaluation.getEvaluateStatus() == EvaluateStatus.Approved) {
			evaluation.setEvaluateApprovedBy(request.getPerformedBy());
			evaluation.setEvaluateApprovedAt(request.getPerformedAt());
		}
		
		evaluation.setCreatedBy(request.getPerformedBy());
		evaluation.setCreatedAt(request.getPerformedAt());
		
		evaluation = studyClassEvaluationRepository.save(evaluation);
		logger.info("Create StudyClassEvaluation => {}", evaluation);
		evaluationProfile.setStudyClassEvaluation(evaluation);
		// ****************************** evaluation ****************************** //
		
		
		// ****************************** evaluationDetails ****************************** //
		if(evaluationDetails != null && !evaluationDetails.isEmpty()) {
			for(StudyClassEvaluationDetail evaluationDetail : evaluationDetails) {
				evaluationDetail.setId(UUID.randomUUID().toString());
				evaluationDetail.setClassEvaluationId(evaluation.getId());
				evaluationDetail.setCreatedBy(request.getPerformedBy());
				evaluationDetail.setCreatedAt(request.getPerformedAt());
			}
			
			evaluationDetails = studyClassEvaluationDetailRepository.saveAll(evaluationDetails);
			logger.info("Create StudyClassEvaluationDetails => {}", evaluationDetails);
			evaluationProfile.setStudyClassEvaluationDetails(evaluationDetails);
		}
		// ****************************** evaluationDetails ****************************** //
		
		
		setTeacherHourAcquired(request, EvaluationOperation.CREATE, evaluation);
		
		
		Response response = new Response();
		response.setCreatedStudyClassEvaluationProfile(evaluationProfile);
		
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
		protected StudyClassEvaluationProfile createdStudyClassEvaluationProfile;
	}
}