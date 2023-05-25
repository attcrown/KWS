package com.otc.kws.fishsix.lib.gateway.web.controller.workspace.admin;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.otc.kws.core.domain.exception.KwsRuntimeException;
import com.otc.kws.fishsix.lib.domain.entity.EducationClass;
import com.otc.kws.fishsix.lib.domain.entity.EvaluationFactor;
import com.otc.kws.fishsix.lib.domain.entity.Student;
import com.otc.kws.fishsix.lib.domain.entity.StudyClassChannel;
import com.otc.kws.fishsix.lib.domain.entity.StudyClassLocation;
import com.otc.kws.fishsix.lib.domain.entity.StudyClassType;
import com.otc.kws.fishsix.lib.domain.model.StudyClassScheduleProfile;
import com.otc.kws.fishsix.lib.domain.model.SubjectProfile;
import com.otc.kws.fishsix.lib.domain.model.TeacherProfile;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaEducationClassRepository;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaEvaluationFactorRepository;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaStudentRepository;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaStudyClassChannelRepository;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaStudyClassLocationRepository;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaStudyClassTypeRepository;
import com.otc.kws.fishsix.lib.domain.service.study_class.StudyClassService;
import com.otc.kws.fishsix.lib.domain.service.subject.FishsixSubjectService;
import com.otc.kws.fishsix.lib.domain.service.teacher.FishsixTeacherService;
import com.otc.kws.fishsix.lib.gateway.web.controller.workspace.KwsFishsixWorkspaceController;

@Controller
@RequestMapping(KwsFishsixWorkspaceController.PREFIX_PATH + "/admin/study-class-schedule")
public class FishsixStudyClassScheduleController extends KwsFishsixWorkspaceController
{
	@Autowired
	protected JpaStudyClassChannelRepository studyClassChannelRepository;
	
	@Autowired
	protected JpaStudyClassTypeRepository studyClassTypeRepository;
	
	@Autowired
	protected JpaStudyClassLocationRepository studyClassLocationRepository;
	
	@Autowired
	protected JpaEducationClassRepository educationClassRepository;
	
	@Autowired
	protected FishsixSubjectService subjectService;
	
	@Autowired
	protected FishsixTeacherService teacherService;
	
	@Autowired
	protected JpaStudentRepository studentRepository;
	
	@Autowired
	protected StudyClassService studyClassService;
	
	@Autowired
	protected JpaEvaluationFactorRepository evaluationFactorRepository;
	

	@Override
	protected void setupModel(HttpServletRequest request, HttpServletResponse response, Model model) 
	{
		Date now = new Date();		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		
		CompletableFuture<List<StudyClassChannel>> classChannelsFuture = CompletableFuture.supplyAsync(() -> {
			return studyClassChannelRepository.findAllActive();
		});
		
		CompletableFuture<List<StudyClassType>> classTypesFuture = CompletableFuture.supplyAsync(() -> {
			return studyClassTypeRepository.findAllActive();
		});
		
		CompletableFuture<List<StudyClassLocation>> classLocationsFuture = CompletableFuture.supplyAsync(() -> {
			return studyClassLocationRepository.findAllActive();
		});
		
		CompletableFuture<List<EducationClass>> educationClassesFuture = CompletableFuture.supplyAsync(() -> {
			return educationClassRepository.findAll();
		});
		
		CompletableFuture<List<EvaluationFactor>> evaluationFactorsFuture = CompletableFuture.supplyAsync(() -> {
			return evaluationFactorRepository.findAll();
		});
		
		CompletableFuture<List<SubjectProfile>> subjectProfilesFuture = CompletableFuture.supplyAsync(() -> {
			return subjectService.fetchSubject(req -> {
				setupServiceRequest(request, req);
			}).getSubjectProfiles();
		});
		
		CompletableFuture<List<TeacherProfile>> teacherProfilesFuture = CompletableFuture.supplyAsync(() -> {
			return teacherService.fetchTeacher(req -> {
				setupServiceRequest(request, req);
			}).getTeacherProfiles();
		});
		
		CompletableFuture<List<Student>> studentsFuture = CompletableFuture.supplyAsync(() -> {
			return studentRepository.findAll();
		});
		
		CompletableFuture<StudyClassScheduleProfile> studyClassScheduleProfileFuture = CompletableFuture.supplyAsync(() -> {
			return studyClassService.loadStudyClassScheduleProfile(req -> {
				setupServiceRequest(request, req);
				req.setDate(now);
				req.setForceCreateEmptyStudyClasses(true);
			}).getStudyClassScheduleProfile();
		});
		
		CompletableFuture.allOf(
				classChannelsFuture, 
				classTypesFuture, 
				classLocationsFuture, 
				educationClassesFuture, 
				evaluationFactorsFuture, 
				subjectProfilesFuture, 
				teacherProfilesFuture, 
				studentsFuture, 
				studyClassScheduleProfileFuture
			);
		
		try {
			model.addAttribute("today", sdf.format(now));
			model.addAttribute("classChannels", classChannelsFuture.get());
			model.addAttribute("classTypes", classTypesFuture.get());
			model.addAttribute("classLocations", classLocationsFuture.get());
			model.addAttribute("educationClasses", educationClassesFuture.get());
			model.addAttribute("evaluationFactors", evaluationFactorsFuture.get());
			model.addAttribute("subjectProfiles", subjectProfilesFuture.get());
			model.addAttribute("teacherProfiles", teacherProfilesFuture.get());
			model.addAttribute("students", studentsFuture.get());
			model.addAttribute("studyClassScheduleProfile", studyClassScheduleProfileFuture.get());
		} catch(Exception ex) {
			throw new KwsRuntimeException(ex);
		}
	}
}