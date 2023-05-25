package com.otc.kws.fishsix.lib.domain.service.course;

import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.otc.kws.core.domain.service.BaseKwsService;

@Service
public class CourseService extends BaseKwsService
{
	@Autowired
	protected CourseFetchCommand courseFetchCommand;
	
	@Autowired
	protected CourseCreateCommand courseCreateCommand;
	
	@Autowired
	protected CourseUpdateCommand courseUpdateCommand;
	
	@Autowired
	protected CourseRemoveCommand courseRemoveCommand;
	
	@Autowired
	protected CourseAddSubjectCommand courseAddSubjectCommand;
	
	@Autowired
	protected CourseRemoveSubjectCommand courseRemoveSubjectCommand;
	
	
	// ****************************** fetchCourse ****************************** //
	public CourseFetchCommand.Response fetchCourse(Consumer<CourseFetchCommand.Request> consumer)
	{
		CourseFetchCommand.Request request = new CourseFetchCommand.Request();
		consumer.accept(request);
		return fetchCourse(request);
	}
	
	public CourseFetchCommand.Response fetchCourse(CourseFetchCommand.Request request)
	{
		logger.info("### {}.fetchCourse ###", getClass().getSimpleName());
		return courseFetchCommand.fetchCourse(request);
	}
	// ****************************** fetchCourse ****************************** //
	
	
	// ****************************** createCourse ****************************** //
	@Transactional
	public CourseCreateCommand.Response createCourse(Consumer<CourseCreateCommand.Request> consumer)
	{
		CourseCreateCommand.Request request = new CourseCreateCommand.Request();
		consumer.accept(request);
		return createCourse(request);
	}
	
	@Transactional
	public CourseCreateCommand.Response createCourse(CourseCreateCommand.Request request)
	{
		logger.info("### {}.createCourse ###", getClass().getSimpleName());
		return courseCreateCommand.createCourse(request);
	}
	// ****************************** createCourse ****************************** //
	
	
	// ****************************** updateCourse ****************************** //
	@Transactional
	public CourseUpdateCommand.Response updateCourse(Consumer<CourseUpdateCommand.Request> consumer)
	{
		CourseUpdateCommand.Request request = new CourseUpdateCommand.Request();
		consumer.accept(request);
		return updateCourse(request);
	}
	
	@Transactional
	public CourseUpdateCommand.Response updateCourse(CourseUpdateCommand.Request request)
	{
		logger.info("### {}.updateCourse ###", getClass().getSimpleName());
		return courseUpdateCommand.updateCourse(request);
	}
	// ****************************** updateCourse ****************************** //
	
	
	// ****************************** removeCourse ****************************** //
	@Transactional
	public CourseRemoveCommand.Response removeCourse(Consumer<CourseRemoveCommand.Request> consumer)
	{
		CourseRemoveCommand.Request request = new CourseRemoveCommand.Request();
		consumer.accept(request);
		return removeCourse(request);
	}
	
	@Transactional
	public CourseRemoveCommand.Response removeCourse(CourseRemoveCommand.Request request)
	{
		logger.info("### {}.removeCourse ###", getClass().getSimpleName());
		return courseRemoveCommand.removeCourse(request);
	}
	// ****************************** removeCourse ****************************** //
	
	
	// ****************************** addSubject ****************************** //
	@Transactional
	public CourseAddSubjectCommand.Response addSubject(Consumer<CourseAddSubjectCommand.Request> consumer)
	{
		CourseAddSubjectCommand.Request request = new CourseAddSubjectCommand.Request();
		consumer.accept(request);
		return addSubject(request);
	}
	
	@Transactional
	public CourseAddSubjectCommand.Response addSubject(CourseAddSubjectCommand.Request request)
	{
		logger.info("### {}.addSubject ###", getClass().getSimpleName());
		return courseAddSubjectCommand.addSubject(request);
	}
	// ****************************** addSubject ****************************** //
	
	
	// ****************************** removeSubject ****************************** //
	@Transactional
	public CourseRemoveSubjectCommand.Response removeSubject(Consumer<CourseRemoveSubjectCommand.Request> consumer)
	{
		CourseRemoveSubjectCommand.Request request = new CourseRemoveSubjectCommand.Request();
		consumer.accept(request);
		return removeSubject(request);
	}
	
	@Transactional
	public CourseRemoveSubjectCommand.Response removeSubject(CourseRemoveSubjectCommand.Request request)
	{
		logger.info("### {}.removeSubject ###", getClass().getSimpleName());
		return courseRemoveSubjectCommand.removeSubject(request);
	}
	// ****************************** removeSubject ****************************** //
}