package com.otc.kws.firstjobber.lib.gateway.web.controller.registration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.otc.kws.core.domain.entity.Amphur;
import com.otc.kws.core.domain.entity.EducationDegree;
import com.otc.kws.core.domain.entity.EducationFaculty;
import com.otc.kws.core.domain.entity.EducationMajor;
import com.otc.kws.core.domain.entity.Province;
import com.otc.kws.core.domain.entity.School;
import com.otc.kws.core.domain.service.AmphurService;
import com.otc.kws.core.domain.service.ConfigService;
import com.otc.kws.core.domain.service.GenderService;
import com.otc.kws.core.domain.service.ProvinceService;
import com.otc.kws.core.domain.service.RelationshipService;
import com.otc.kws.core.domain.service.education.EducationDegreeService;
import com.otc.kws.core.domain.service.education.EducationFacultyService;
import com.otc.kws.core.domain.service.education.EducationLevelService;
import com.otc.kws.core.domain.service.education.EducationMajorService;
import com.otc.kws.core.domain.service.education.EducationStatusService;
import com.otc.kws.core.domain.service.education.SchoolService;
import com.otc.kws.core.domain.service.education.SchoolTypeService;
import com.otc.kws.firstjobber.lib.domain.entity.Job;
import com.otc.kws.firstjobber.lib.domain.service.InternshipTypeService;
import com.otc.kws.firstjobber.lib.domain.service.JobService;
import com.otc.kws.firstjobber.lib.gateway.web.controller.KwsFirstJobberBaseController;

@Controller
@RequestMapping(KwsFirstJobberBaseController.PREFIX_PATH + "/registration/internship")
public class KwsFirstJobberInternshipRegistrationController extends KwsFirstJobberBaseController
{
	@Autowired
	protected ConfigService configService;
	
	@Autowired
	protected GenderService genderService;
	
	@Autowired
	protected RelationshipService relationshipService;
	
	@Autowired
	protected SchoolTypeService schoolTypeService;
	
	@Autowired
	protected SchoolService schoolService;
	
	@Autowired
	protected ProvinceService provinceService;
	
	@Autowired
	protected AmphurService amphurService;
	
	@Autowired
	protected EducationStatusService educationStatusService;
	
	@Autowired
	protected EducationLevelService educationLevelService;
	
	@Autowired
	protected EducationDegreeService educationDegreeService;
	
	@Autowired
	protected EducationFacultyService educationFacultyService;
	
	@Autowired
	protected EducationMajorService educationMajorService;
	
	@Autowired
	protected InternshipTypeService internshipTypeService;
	
	@Autowired
	protected JobService jobService;
	
	
	@GetMapping
	public String handle(HttpServletRequest request, Model model) throws Exception
	{
		logger.info("### KwsFirstJobberInternshipRegistrationController.handle ###");
		
		CompletableFuture<List<School>> schoolsFuture = schoolService.fetchFlyweightAllActiveAsync();
		//CompletableFuture<List<Province>> provincesFuture = provinceService.findAllAsync();
		//CompletableFuture<List<Amphur>> amphursFuture = amphurService.findAllAsync();
		CompletableFuture<List<EducationDegree>> educationDegreesFuture = educationDegreeService.fetchFlyweightAllActiveAsync();
		CompletableFuture<List<EducationFaculty>> educationFacultiesFuture = educationFacultyService.fetchFlyweightAllActiveAsync();
		CompletableFuture<List<EducationMajor>> educationMajorsFuture = educationMajorService.fetchFlyweightAllActiveAsync();
		CompletableFuture<List<Job>> jobsFuture = jobService.findAllActiveAsync();
	    
	    CompletableFuture.allOf(
	    		schoolsFuture, 
	    		//provincesFuture, 
	    		//amphursFuture, 
	    		educationDegreesFuture, 
	    		educationFacultiesFuture, 
	    		educationMajorsFuture, 
	    		jobsFuture
    		).join();
	    
	    Map<String, Object> data = new HashMap<>();
	    data.put("genders", genderService.getAllActive());
	    data.put("relationships", relationshipService.getAllActive());
	    data.put("schoolTypes", schoolTypeService.getAllActive());
	    data.put("schools", schoolsFuture.get());
	    //data.put("provinces", provincesFuture.get());
	    //data.put("amphurs", amphursFuture.get());
	    data.put("educationLevels", educationLevelService.getByIds(configService.getFirstJobberInternshipRegistrationEducationLevels()));
	    data.put("educationStatuses", educationStatusService.getAllActive());
	    data.put("educationDegrees", educationDegreesFuture.get());
	    data.put("educationFaculties", educationFacultiesFuture.get());
	    data.put("educationMajors", educationMajorsFuture.get());
	    data.put("internshipTypes", internshipTypeService.getAllActive());
	    data.put("jobs", jobsFuture.get());
	    
	    model.addAttribute("kws_webEnv", getWebEnv(request));
	    model.addAttribute("kws_serverURL", getServerURL(request));
	    model.addAttribute("kws_data", data);
		
		return getCurrentWeb(request).getViewFile().replace("${web_template}", getCurrentWebTemplate(request));
	}
}