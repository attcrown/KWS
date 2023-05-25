package com.otc.kws.fishsix.lib.gateway.web.controller.workspace.admin.teacher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.otc.kws.core.domain.entity.EducationLevel;
import com.otc.kws.core.domain.entity.EducationStatus;
import com.otc.kws.core.domain.entity.Gender;
import com.otc.kws.core.domain.entity.NameTitle;
import com.otc.kws.core.domain.repository.jpa.JpaEducationLevelRepository;
import com.otc.kws.core.domain.repository.jpa.JpaEducationStatusRepository;
import com.otc.kws.core.domain.repository.jpa.JpaGenderRepository;
import com.otc.kws.core.domain.repository.jpa.JpaNameTitleRepository;
import com.otc.kws.fishsix.lib.domain.entity.StudyClassLocation;
import com.otc.kws.fishsix.lib.domain.entity.TeacherRegister;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaEducationClassRepository;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaStudyClassChannelRepository;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaStudyClassLocationRepository;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaSubjectCategoryRepository;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaSubjectRepository;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaTeacherHireTypeRepository;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaTeacherWorktimeTypeRepository;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaWageRateTypeRepository;
import com.otc.kws.fishsix.lib.domain.service.teacher_register.FishsixTeacherRegisterService;
import com.otc.kws.fishsix.lib.gateway.web.controller.workspace.KwsFishsixWorkspaceController;

@Controller("com.otc.kws.fishsix.lib.gateway.web.controller.workspace.admin.teacher.FishsixTeacherNewRegistrationController")
@RequestMapping(KwsFishsixWorkspaceController.PREFIX_PATH + "/admin/teacher/new-registration")
public class FishsixTeacherNewRegistrationController extends KwsFishsixWorkspaceController
{
	@Autowired
	protected FishsixTeacherRegisterService teacherRegisterService;
	
	@Autowired
	protected JpaGenderRepository genderRepository;
	
	@Autowired
	protected JpaNameTitleRepository nameTitleRepository;
	
	@Autowired
	protected JpaEducationLevelRepository educationLevelRepository;
	
	@Autowired
	protected JpaEducationStatusRepository educationStatusRepository;
	
	@Autowired
	protected JpaStudyClassChannelRepository studyClassChannelRepository;
	
	@Autowired
	protected JpaStudyClassLocationRepository studyClassLocationRepository;
	
	@Autowired
	protected JpaWageRateTypeRepository wageRateTypeRepository;
	
	@Autowired
	protected JpaTeacherHireTypeRepository hireTypeRepository;
	
	@Autowired
	protected JpaTeacherWorktimeTypeRepository worktimeTypeRepository;
	
	@Autowired
	protected JpaEducationClassRepository educationClassRepository;
	
	@Autowired
	protected JpaSubjectCategoryRepository subjectCategoryRepository;
	
	@Autowired
	protected JpaSubjectRepository subjectRepository;
	
	
	@Override
	protected void setupModel(HttpServletRequest request, HttpServletResponse response, Model model) 
	{
		List<TeacherRegister> teacherRegisters = teacherRegisterService.search(req -> {
			setupServiceRequest(request, req);
		}).getTeacherRegisters();
		
		model.addAttribute("teacherRegisters", teacherRegisters);
		model.addAttribute("genders", loadGenders(request));
		model.addAttribute("nameTitles", loadNameTitles(request));
		model.addAttribute("educationStatuses", loadEducationStatuses(request));
		model.addAttribute("educationLevels", loadEducationLevels(request));
		model.addAttribute("studyClassLocations", loadStudyClassLocations(request));
		model.addAttribute("wageRateTypes", wageRateTypeRepository.findAllActive());
		model.addAttribute("hireTypes", hireTypeRepository.findAllActive());
		model.addAttribute("worktimeTypes", worktimeTypeRepository.findAllActive());
		model.addAttribute("educationClasses", educationClassRepository.findAllActive());
		model.addAttribute("subjectCategories", subjectCategoryRepository.findAllActive());
		model.addAttribute("subjects", subjectRepository.findAllActive());
	}
	
	
	protected List<Map<String, String>> loadGenders(HttpServletRequest request)
	{
		List<Map<String, String>> datas = new ArrayList<>();
		List<Gender> genders = genderRepository.findAllActive();
		if(genders != null && !genders.isEmpty()) {
			for(Gender gender : genders) {
				Map<String, String> map = new HashMap<>();
				map.put("id", gender.getId());
				map.put("name", gender.getName());
				datas.add(map);
			}
		}
		return datas;
	}
	
	protected List<Map<String, String>> loadNameTitles(HttpServletRequest request)
	{
		List<Map<String, String>> datas = new ArrayList<>();
		List<NameTitle> nameTitles = nameTitleRepository.findAllActive();
		if(nameTitles != null && !nameTitles.isEmpty()) {
			for(NameTitle nameTitle : nameTitles) {
				Map<String, String> map = new HashMap<>();
				map.put("id", nameTitle.getId());
				map.put("title", nameTitle.getTitle());
				datas.add(map);
			}
		}
		return datas;
	}
	
	protected List<Map<String, String>> loadEducationStatuses(HttpServletRequest request)
	{
		List<Map<String, String>> datas = new ArrayList<>();
		List<EducationLevel> educationLevels = educationLevelRepository.findAllById(Arrays.asList("Bachelor", "Master", "Doctor"));
		if(educationLevels != null && !educationLevels.isEmpty()) {
			for(EducationLevel educationLevel : educationLevels) {
				Map<String, String> map = new HashMap<>();
				map.put("id", educationLevel.getId());
				map.put("name", educationLevel.getName());
				datas.add(map);
			}
		}
		return datas;
	}
	
	protected List<Map<String, String>> loadEducationLevels(HttpServletRequest request)
	{
		List<Map<String, String>> datas = new ArrayList<>();
		List<EducationStatus> educationStatuses = educationStatusRepository.findAllById(Arrays.asList("studying", "graduate"));
		if(educationStatuses != null && !educationStatuses.isEmpty()) {
			for(EducationStatus educationStatus : educationStatuses) {
				Map<String, String> map = new HashMap<>();
				map.put("id", educationStatus.getId());
				map.put("name", educationStatus.getName());
				datas.add(map);
			}
		}
		return datas;
	}
	
	protected List<Map<String, String>> loadStudyClassLocations(HttpServletRequest request)
	{
		List<Map<String, String>> datas = new ArrayList<>();
		List<StudyClassLocation> studyClassLocations = studyClassLocationRepository.findAll();
		if(studyClassLocations != null && !studyClassLocations.isEmpty()) {
			for(StudyClassLocation studyClassLocation : studyClassLocations) {
				Map<String, String> map = new HashMap<>();
				map.put("id", studyClassLocation.getId());
				map.put("name", studyClassLocation.getLocationName());
				datas.add(map);
			}
		}
		return datas;
	}
}