package com.otc.kws.core.gateway.web.controller.day_work_admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.otc.kws.core.gateway.web.controller.workspace.KwsCoreWorkspaceController;
@Controller
@RequestMapping(KwsCoreWorkspaceController.PREFIX_PATH + "/ocp_admin/day_work")

public class day_work_adminController extends KwsCoreWorkspaceController{
	protected void setupModel(HttpServletRequest request, HttpServletResponse response, Model model) {
		// TODO Auto-generated method stub
		
	}
}
