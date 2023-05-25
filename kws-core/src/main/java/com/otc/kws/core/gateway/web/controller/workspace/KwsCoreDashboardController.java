package com.otc.kws.core.gateway.web.controller.workspace;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(KwsCoreWorkspaceController.PREFIX_PATH + "/dashboard")
public class KwsCoreDashboardController extends KwsCoreWorkspaceController
{
	@Override
	protected void setupModel(HttpServletRequest request, HttpServletResponse response, Model model) 
	{
		
	}
}