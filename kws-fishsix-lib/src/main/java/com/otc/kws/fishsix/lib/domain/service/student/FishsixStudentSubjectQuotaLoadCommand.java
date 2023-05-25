package com.otc.kws.fishsix.lib.domain.service.student;

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
import com.otc.kws.fishsix.lib.domain.entity.Subject;
import com.otc.kws.fishsix.lib.domain.model.StudentCourseQuotaInfo;
import com.otc.kws.fishsix.lib.domain.model.SubjectProfile;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaCourseOrderRepository;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaSubjectRepository;
import com.otc.kws.fishsix.lib.domain.service.subject.FishsixSubjectService;

import lombok.Data;

@Component
public class FishsixStudentSubjectQuotaLoadCommand extends BaseKwsCommand
{
	@Autowired
	protected FishsixStudentService studentService;
	
	@Autowired
	protected JpaCourseOrderRepository courseOrderRepository;
	
	@Autowired
	protected JpaSubjectRepository subjectRepository;
	
	@Autowired
	protected FishsixSubjectService subjectService;
	
	
	public Response loadStudentSubjectQuota(Request request)
	{
		logger.info("### {}.loadStudentSubjectQuota ###", getClass().getSimpleName());
		
		Response response = new Response();
		
		List<StudentCourseQuotaInfo> studentCourseQuotaInfos = studentService.loadStudentCourseQuotaInfo(req -> {
			req.copyFrom(request);
			req.setStudentId(request.getStudentId());
		}).getStudentCourseQuotaInfos();
		
		if(studentCourseQuotaInfos == null || studentCourseQuotaInfos.isEmpty()) {
			return response;
		}
		
		List<String> courseOrderIds = studentCourseQuotaInfos.
				stream().
				map(e -> e.getStudentCourseQuota().getCourseOrderId()).
				distinct().
				collect(Collectors.toList());
		
		List<CourseOrder> courseOrders = courseOrderRepository.findAllById(courseOrderIds);
		
		if(courseOrders != null && !courseOrders.isEmpty()) {
			List<String> subjectIds = new ArrayList<>();
			
			for(CourseOrder courseOrder : courseOrders) {
				if(courseOrder.getSelectedSubjectIds() != null && !courseOrder.getSelectedSubjectIds().isEmpty()) {
					subjectIds.addAll(Arrays.asList(courseOrder.getSelectedSubjectIds().split(",")));
				}
			}
			
			if(! subjectIds.isEmpty()) {
				List<Subject> subjects = subjectRepository.findAllById(subjectIds);
				
				if(subjects != null && !subjects.isEmpty()) {
					List<SubjectProfile> subjectProfiles = subjectService.fetchSubject(req -> {
						req.copyFrom(request);
						req.setSubjects(subjects);
					}).getSubjectProfiles();
					
					response.setSubjectProfiles(subjectProfiles);
				}
			}
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
		protected List<SubjectProfile> subjectProfiles;
	}
}