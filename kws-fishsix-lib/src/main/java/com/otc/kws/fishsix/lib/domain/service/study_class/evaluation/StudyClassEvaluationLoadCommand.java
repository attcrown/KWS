package com.otc.kws.fishsix.lib.domain.service.study_class.evaluation;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.otc.kws.core.domain.service.BaseKwsCommand;
import com.otc.kws.core.domain.service.BaseKwsCommandRequest;
import com.otc.kws.core.domain.service.BaseKwsCommandResponse;
import com.otc.kws.fishsix.lib.domain.entity.StudyClassEvaluation;
import com.otc.kws.fishsix.lib.domain.entity.StudyClassEvaluationDetail;
import com.otc.kws.fishsix.lib.domain.model.StudyClassEvaluationProfile;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaStudyClassEvaluationDetailRepository;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaStudyClassEvaluationRepository;

import lombok.Data;

@Component
public class StudyClassEvaluationLoadCommand extends BaseKwsCommand
{
	@Autowired
	protected JpaStudyClassEvaluationRepository studyClassEvaluationRepository;
	
	@Autowired
	protected JpaStudyClassEvaluationDetailRepository studyClassEvaluationDetailRepository;
	
	
	public Response loadStudyClassEvaluation(Request request)
	{
		logger.info("### {}.loadStudyClassEvaluation ###", getClass().getSimpleName());
		
		Response response = new Response();
		
		List<StudyClassEvaluation> evaluations = studyClassEvaluationRepository.findByClassScheduleIds(request.getClassScheduleIds());
		List<StudyClassEvaluationDetail> evaluationDetails = null;
		
		if(evaluations != null && !evaluations.isEmpty()) {
			List<String> evaluationIds = evaluations.stream().map(StudyClassEvaluation::getId).distinct().collect(Collectors.toList());
			evaluationDetails = studyClassEvaluationDetailRepository.findByClassEvaluationIds(evaluationIds);
		}
		
		if(evaluations != null && !evaluations.isEmpty()) {
			List<StudyClassEvaluationProfile> studyClassEvaluationProfiles = new ArrayList<>();
			
			for(StudyClassEvaluation evaluation : evaluations) {
				StudyClassEvaluationProfile evaluationProfile = new StudyClassEvaluationProfile();
				evaluationProfile.setStudyClassEvaluation(evaluation);
				
				if(evaluationDetails != null && !evaluationDetails.isEmpty()) {
					evaluationProfile.setStudyClassEvaluationDetails(evaluationDetails.
							stream().
							filter(e -> e.getClassEvaluationId().equals(evaluation.getId())).
							collect(Collectors.toList())
						);
				}
				
				studyClassEvaluationProfiles.add(evaluationProfile);
			}
			
			response.setStudyClassEvaluationProfiles(studyClassEvaluationProfiles);
		}
		
		return response;
	}
	
	
	@Data
	public static class Request extends BaseKwsCommandRequest
	{
		protected List<String> classScheduleIds;
	}
	
	
	@Data
	public static class Response extends BaseKwsCommandResponse
	{
		protected List<StudyClassEvaluationProfile> studyClassEvaluationProfiles;
	}
}