package com.otc.kws.fishsix.lib.domain.service.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.otc.kws.core.domain.constant.value.MasterStatusValue;
import com.otc.kws.core.domain.exception.KwsRuntimeException;
import com.otc.kws.core.domain.service.BaseKwsCommand;
import com.otc.kws.core.domain.service.BaseKwsCommandRequest;
import com.otc.kws.core.domain.service.BaseKwsCommandResponse;
import com.otc.kws.fishsix.lib.domain.entity.CourseSubjectCollection;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaCourseSubjectCollectionRepository;

import lombok.Data;

@Component
public class CourseRemoveSubjectCommand extends BaseKwsCommand
{
	@Autowired
	protected JpaCourseSubjectCollectionRepository courseSubjectCollectionRepository;
	
	
	public Response removeSubject(Request request)
	{
		logger.info("### {}.removeSubject ###", getClass().getSimpleName());
		
		CourseSubjectCollection removedCourseSubjectCollection = courseSubjectCollectionRepository.findById(request.getCourseSubjectCollectionId()).orElse(null);
		if(removedCourseSubjectCollection == null) {
			throw new KwsRuntimeException("CourseSubjectCollection Not Found");
		}
		
		if(request.isPhysicalRemove()) {
			courseSubjectCollectionRepository.delete(removedCourseSubjectCollection);
			logger.info("remove courseSubjectCollection => {}", removedCourseSubjectCollection);
		} else {
			removedCourseSubjectCollection.setStatus(MasterStatusValue.Closed);
			removedCourseSubjectCollection.setLastModifiedBy(request.getPerformedBy());
			removedCourseSubjectCollection.setLastModifiedAt(request.getPerformedAt());
			
			courseSubjectCollectionRepository.save(removedCourseSubjectCollection);
			logger.info("update courseSubjectCollection => {}", removedCourseSubjectCollection);
		}
		
		Response response = new Response();
		response.setRemovedCourseSubjectCollection(removedCourseSubjectCollection);
		
		return response;
	}
	
	
	@Data
	public static class Request extends BaseKwsCommandRequest
	{
		protected String courseSubjectCollectionId;
		protected boolean physicalRemove = false;
	}
	
	
	@Data
	public static class Response extends BaseKwsCommandResponse
	{
		protected CourseSubjectCollection removedCourseSubjectCollection;
	}
}