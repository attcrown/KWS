package com.otc.kws.fishsix.lib.domain.service.course;

import java.util.ArrayList;
import java.util.List;
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
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaCourseRepository;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaCourseSubjectCollectionRepository;

import lombok.Data;

@Component
public class CourseUpdateCommand extends BaseKwsCommand
{
	@Autowired
	protected JpaCourseRepository courseRepository;
	
	@Autowired
	protected JpaCourseSubjectCollectionRepository courseSubjectCollectionRepository;
	
	
	public Response updateCourse(Request request)
	{
		logger.info("### {}.updateCourse ###", getClass().getSimpleName());
		
		Response response = new Response();
		
		if(request.getCourse() == null) {
			throw new KwsRuntimeException("Course param is null");
		}
		
		if(request.getCourse().getId() == null) {
			throw new KwsRuntimeException("Course id is null");
		}
		
		if(courseRepository.findById(request.getCourse().getId()) == null) {
			throw new KwsRuntimeException("Course with id: ("+request.getCourse().getId()+") not found");
		}
		
		request.getCourse().setLastModifiedBy(request.getPerformedBy());
		request.getCourse().setLastModifiedAt(request.getPerformedAt());
		
		Course updatedCourse = courseRepository.save(request.getCourse());
		logger.info("update course => {}", updatedCourse);
		
		response.setUpdatedCourse(updatedCourse);
		
		if(request.getSubjectIds() != null && !request.getSubjectIds().isEmpty()) {
			List<CourseSubjectCollection> removedCourseSubjectCollections = courseSubjectCollectionRepository.removeByCourseId(updatedCourse.getId());
			logger.info("remove courseSubjectCollection => {}", removedCourseSubjectCollections);
			courseSubjectCollectionRepository.flush();
			
			List<CourseSubjectCollection> createdCourseSubjectCollections = new ArrayList<>();
			
			for(String subjectId : request.getSubjectIds()) {
				CourseSubjectCollection courseSubjectCollection = new CourseSubjectCollection();
				courseSubjectCollection.setId(UUID.randomUUID().toString());
				courseSubjectCollection.setCourseId(updatedCourse.getId());
				courseSubjectCollection.setSubjectId(subjectId);
				courseSubjectCollection.setStatus(MasterStatusValue.Active);
				courseSubjectCollection.setCreatedBy(request.getPerformedBy());
				courseSubjectCollection.setCreatedAt(request.getPerformedAt());
				courseSubjectCollection.setLastModifiedBy(request.getPerformedBy());
				courseSubjectCollection.setLastModifiedAt(request.getPerformedAt());
				
				CourseSubjectCollection createdCourseSubjectCollection = courseSubjectCollectionRepository.save(courseSubjectCollection);
				logger.info("create courseSubjectCollection => {}", createdCourseSubjectCollection);
				
				createdCourseSubjectCollections.add(createdCourseSubjectCollection);
			}
			
			response.setCreatedCourseSubjectCollections(createdCourseSubjectCollections);
		}
		
		return response;
	}
	
	
	@Data
	public static class Request extends BaseKwsCommandRequest
	{
		protected Course course;
		protected List<String> subjectIds;
	}
	
	
	@Data
	public static class Response extends BaseKwsCommandResponse
	{
		protected Course updatedCourse;
		protected List<CourseSubjectCollection> createdCourseSubjectCollections;
	}
}