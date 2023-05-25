package com.otc.kws.fishsix.lib.gateway.web.controller.workspace.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.otc.kws.core.domain.repository.jpa.JpaEducationClassLevelRepository;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaEducationClassRepository;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaSubjectCategoryRepository;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaSubjectRepository;
import com.otc.kws.fishsix.lib.domain.service.subject.FishsixSubjectService;
import com.otc.kws.fishsix.lib.gateway.web.controller.workspace.KwsFishsixWorkspaceController;

@Controller
@RequestMapping(KwsFishsixWorkspaceController.PREFIX_PATH + "/admin/subject")
public class FishsixSubjectController extends KwsFishsixWorkspaceController
{
	@Autowired
	protected JpaEducationClassLevelRepository educationClassLevelRepository;
	
	@Autowired
	protected JpaEducationClassRepository educationClassRepository;
	
	@Autowired
	protected JpaSubjectCategoryRepository subjectCategoryRepository;
	
	@Autowired
	protected FishsixSubjectService fishsixSubjectService;
	
	@Autowired
	protected JpaSubjectRepository subjectRepository;
	
	
	@Override
	protected void setupModel(HttpServletRequest request, HttpServletResponse response, Model model) 
	{
		model.addAttribute("classLevels", educationClassLevelRepository.findAllActive());
		model.addAttribute("educationClasses", educationClassRepository.findAllActive());
		model.addAttribute("subjectCategories", subjectCategoryRepository.findAllActive());
		model.addAttribute("subjectProfiles", fishsixSubjectService.fetchSubject(req -> {
			setupServiceRequest(request, req);
			req.setSubjects(subjectRepository.findAllActive());
		}).getSubjectProfiles());
	}
}