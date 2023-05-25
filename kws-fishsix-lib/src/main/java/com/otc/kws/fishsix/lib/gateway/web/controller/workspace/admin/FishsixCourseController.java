package com.otc.kws.fishsix.lib.gateway.web.controller.workspace.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.otc.kws.core.domain.repository.jpa.JpaEducationClassLevelRepository;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaEducationClassRepository;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaStudyClassChannelRepository;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaStudyClassTypeRepository;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaSubjectCategoryRepository;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaSubjectRepository;
import com.otc.kws.fishsix.lib.gateway.web.controller.workspace.KwsFishsixWorkspaceController;

@Controller
@RequestMapping(KwsFishsixWorkspaceController.PREFIX_PATH + "/admin/course")
public class FishsixCourseController extends KwsFishsixWorkspaceController
{
	@Autowired
	protected JpaStudyClassChannelRepository classChannelRepository;
	
	@Autowired
	protected JpaStudyClassTypeRepository classTypeRepository;
	
	@Autowired
	protected JpaEducationClassLevelRepository educationClassLevelRepository;
	
	@Autowired
	protected JpaEducationClassRepository educationClassRepository;
	
	@Autowired
	protected JpaSubjectRepository subjectRepository;
	
	@Autowired
	protected JpaSubjectCategoryRepository subjectCategoryRepository;
	
	
	@Override
	protected void setupModel(HttpServletRequest request, HttpServletResponse response, Model model) 
	{
		model.addAttribute("classChannels", classChannelRepository.findAllActive());
		model.addAttribute("classTypes", classTypeRepository.findAllActive());
		model.addAttribute("classLevels", educationClassLevelRepository.findAllActive());
		model.addAttribute("educationClasses", educationClassRepository.findAllActive());
		model.addAttribute("subjectCategories", subjectCategoryRepository.findAllActive());
		model.addAttribute("subjects", subjectRepository.findAllActive());
	}
}