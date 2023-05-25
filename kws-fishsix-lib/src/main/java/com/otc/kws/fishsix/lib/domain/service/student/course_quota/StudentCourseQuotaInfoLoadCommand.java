package com.otc.kws.fishsix.lib.domain.service.student.course_quota;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.otc.kws.core.domain.service.BaseKwsCommand;
import com.otc.kws.core.domain.service.BaseKwsCommandRequest;
import com.otc.kws.core.domain.service.BaseKwsCommandResponse;
import com.otc.kws.fishsix.lib.domain.entity.CourseOrder;
import com.otc.kws.fishsix.lib.domain.entity.StudentCourseQuota;
import com.otc.kws.fishsix.lib.domain.entity.StudyClass;
import com.otc.kws.fishsix.lib.domain.entity.StudyClassSchedule;
import com.otc.kws.fishsix.lib.domain.entity.StudyClassSchedule.ClassStatus;
import com.otc.kws.fishsix.lib.domain.entity.StudyClassSchedule.StudentCheckinStatus;
import com.otc.kws.fishsix.lib.domain.model.StudentCourseQuotaInfo;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaCourseOrderRepository;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaStudentCourseQuotaRepository;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaStudyClassRepository;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaStudyClassScheduleRepository;

import lombok.Data;

@Component
public class StudentCourseQuotaInfoLoadCommand extends BaseKwsCommand
{
	@Autowired
	protected JpaStudentCourseQuotaRepository studentCourseQuotaRepository;
	
	@Autowired
	protected JpaCourseOrderRepository courseOrderRepository;
	
	@Autowired
	protected JpaStudyClassRepository studyClassRepository;
	
	@Autowired
	protected JpaStudyClassScheduleRepository studyClassScheduleRepository;
	
	
	public Response loadStudentCourseQuotaInfo(Request request)
	{
		logger.info("### {}.loadStudentCourseQuotaInfo ###", getClass().getSimpleName());
		
		Response response = new Response();
		
		List<StudentCourseQuota> studentCourseQuotas = studentCourseQuotaRepository.findActiveQuotaByStudentId(request.getStudentId());
		
		if(studentCourseQuotas != null && !studentCourseQuotas.isEmpty()) {
			List<StudentCourseQuotaInfo> studentCourseQuotaInfos = new ArrayList<>();
			
			List<CourseOrder> courseOrders = courseOrderRepository.findAllById(
					studentCourseQuotas.
					stream().
					map(StudentCourseQuota::getCourseOrderId).
					distinct().
					collect(Collectors.toList())
				);
			
			List<StudyClassSchedule> studyClassSchedules = studyClassScheduleRepository.findByStudentCourseQuotaIds(
					studentCourseQuotas.
					stream().
					map(StudentCourseQuota::getId).
					distinct().
					collect(Collectors.toList())
				);
			
			if(studyClassSchedules != null && !studyClassSchedules.isEmpty()) {
				studyClassSchedules = studyClassSchedules.
						stream().
						filter(e -> e.getClassStatus() != ClassStatus.Cancelled).
						collect(Collectors.toList());
			}
			
			List<StudyClass> studyClasses = null;
			
			if(studyClassSchedules != null && !studyClassSchedules.isEmpty()) {
				studyClasses = studyClassRepository.findAllById(
						studyClassSchedules.
						stream().
						map(StudyClassSchedule::getClassId).
						distinct().
						collect(Collectors.toList())
					);
			}
			
			int summaryTotalHour = 0;
			int summaryStudiedHour = 0;
			int summaryReservedHour = 0;
			int summaryLeavedHour = 0;
			int summaryAvailableHour = 0;
			
			for(StudentCourseQuota studentCourseQuota : studentCourseQuotas) {
				List<StudyClassSchedule> _studyClassSchedules = null;
				
				if(studyClassSchedules != null && !studyClassSchedules.isEmpty()) {
					_studyClassSchedules = studyClassSchedules.
							stream().
							filter(e -> e.getStudentCourseQuotaId().equals(studentCourseQuota.getId())).
							collect(Collectors.toList());
				}
				
				StudentCourseQuotaInfo studentCourseQuotaInfo = new StudentCourseQuotaInfo();
				studentCourseQuotaInfo.setStudentCourseQuota(studentCourseQuota);
				studentCourseQuotaInfo.setTotalHour(studentCourseQuota.getInitialHour());
				studentCourseQuotaInfo.setStudiedHour(0);
				studentCourseQuotaInfo.setReservedHour(0);
				studentCourseQuotaInfo.setLeavedHour(0);
				studentCourseQuotaInfo.setAvailableHour(studentCourseQuotaInfo.getTotalHour());
				
				if(courseOrders != null && !courseOrders.isEmpty()) {
					CourseOrder courseOrder = courseOrders.
							stream().
							filter(e -> e.getId().equals(studentCourseQuota.getCourseOrderId())).
							findFirst().
							orElse(null);
					
					if(courseOrder != null && courseOrder.getSelectedSubjectIds() != null && !courseOrder.getSelectedSubjectIds().isEmpty()) {
						studentCourseQuotaInfo.setSubjectedIds(Arrays.asList(courseOrder.getSelectedSubjectIds().split(",")));
					}
				}
				
				if(_studyClassSchedules != null && !_studyClassSchedules.isEmpty()) {
					int studiedHour = 0;
					int reservedHour = 0;
					int leavedHour = 0;
					
					for(StudyClassSchedule studyClassSchedule : _studyClassSchedules) {
						StudyClass studyClass = studyClasses.
								stream().
								filter(e -> e.getId().equals(studyClassSchedule.getClassId())).
								findFirst().
								orElse(null);
						
						if(studyClass == null) {
							continue;
						}
						
						if(studyClassSchedule.getClassStatus() == ClassStatus.Reserved || studyClassSchedule.getClassStatus() == ClassStatus.Confirmed) {
							reservedHour += studyClass.getClassHour();
						} else if(studyClassSchedule.getClassStatus() == ClassStatus.Completed) {
							if(studyClassSchedule.getStudentCheckinStatus() == StudentCheckinStatus.Leave) {
								leavedHour += studyClass.getClassHour();
							} else {
								studiedHour += studyClass.getClassHour();
							}
						}
					}
					
					studentCourseQuotaInfo.setStudiedHour(studiedHour);
					studentCourseQuotaInfo.setReservedHour(reservedHour);
					studentCourseQuotaInfo.setLeavedHour(leavedHour);
					studentCourseQuotaInfo.setAvailableHour(studentCourseQuotaInfo.getTotalHour() - studiedHour - reservedHour - leavedHour);
				}
				
				summaryTotalHour += studentCourseQuotaInfo.getTotalHour();
				summaryStudiedHour += studentCourseQuotaInfo.getStudiedHour();
				summaryReservedHour += studentCourseQuotaInfo.getReservedHour();
				summaryLeavedHour += studentCourseQuotaInfo.getLeavedHour();
				summaryAvailableHour += studentCourseQuotaInfo.getAvailableHour();
				
				studentCourseQuotaInfos.add(studentCourseQuotaInfo);
			}
			
			if(studentCourseQuotaInfos != null && !studentCourseQuotaInfos.isEmpty()) {
				studentCourseQuotaInfos.sort((o1, o2) -> o1.getStudentCourseQuota().getActivatedAt().compareTo(o2.getStudentCourseQuota().getActivatedAt()));
			}
			
			StudentCourseQuotaInfo summaryStudentCourseQuotaInfo = new StudentCourseQuotaInfo();
			summaryStudentCourseQuotaInfo.setTotalHour(summaryTotalHour);
			summaryStudentCourseQuotaInfo.setStudiedHour(summaryStudiedHour);
			summaryStudentCourseQuotaInfo.setReservedHour(summaryReservedHour);
			summaryStudentCourseQuotaInfo.setLeavedHour(summaryLeavedHour);
			summaryStudentCourseQuotaInfo.setAvailableHour(summaryAvailableHour);
			
			response.setStudentCourseQuotaInfos(studentCourseQuotaInfos);
			response.setSummaryStudentCourseQuotaInfo(summaryStudentCourseQuotaInfo);
		}
		
		return response;
	}
	
	
	@Data
	public static class Request extends BaseKwsCommandRequest
	{
		protected String studentId;
	}
	
	
	@Data
	public static class Response extends BaseKwsCommandResponse
	{
		protected List<StudentCourseQuotaInfo> studentCourseQuotaInfos;
		protected StudentCourseQuotaInfo summaryStudentCourseQuotaInfo;
	}
}