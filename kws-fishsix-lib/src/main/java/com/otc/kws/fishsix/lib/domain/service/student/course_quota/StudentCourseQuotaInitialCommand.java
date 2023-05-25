package com.otc.kws.fishsix.lib.domain.service.student.course_quota;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.otc.kws.core.domain.exception.KwsRuntimeException;
import com.otc.kws.core.domain.service.BaseKwsCommand;
import com.otc.kws.core.domain.service.BaseKwsCommandRequest;
import com.otc.kws.core.domain.service.BaseKwsCommandResponse;
import com.otc.kws.fishsix.lib.domain.entity.Course;
import com.otc.kws.fishsix.lib.domain.entity.CourseOrder;
import com.otc.kws.fishsix.lib.domain.entity.Student;
import com.otc.kws.fishsix.lib.domain.entity.StudentCourseQuota;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaCourseOrderRepository;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaCourseRepository;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaStudentCourseQuotaRepository;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaStudentRepository;

import lombok.Data;

@Component
public class StudentCourseQuotaInitialCommand extends BaseKwsCommand
{
	@Autowired
	protected JpaCourseOrderRepository courseOrderRepository;
	
	@Autowired
	protected JpaCourseRepository courseRepository;
	
	@Autowired
	protected JpaStudentRepository studentRepository;
	
	@Autowired
	protected JpaStudentCourseQuotaRepository studentCourseQuotaRepository;
	
	
	public Response initialStudentCourseQuota(Request request)
	{
		logger.info("### {}.initialStudentCourseQuota ###", getClass().getSimpleName());
		
		CourseOrder courseOrder = request.getCourseOrder();
		Course course = request.getCourse();
		Student student = request.getStudent();
		
		if(courseOrder == null && request.getCourseOrderId() != null) {
			courseOrder = courseOrderRepository.findById(request.getCourseOrderId()).orElse(null);
		}
		
		if(course == null && request.getCourseId() != null) {
			course = courseRepository.findById(request.getCourseId()).orElse(null);
		}
		
		if(student == null && request.getStudentId() != null) {
			student = studentRepository.findById(request.getStudentId()).orElse(null);
		}
		
		if(courseOrder == null) {
			throw new KwsRuntimeException("CourseOrder Not Found");
		}
		
		if(course == null) {
			throw new KwsRuntimeException("Course Not Found");
		}
		
		if(student == null) {
			throw new KwsRuntimeException("Student Not Found");
		}
		
		Date activatedAt = request.getActivatedAt() != null ? request.getActivatedAt() : request.getPerformedAt();
		Date expiredAt = request.getExpiredAt() != null ? request.getExpiredAt() : initialExpiredAt(activatedAt, course.getExpirationPeriod());
		
		StudentCourseQuota studentCourseQuota = new StudentCourseQuota();
		studentCourseQuota.setId(UUID.randomUUID().toString());
        studentCourseQuota.setCourseOrderId(courseOrder.getId());
        studentCourseQuota.setCourseId(courseOrder.getCourseId());
        studentCourseQuota.setStudentId(courseOrder.getStudentId());
        studentCourseQuota.setInitialHour(course.getHour() + course.getExtraHour());
        studentCourseQuota.setUsedHour(0);
        studentCourseQuota.setActivatedAt(activatedAt);
        studentCourseQuota.setExpiredAt(expiredAt);
        studentCourseQuota.setCreatedBy(request.getPerformedBy());
		studentCourseQuota.setCreatedAt(request.getPerformedAt());
		
		StudentCourseQuota createdStudentCourseQuota = studentCourseQuotaRepository.save(studentCourseQuota);
		logger.info("Create StudentCourseQuota => {}", createdStudentCourseQuota);
		
		Response response = new Response();
		response.setCreatedStudentCourseQuota(createdStudentCourseQuota);
		
		return response;
	}
	
	protected Date initialExpiredAt(Date activatedAt, String expirationPeriod)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(activatedAt);
		
		int value = Integer.parseInt(expirationPeriod.substring(0, expirationPeriod.length() - 1));
		String unit = expirationPeriod.substring(expirationPeriod.length() - 1);
		
		if(unit.equals("M")) {
			calendar.add(Calendar.MONTH, value);
		} else if(unit.equals("Y")) {
			calendar.add(Calendar.YEAR, value);
		}
		
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		calendar.set(Calendar.HOUR_OF_DAY, calendar.getMaximum(Calendar.HOUR_OF_DAY));
		calendar.set(Calendar.MINUTE, calendar.getMaximum(Calendar.MINUTE));
		calendar.set(Calendar.SECOND, calendar.getMaximum(Calendar.SECOND));
		calendar.set(Calendar.MILLISECOND, calendar.getMinimum(Calendar.MILLISECOND));
		
		return calendar.getTime();
	}
	
	
	@Data
	public static class Request extends BaseKwsCommandRequest
	{
		protected String courseOrderId;
		protected String courseId;
		protected String studentId;
		protected CourseOrder courseOrder;
		protected Course course;
		protected Student student;
		protected Date activatedAt;
		protected Date expiredAt;
	}
	
	
	@Data
	public static class Response extends BaseKwsCommandResponse
	{
		protected StudentCourseQuota createdStudentCourseQuota;
	}
}