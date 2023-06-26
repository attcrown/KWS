package com.otc.kws.core.gateway.web.controller.day_switch_off_user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.otc.kws.core.gateway.web.controller.workspace.KwsCoreWorkspaceController;
@Controller
@RequestMapping(KwsCoreWorkspaceController.PREFIX_PATH + "/ocp_user/day_switch_off")

public class day_switch_off_userController extends KwsCoreWorkspaceController{
	protected void setupModel(HttpServletRequest request, HttpServletResponse response, Model model) {
		// TODO Auto-generated method stub
		
	}
}