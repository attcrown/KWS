package com.otc.kws.fishsix.lib.domain.service.class_schedule;

import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.otc.kws.core.domain.service.BaseKwsService;

@Service
public class StudyClassScheduleTemplateService extends BaseKwsService
{
	@Autowired
	protected StudyClassScheduleTemplateLoadCommand studyClassScheduleTemplateLoadCommand;
	
	@Autowired
	protected StudyClassScheduleTemplateCreateCommand studyClassScheduleTemplateCreateCommand;
	
	@Autowired
	protected StudyClassScheduleTemplateUpdateCommand studyClassScheduleTemplateUpdateCommand;
	
	@Autowired
	protected StudyClassScheduleTemplateRemoveCommand studyClassScheduleTemplateRemoveCommand;
	
	
	// ****************************** loadStudyClassScheduleTemplate ****************************** //
	@Transactional
	public StudyClassScheduleTemplateLoadCommand.Response loadStudyClassScheduleTemplate(Consumer<StudyClassScheduleTemplateLoadCommand.Request> consumer)
	{
		StudyClassScheduleTemplateLoadCommand.Request request = new StudyClassScheduleTemplateLoadCommand.Request();
		consumer.accept(request);
		return loadStudyClassScheduleTemplate(request);
	}
	
	@Transactional
	public StudyClassScheduleTemplateLoadCommand.Response loadStudyClassScheduleTemplate(StudyClassScheduleTemplateLoadCommand.Request request)
	{
		logger.info("### {}.loadStudyClassScheduleTemplate ###", getClass().getSimpleName());
		return studyClassScheduleTemplateLoadCommand.loadStudyClassScheduleTemplate(request);
	}
	// ****************************** loadStudyClassScheduleTemplate ****************************** //
	
	
	// ****************************** createStudyClassScheduleTemplate ****************************** //
	@Transactional
	public StudyClassScheduleTemplateCreateCommand.Response createStudyClassScheduleTemplate(Consumer<StudyClassScheduleTemplateCreateCommand.Request> consumer)
	{
		StudyClassScheduleTemplateCreateCommand.Request request = new StudyClassScheduleTemplateCreateCommand.Request();
		consumer.accept(request);
		return createStudyClassScheduleTemplate(request);
	}
	
	@Transactional
	public StudyClassScheduleTemplateCreateCommand.Response createStudyClassScheduleTemplate(StudyClassScheduleTemplateCreateCommand.Request request)
	{
		logger.info("### {}.createStudyClassScheduleTemplate ###", getClass().getSimpleName());
		return studyClassScheduleTemplateCreateCommand.createStudyClassScheduleTemplate(request);
	}
	// ****************************** createStudyClassScheduleTemplate ****************************** //
	
	
	// ****************************** updateStudyClassScheduleTemplate ****************************** //
	@Transactional
	public StudyClassScheduleTemplateUpdateCommand.Response updateStudyClassScheduleTemplate(Consumer<StudyClassScheduleTemplateUpdateCommand.Request> consumer)
	{
		StudyClassScheduleTemplateUpdateCommand.Request request = new StudyClassScheduleTemplateUpdateCommand.Request();
		consumer.accept(request);
		return updateStudyClassScheduleTemplate(request);
	}
	
	@Transactional
	public StudyClassScheduleTemplateUpdateCommand.Response updateStudyClassScheduleTemplate(StudyClassScheduleTemplateUpdateCommand.Request request)
	{
		logger.info("### {}.updateStudyClassScheduleTemplate ###", getClass().getSimpleName());
		return studyClassScheduleTemplateUpdateCommand.updateStudyClassScheduleTemplate(request);
	}
	// ****************************** updateStudyClassScheduleTemplate ****************************** //
	
	
	// ****************************** removeStudyClassScheduleTemplate ****************************** //
	@Transactional
	public StudyClassScheduleTemplateRemoveCommand.Response removeStudyClassScheduleTemplate(Consumer<StudyClassScheduleTemplateRemoveCommand.Request> consumer)
	{
		StudyClassScheduleTemplateRemoveCommand.Request request = new StudyClassScheduleTemplateRemoveCommand.Request();
		consumer.accept(request);
		return removeStudyClassScheduleTemplate(request);
	}
	
	@Transactional
	public StudyClassScheduleTemplateRemoveCommand.Response removeStudyClassScheduleTemplate(StudyClassScheduleTemplateRemoveCommand.Request request)
	{
		logger.info("### {}.removeStudyClassScheduleTemplate ###", getClass().getSimpleName());
		return studyClassScheduleTemplateRemoveCommand.removeStudyClassScheduleTemplate(request);
	}
	// ****************************** removeStudyClassScheduleTemplate ****************************** //
}