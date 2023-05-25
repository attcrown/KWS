package com.otc.kws.fishsix.lib.domain.model;

import java.util.List;

import com.otc.kws.fishsix.lib.domain.entity.StudyClassEvaluation;
import com.otc.kws.fishsix.lib.domain.entity.StudyClassEvaluationDetail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudyClassEvaluationProfile 
{
	protected StudyClassEvaluation studyClassEvaluation;
	protected List<StudyClassEvaluationDetail> studyClassEvaluationDetails;
}