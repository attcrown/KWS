package com.otc.kws.fishsix.lib.domain.model;

import java.util.List;

import com.otc.kws.fishsix.lib.domain.entity.StudyClass;
import com.otc.kws.fishsix.lib.domain.entity.StudyClassSchedule;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudyClassScheduleProfile 
{
	protected String date;
	protected List<Period> periods;
	
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class Period
	{
		protected String startTime;
		protected String endTime;
		protected List<Class> classes;
	}
	
	
	@Data
	public static class Class extends StudyClass
	{
		protected List<StudyClassSchedule> schedules;
	}
}