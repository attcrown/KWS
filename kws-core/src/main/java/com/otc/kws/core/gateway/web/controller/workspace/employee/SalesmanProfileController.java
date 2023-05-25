package com.otc.kws.core.gateway.web.controller.workspace.employee;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.otc.kws.core.domain.repository.jpa.JpaEmployeeStatusRepository;
import com.otc.kws.core.domain.repository.jpa.JpaNameTitleRepository;
import com.otc.kws.core.domain.repository.jpa.JpaSalesmanRepository;
import com.otc.kws.core.domain.service.GenderService;
import com.otc.kws.core.gateway.web.controller.workspace.KwsCoreWorkspaceController;

@Controller
@RequestMapping(KwsCoreWorkspaceController.PREFIX_PATH + "/employee/salesman")
public class SalesmanProfileController extends KwsCoreWorkspaceController
{
	@Autowired
	protected GenderService genderService;
	
	@Autowired
	protected JpaNameTitleRepository nameTitleRepository;
	
	@Autowired
	protected JpaEmployeeStatusRepository employeeStatusRepository;
	
	@Autowired
	protected JpaSalesmanRepository salesmanRepository;
	
	
	@Override
	protected void setupModel(HttpServletRequest request, HttpServletResponse response, Model model) 
	{
		model.addAttribute("genders", genderService.getAllActive());
		model.addAttribute("nameTitles", nameTitleRepository.findAllActive());
		model.addAttribute("employeeStatuses", employeeStatusRepository.findAllActive());
		model.addAttribute("salesmans", salesmanRepository.findAllActive());
	}
}