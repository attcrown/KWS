package com.otc.kws.firstjobber.lib.gateway.web.controller.workspace.internship;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.otc.kws.firstjobber.lib.gateway.web.controller.workspace.KwsFirstJobberWorkspaceController;

@Controller
@RequestMapping(KwsFirstJobberWorkspaceController.PREFIX_PATH + "/internship/dashboard")
public class FirstJobberInternshipDashboardController extends KwsFirstJobberWorkspaceController
{
	@Override
	protected void setupModel(HttpServletRequest request, HttpServletResponse response, Model model) 
	{
		
	}
}