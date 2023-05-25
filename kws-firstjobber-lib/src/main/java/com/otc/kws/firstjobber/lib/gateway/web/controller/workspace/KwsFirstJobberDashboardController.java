package com.otc.kws.firstjobber.lib.gateway.web.controller.workspace;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(KwsFirstJobberWorkspaceController.PREFIX_PATH + "/dashboard")
public class KwsFirstJobberDashboardController extends KwsFirstJobberWorkspaceController
{
	@Override
	protected void setupModel(HttpServletRequest request, HttpServletResponse response, Model model) 
	{
		
	}
}