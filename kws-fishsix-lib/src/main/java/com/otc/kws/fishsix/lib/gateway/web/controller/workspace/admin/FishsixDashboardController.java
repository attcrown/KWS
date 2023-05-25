package com.otc.kws.fishsix.lib.gateway.web.controller.workspace.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.otc.kws.fishsix.lib.gateway.web.controller.workspace.KwsFishsixWorkspaceController;

@Controller
@RequestMapping(KwsFishsixWorkspaceController.PREFIX_PATH + "/admin/dashboard")
public class FishsixDashboardController extends KwsFishsixWorkspaceController
{
	@Override
	protected void setupModel(HttpServletRequest request, HttpServletResponse response, Model model) 
	{
		
	}
}