package com.otc.kws.fishsix.lib.domain.service.course_order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.otc.kws.core.domain.service.BaseKwsCommand;
import com.otc.kws.core.domain.service.BaseKwsCommandRequest;
import com.otc.kws.core.domain.service.BaseKwsCommandResponse;
import com.otc.kws.fishsix.lib.domain.entity.CourseOrder;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaCourseOrderRepository;

import lombok.Data;

@Component
public class CourseOrderLoadHistoryCommand extends BaseKwsCommand
{
	@Autowired
    protected JpaCourseOrderRepository courseOrderRepository;
	
	
	public Response loadCourseOrderHistories(Request request)
	{
		logger.info("### {}.loadCourseOrderHistories ###", getClass().getSimpleName());
		
		List<CourseOrder> courseOrders = null;
		
		if(request.getCourseId() != null && request.getStudentId() != null) {
			courseOrders = courseOrderRepository.findByCourseIdAndStudentId(request.getCourseId(), request.getStudentId());
		} else if(request.getCourseId() != null) {
			courseOrders = courseOrderRepository.findByCourseId(request.getCourseId());
		} else if(request.getStudentId() != null) {
			courseOrders = courseOrderRepository.findByStudentId(request.getStudentId());
		} else {
			courseOrders = courseOrderRepository.findAll();
		}
		
		Response response = new Response();
		response.setCourseOrders(courseOrders);
		
		return response;
	}
	
	
	@Data
	public static class Request extends BaseKwsCommandRequest
	{
		protected String courseId;
		protected String studentId;
	}
	
	@Data
	public static class Response extends BaseKwsCommandResponse
	{
		protected List<CourseOrder> courseOrders;
	}
}