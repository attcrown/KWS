package com.otc.kws.core.gateway.web.controller.negotiate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.otc.kws.core.gateway.web.controller.workspace.KwsCoreWorkspaceController;
@Controller
@RequestMapping(KwsCoreWorkspaceController.PREFIX_PATH + "/hr/negotiate")
public class NegotiateController extends KwsCoreWorkspaceController{

	@Override
	protected void setupModel(HttpServletRequest request, HttpServletResponse response, Model model) {
		// TODO Auto-generated method stub
		
	}

}
