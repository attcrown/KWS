package com.otc.kws.fishsix.lib.gateway.web.controller.workspace.admin.teacher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaEducationClassRepository;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaSubjectCategoryRepository;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaSubjectRepository;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaTeacherHireTypeRepository;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaTeacherWorktimeTypeRepository;
import com.otc.kws.fishsix.lib.gateway.web.controller.workspace.KwsFishsixWorkspaceController;

@Controller("com.otc.kws.fishsix.lib.gateway.web.controller.workspace.admin.teacher.FishsixTeacherProfileController")
@RequestMapping(KwsFishsixWorkspaceController.PREFIX_PATH + "/admin/teacher/profile")
public class FishsixTeacherProfileController extends KwsFishsixWorkspaceController
{
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
		logger.debug("hireTypes => {}", hireTypeRepository.findAllActive());
		
		model.addAttribute("hireTypes", hireTypeRepository.findAllActive());
		model.addAttribute("worktimeTypes", worktimeTypeRepository.findAllActive());
		model.addAttribute("educationClasses", educationClassRepository.findAllActive());
		model.addAttribute("subjectCategories", subjectCategoryRepository.findAllActive());
		model.addAttribute("subjects", subjectRepository.findAllActive());
	}
}