package com.otc.kws.fishsix.lib.gateway.web.controller.registration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaEducationClassRepository;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaStudyClassChannelRepository;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaStudyClassLocationRepository;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaSubjectCategoryRepository;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaSubjectRepository;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaTeacherHireTypeRepository;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaTeacherWorktimeTypeRepository;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaWageRateTypeRepository;
import com.otc.kws.fishsix.lib.gateway.web.controller.KwsFishsixBaseController;

@Controller
@RequestMapping(KwsFishsixBaseController.PREFIX_PATH + "/registration/teacher")
public class KwsFishsixTeacherRegistrationController extends KwsFishsixBaseController
{
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
	
	
	@GetMapping
	public String handle(HttpServletRequest request, Model model)
	{
		logger.info("### {}.handle ###", getClass().getSimpleName());
		
		Map<String, Object> data = new HashMap<>();
		data.put("genders", loadGenders(request));
		data.put("nameTitles", loadNameTitles(request));
		data.put("educationStatuses", loadEducationStatuses(request));
		data.put("educationLevels", loadEducationLevels(request));
		data.put("studyClassLocations", loadStudyClassLocations(request));
		data.put("wageRateTypes", wageRateTypeRepository.findAllActive());
		data.put("hireTypes", hireTypeRepository.findAllActive());
		data.put("worktimeTypes", worktimeTypeRepository.findAllActive());
		data.put("educationClasses", educationClassRepository.findAllActive());
		data.put("subjectCategories", subjectCategoryRepository.findAllActive());
		data.put("subjects", subjectRepository.findAllActive());
		
		model.addAttribute("kws_webEnv", getWebEnv(request));
	    model.addAttribute("kws_serverURL", getServerURL(request));
	    model.addAttribute("kws_data", data);
		
		return getCurrentWeb(request).getViewFile().replace("${web_template}", getCurrentWebTemplate(request));
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