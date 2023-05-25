package com.otc.kws.fishsix.lib.domain.service.course_order;

import com.otc.kws.core.domain.exception.KwsRuntimeException;
import com.otc.kws.core.domain.service.BaseKwsCommand;
import com.otc.kws.core.domain.service.BaseKwsCommandRequest;
import com.otc.kws.core.domain.service.BaseKwsCommandResponse;
import com.otc.kws.fishsix.lib.domain.constant.value.CourseOrderStatusValue;
import com.otc.kws.fishsix.lib.domain.entity.Course;
import com.otc.kws.fishsix.lib.domain.entity.CourseOrder;
import com.otc.kws.fishsix.lib.domain.entity.Student;
import com.otc.kws.fishsix.lib.domain.entity.StudentCourseQuota;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaCourseOrderRepository;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaCourseRepository;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaStudentRepository;
import com.otc.kws.fishsix.lib.domain.service.student.FishsixStudentService;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CourseOrderCreateCommand extends BaseKwsCommand
{
    @Autowired
    protected JpaStudentRepository studentRepository;

    @Autowired
    protected JpaCourseRepository courseRepository;

    @Autowired
    protected JpaCourseOrderRepository courseOrderRepository;
    
    @Autowired
    protected FishsixStudentService studentService;


    public Response createCourseOrder(Request request)
    {
        logger.info("### {}.createCourseOrder ###", getClass().getSimpleName());

        CourseOrder courseOrder = request.getCourseOrder();

        if(courseOrder.getCourseId() == null) {
            throw new KwsRuntimeException("กรุณาระบุคอร์สที่ต้องการซื้อ");
        }
        
        Course course = courseRepository.findById(courseOrder.getCourseId()).orElse(null);

        if(course == null) {
            throw new KwsRuntimeException("ไม่พบคอร์สที่ต้องการซื้อ");
        }

        if(courseOrder.getStudentId() == null) {
            throw new KwsRuntimeException("กรุณาระบุนักเรียนที่ต้องการซื้อคอร์ส");
        }
        
        Student student = studentRepository.findById(courseOrder.getStudentId()).orElse(null);

        if(student == null) {
            throw new KwsRuntimeException("ไม่พบข้อมูลนักเรียน");
        }

        if(courseOrder.getId() == null) {
            courseOrder.setId(UUID.randomUUID().toString());
        }

        if(courseOrder.getOrderStatusId() == null) {
            courseOrder.setOrderStatusId(CourseOrderStatusValue.Pending);
        }

        if(courseOrder.getOrderedBy() == null) {
            courseOrder.setOrderedBy(request.getPerformedBy());
        }

        if(courseOrder.getOrderedAt() == null) {
            courseOrder.setOrderedAt(request.getPerformedAt());
        }

        if(courseOrder.getOrderStatusId().equals(CourseOrderStatusValue.Cancelled)) {
            if(courseOrder.getCancelledBy() == null) {
                courseOrder.setCancelledBy(request.getPerformedBy());
            }

            if(courseOrder.getCancelledAt() == null) {
                courseOrder.setCancelledAt(request.getPerformedAt());
            }
        }

        if(courseOrder.getOrderStatusId().equals(CourseOrderStatusValue.Rejected)) {
            if(courseOrder.getRejectedBy() == null) {
                courseOrder.setRejectedBy(request.getPerformedBy());
            }

            if(courseOrder.getRejectedAt() == null) {
                courseOrder.setRejectedAt(request.getPerformedAt());
            }
        }

        if(courseOrder.getOrderStatusId().equals(CourseOrderStatusValue.Approved)) {
            if(courseOrder.getApprovedBy() == null) {
                courseOrder.setApprovedBy(request.getPerformedBy());
            }

            if(courseOrder.getApprovedAt() == null) {
                courseOrder.setApprovedAt(request.getPerformedAt());
            }
            
            if(courseOrder.getPaymentAt() == null) {
            	courseOrder.setPaymentAt(request.getPerformedAt());
            }
        }
        
        if(courseOrder.getPaymentAmount() == null) {
        	courseOrder.setPaymentAmount(course.getPrice().subtract(course.getDiscountPrice()));
        }

        CourseOrder createdCourseOrder = courseOrderRepository.save(courseOrder);
        logger.info("Create Course Order => {}", createdCourseOrder);
        
        Response response = new Response();
        response.setCreatedCourseOrder(createdCourseOrder);
        
        if(courseOrder.getOrderStatusId() == CourseOrderStatusValue.Approved) {
        	// ****************************** initial studentCourseQuota ****************************** //
            StudentCourseQuota createdStudentCourseQuota = studentService.initialStudentCourseQuota(req -> {
            	req.copyFrom(request);
            	req.setCourseOrder(createdCourseOrder);
            	req.setCourse(course);
            	req.setStudent(student);
            }).getCreatedStudentCourseQuota();
            // ****************************** initial studentCourseQuota ****************************** //
            
            response.setCreatedStudentCourseQuota(createdStudentCourseQuota);
        }

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
        protected CourseOrder createdCourseOrder;
        protected StudentCourseQuota createdStudentCourseQuota;
    }
}