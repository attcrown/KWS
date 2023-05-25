package com.otc.kws.core.gateway.web.controller.workspace.education;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.otc.kws.core.domain.service.education.SchoolOwnerTypeService;
import com.otc.kws.core.domain.service.education.SchoolTypeService;
import com.otc.kws.core.gateway.web.controller.workspace.KwsCoreWorkspaceController;

@Controller
@RequestMapping(KwsCoreWorkspaceController.PREFIX_PATH + "/education/school")
public class SchoolController extends KwsCoreWorkspaceController
{
	@Autowired
	protected SchoolTypeService schoolTypeService;
	
	@Autowired
	protected SchoolOwnerTypeService schoolOwnerTypeService;
	
	
	@Override
	protected void setupModel(HttpServletRequest request, HttpServletResponse response, Model model) 
	{
		model.addAttribute("schoolTypes", schoolTypeService.getAllActive());
		model.addAttribute("schoolOwnerTypes", schoolOwnerTypeService.getAllActive());
	}
}