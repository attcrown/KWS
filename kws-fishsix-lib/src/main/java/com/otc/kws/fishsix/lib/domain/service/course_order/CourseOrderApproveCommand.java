package com.otc.kws.fishsix.lib.domain.service.course_order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.otc.kws.core.domain.exception.KwsRuntimeException;
import com.otc.kws.core.domain.service.BaseKwsCommand;
import com.otc.kws.core.domain.service.BaseKwsCommandRequest;
import com.otc.kws.core.domain.service.BaseKwsCommandResponse;
import com.otc.kws.fishsix.lib.domain.constant.value.CourseOrderStatusValue;
import com.otc.kws.fishsix.lib.domain.entity.Course;
import com.otc.kws.fishsix.lib.domain.entity.CourseOrder;
import com.otc.kws.fishsix.lib.domain.entity.StudentCourseQuota;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaCourseOrderRepository;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaCourseRepository;
import com.otc.kws.fishsix.lib.domain.service.student.FishsixStudentService;

import lombok.Data;

@Component
public class CourseOrderApproveCommand extends BaseKwsCommand
{
	@Autowired
	protected JpaCourseRepository courseRepository;
	
	@Autowired
    protected JpaCourseOrderRepository courseOrderRepository;
    
    @Autowired
    protected FishsixStudentService studentService;
    
    
    public Response approveCourseOrder(Request request)
    {
    	logger.info("### {}.approveCourseOrder ###", getClass().getSimpleName());
    	
    	CourseOrder courseOrder = request.getCourseOrder();
    	
    	if(courseOrder == null) {
    		throw new KwsRuntimeException("CourseOrder Is Null");
    	}
    	
    	if(courseOrderRepository.findById(courseOrder.getId()).isEmpty()) {
    		throw new KwsRuntimeException("CourseOrder {id: "+courseOrder.getId()+"} Not Found");
    	}
    	
    	if(courseOrder.getOrderStatusId() != CourseOrderStatusValue.Pending) {
    		throw new KwsRuntimeException("Invalid CourseOrder OrderStatus");
    	}
    	
    	Course course = courseRepository.findById(courseOrder.getCourseId()).orElse(null);
    	
    	courseOrder.setApprovedBy(request.getPerformedBy());
    	courseOrder.setApprovedAt(request.getPerformedAt());
    	
    	if(courseOrder.getPaymentAt() == null) {
    		courseOrder.setApprovedAt(request.getPerformedAt());
    	}
    	
    	CourseOrder approvedCourseOrder = courseOrderRepository.save(courseOrder);
    	logger.info("Approve Course Order => {}", approvedCourseOrder);
    	
    	// ****************************** create studentCourseQuota ****************************** //
        StudentCourseQuota studentCourseQuota = new StudentCourseQuota();
        studentCourseQuota.setCourseOrderId(courseOrder.getId());
        studentCourseQuota.setCourseId(courseOrder.getCourseId());
        studentCourseQuota.setStudentId(courseOrder.getStudentId());
        studentCourseQuota.setInitialHour(course.getHour() + course.getExtraHour());
        studentCourseQuota.setUsedHour(0);
        
        StudentCourseQuota createdStudentCourseQuota = studentService.createStudentCourseQuota(req -> {
        	req.copyFrom(request);
        	req.setStudentCourseQuota(studentCourseQuota);
        }).getCreatedStudentCourseQuota();
        // ****************************** create studentCourseQuota ****************************** //
    	
    	Response response = new Response();
    	response.setApprovedCourseOrder(approvedCourseOrder);
    	response.setCreatedStudentCourseQuota(createdStudentCourseQuota);
    	
    	return response;
    }
	
	
	@Data
	public static class Request extends BaseKwsCommandRequest
	{
		protected CourseOrder courseOrder;
	}
	
	
	@Data
	public static class Response extends BaseKwsCommandResponse
	{
		protected CourseOrder approvedCourseOrder;
		protected StudentCourseQuota createdStudentCourseQuota;
	}
}