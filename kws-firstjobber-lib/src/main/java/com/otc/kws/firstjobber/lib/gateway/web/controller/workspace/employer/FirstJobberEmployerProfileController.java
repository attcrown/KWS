package com.otc.kws.firstjobber.lib.gateway.web.controller.workspace.employer;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.otc.kws.core.domain.service.GenderService;
import com.otc.kws.core.domain.service.MasterStatusService;
import com.otc.kws.core.domain.service.ProvinceService;
import com.otc.kws.firstjobber.lib.domain.service.employer.FjbEmployerTypeService;
import com.otc.kws.firstjobber.lib.gateway.web.controller.workspace.KwsFirstJobberWorkspaceController;

@Controller
@RequestMapping(KwsFirstJobberWorkspaceController.PREFIX_PATH + "/employer/profile")
public class FirstJobberEmployerProfileController extends KwsFirstJobberWorkspaceController
{
	@Autowired
	protected ProvinceService provinceService;
	
	@Autowired
	protected FjbEmployerTypeService fjbEmployerTypeService;
	
	@Autowired
	protected GenderService genderService;
	
	@Autowired
	protected MasterStatusService masterStatusService;
	
	
	@Override
	protected void setupModel(HttpServletRequest request, HttpServletResponse response, Model model) 
	{
		logger.info("### " + getClass().getSimpleName() + ".setupModel ###");
		
		Map<String, Object> data = new HashMap<>();
		data.put("employerTypes", fjbEmployerTypeService.getAll());
		data.put("genders", genderService.getAllActive());
		data.put("masterStatuses", masterStatusService.getAllActive());
		data.put("provinces", provinceService.findAll());

		model.addAttribute("kws_data", data);
	}
}