package com.otc.kws.fishsix.lib.domain.service.class_schedule;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.otc.kws.core.domain.service.BaseKwsCommand;
import com.otc.kws.core.domain.service.BaseKwsCommandRequest;
import com.otc.kws.core.domain.service.BaseKwsCommandResponse;
import com.otc.kws.core.domain.util.DayOfWeekUtil;
import com.otc.kws.fishsix.lib.domain.entity.StudyClassScheduleTemplate;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaStudyClassScheduleTemplateRepository;

import lombok.Data;

@Component
public class StudyClassScheduleTemplateLoadCommand extends BaseKwsCommand
{
	@Autowired
	protected JpaStudyClassScheduleTemplateRepository studyClassScheduleTemplateRepository;
	
	
	public Response loadStudyClassScheduleTemplate(Request request)
	{
		logger.info("### {}.loadStudyClassScheduleTemplate ###", getClass().getSimpleName());
		
		List<StudyClassScheduleTemplate> studyClassScheduleTemplates = studyClassScheduleTemplateRepository.findAll();
		
		if(request.getDate() != null && studyClassScheduleTemplates != null && !studyClassScheduleTemplates.isEmpty()) {
			DayOfWeek dayOfWeek = DayOfWeekUtil.getDayOfWeek(request.getDate());
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
			
			String date = sdf.format(request.getDate());
			
			studyClassScheduleTemplates = studyClassScheduleTemplates.
					stream().
					filter(e -> {
						String startDate = sdf.format(e.getStartDate());
						
						if(e.isRepeatable()) {
							if(date.compareTo(startDate) >= 0) {
								DayOfWeek _dayOfWeek = DayOfWeekUtil.getDayOfWeek(e.getStartDate());
								return dayOfWeek == _dayOfWeek;
							}
						} else {
							return date.equals(startDate);
						}
						
						return false;
					}).
					collect(Collectors.toList());
		}
		
		Response response = new Response();
		response.setStudyClassScheduleTemplates(studyClassScheduleTemplates);
		
		return response;
	}
	
	
	@Data
	public static class Request extends BaseKwsCommandRequest
	{
		protected Date date;
	}
	
	
	@Data
	public static class Response extends BaseKwsCommandResponse
	{
		protected List<StudyClassScheduleTemplate> studyClassScheduleTemplates;
	}
}