package com.otc.kws.fishsix.lib.gateway.web.controller.workspace.admin.student;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.otc.kws.core.domain.entity.Salesman;
import com.otc.kws.core.domain.exception.KwsRuntimeException;
import com.otc.kws.core.domain.repository.jpa.JpaSalesmanRepository;
import com.otc.kws.fishsix.lib.domain.entity.CourseOrderStatus;
import com.otc.kws.fishsix.lib.domain.entity.Student;
import com.otc.kws.fishsix.lib.domain.entity.StudyClassChannel;
import com.otc.kws.fishsix.lib.domain.entity.StudyClassType;
import com.otc.kws.fishsix.lib.domain.entity.Teacher;
import com.otc.kws.fishsix.lib.domain.model.CourseProfile;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaCourseOrderStatusRepository;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaStudentRepository;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaStudyClassChannelRepository;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaStudyClassTypeRepository;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaTeacherRepository;
import com.otc.kws.fishsix.lib.domain.service.course.CourseService;
import com.otc.kws.fishsix.lib.gateway.web.controller.workspace.KwsFishsixWorkspaceController;

@Controller("com.otc.kws.fishsix.lib.gateway.web.controller.workspace.admin.student.FishsixStudentCourseHistoryController")
@RequestMapping(KwsFishsixWorkspaceController.PREFIX_PATH + "/admin/student/course-history")
public class FishsixStudentCourseHistoryController extends KwsFishsixWorkspaceController
{
	@Autowired
	protected JpaStudyClassChannelRepository studyClassChannelRepository;
	
	@Autowired
	protected JpaStudyClassTypeRepository studyClassTypeRepository;
	
	@Autowired
	protected JpaStudentRepository studentRepository;
	
	@Autowired
	protected JpaTeacherRepository teacherRepository;
	
	@Autowired
	protected CourseService courseService;
	
	@Autowired
	protected JpaCourseOrderStatusRepository courseOrderStatusRepository;
	
	@Autowired
	protected JpaSalesmanRepository salesmanRepository;
	
	
	@Override
	protected void setupModel(HttpServletRequest request, HttpServletResponse response, Model model) 
	{
		CompletableFuture<List<StudyClassChannel>> classChannelsFuture = CompletableFuture.supplyAsync(() -> {
			return studyClassChannelRepository.findAllActive();
		});
		
		CompletableFuture<List<StudyClassType>> classTypesFuture = CompletableFuture.supplyAsync(() -> {
			return studyClassTypeRepository.findAllActive();
		});
		
		CompletableFuture<List<Student>> studentsFuture = CompletableFuture.supplyAsync(() -> {
			return studentRepository.findAll();
		});
		
		CompletableFuture<List<Teacher>> teachersFuture = CompletableFuture.supplyAsync(() -> {
			return teacherRepository.findAll();
		});
		
		CompletableFuture<List<CourseProfile>> courseProfilesFuture = CompletableFuture.supplyAsync(() -> {
			return courseService.fetchCourse(req -> {
				setupServiceRequest(request, req);
			}).getCourseProfiles();
		});
		
		CompletableFuture<List<CourseOrderStatus>> orderStatusesFuture = CompletableFuture.supplyAsync(() -> {
			return courseOrderStatusRepository.findAllActive();
		});
		
		CompletableFuture<List<Salesman>> salesmansFuture = CompletableFuture.supplyAsync(() -> {
			return salesmanRepository.findAllActive();
		});
		
		CompletableFuture.allOf(
				classChannelsFuture, 
				classTypesFuture, 
				studentsFuture, 
				teachersFuture, 
				courseProfilesFuture, 
				orderStatusesFuture, 
				salesmansFuture
			);
		
		try {
			model.addAttribute("classChannels", classChannelsFuture.get());
			model.addAttribute("classTypes", classTypesFuture.get());
			model.addAttribute("students", studentsFuture.get());
			model.addAttribute("teachers", teachersFuture.get());
			model.addAttribute("courseProfiles", courseProfilesFuture.get());
			model.addAttribute("orderStatuses", orderStatusesFuture.get());
			model.addAttribute("salesmans", salesmansFuture.get());
		} catch(Exception ex) {
			throw new KwsRuntimeException(ex);
		}
	}
}