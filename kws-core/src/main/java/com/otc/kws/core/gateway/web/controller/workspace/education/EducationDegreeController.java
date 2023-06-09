package com.otc.kws.core.gateway.web.controller.workspace.education;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.otc.kws.core.gateway.web.controller.workspace.KwsCoreWorkspaceController;

@Controller
@RequestMapping(KwsCoreWorkspaceController.PREFIX_PATH + "/education/education-degree")
public class EducationDegreeController extends KwsCoreWorkspaceController
{
	@Override
	protected void setupModel(HttpServletRequest request, HttpServletResponse response, Model model) 
	{
		
	}
}