package com.otc.kws.fishsix.lib.domain.service.course;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.otc.kws.core.domain.constant.value.MasterStatusValue;
import com.otc.kws.core.domain.exception.KwsRuntimeException;
import com.otc.kws.core.domain.service.BaseKwsCommand;
import com.otc.kws.core.domain.service.BaseKwsCommandRequest;
import com.otc.kws.core.domain.service.BaseKwsCommandResponse;
import com.otc.kws.fishsix.lib.domain.entity.Course;
import com.otc.kws.fishsix.lib.domain.entity.CourseSubjectCollection;
import com.otc.kws.fishsix.lib.domain.entity.Subject;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaCourseRepository;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaCourseSubjectCollectionRepository;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaSubjectRepository;

import lombok.Data;

@Component
public class CourseAddSubjectCommand extends BaseKwsCommand
{
	@Autowired
	protected JpaCourseRepository courseRepository;
	
	@Autowired
	protected JpaSubjectRepository subjectRepository;
	
	@Autowired
	protected JpaCourseSubjectCollectionRepository courseSubjectCollectionRepository;
	
	
	public Response addSubject(Request request) 
	{
		logger.info("### {}.addSubject ###", getClass().getSimpleName());
		
		Course course = courseRepository.findById(request.getCourseSubjectCollection().getCourseId()).orElse(null);
		if(course == null) {
			throw new KwsRuntimeException("Course Not Found");
		}
		
		Subject subject = subjectRepository.findById(request.getCourseSubjectCollection().getSubjectId()).orElse(null);
		if(subject == null) {
			throw new KwsRuntimeException("Subject Not Found");
		}
		
		CourseSubjectCollection courseSubjectCollection = courseSubjectCollectionRepository.findByCourseIdAndSubjectId(request.getCourseSubjectCollection().getCourseId(), request.getCourseSubjectCollection().getSubjectId());
		
		if(courseSubjectCollection != null) {
			courseSubjectCollectionRepository.delete(courseSubjectCollection);
			logger.info("remove courseSubjectCollection => {}", courseSubjectCollection);
		}
		
		if(request.getCourseSubjectCollection().getId() == null) {
			request.getCourseSubjectCollection().setId(UUID.randomUUID().toString());
		}
		
		if(request.getCourseSubjectCollection().getStatus() == null) {
			request.getCourseSubjectCollection().setStatus(MasterStatusValue.Active);
		}
		
		request.getCourseSubjectCollection().setCreatedBy(request.getPerformedBy());
		request.getCourseSubjectCollection().setCreatedAt(request.getPerformedAt());
		request.getCourseSubjectCollection().setLastModifiedBy(request.getPerformedBy());
		request.getCourseSubjectCollection().setLastModifiedAt(request.getPerformedAt());
		
		CourseSubjectCollection addedCourseSubjectCollection = courseSubjectCollectionRepository.save(request.getCourseSubjectCollection());
		logger.info("create courseSubjectCollection => {}", addedCourseSubjectCollection);
		
		Response response = new Response();
		response.setAddedCourseSubjectCollection(addedCourseSubjectCollection);
		
		return response;
	}
	
	
	@Data
	public static class Request extends BaseKwsCommandRequest
	{
		protected CourseSubjectCollection courseSubjectCollection;
	}
	
	
	@Data
	public static class Response extends BaseKwsCommandResponse
	{
		protected CourseSubjectCollection addedCourseSubjectCollection;
	}
}