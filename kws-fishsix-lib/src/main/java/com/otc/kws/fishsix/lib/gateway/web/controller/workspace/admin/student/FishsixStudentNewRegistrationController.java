package com.otc.kws.fishsix.lib.gateway.web.controller.workspace.admin.student;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.otc.kws.fishsix.lib.domain.entity.StudentRegister;
import com.otc.kws.fishsix.lib.domain.service.student_register.FishsixStudentRegisterService;
import com.otc.kws.fishsix.lib.gateway.web.controller.workspace.KwsFishsixWorkspaceController;

@Controller("com.otc.kws.fishsix.lib.gateway.web.controller.workspace.admin.student.FishsixStudentNewRegistrationController")
@RequestMapping(KwsFishsixWorkspaceController.PREFIX_PATH + "/admin/student/new-registration")
public class FishsixStudentNewRegistrationController extends KwsFishsixWorkspaceController
{
	@Autowired
	protected FishsixStudentRegisterService studentRegisterService;
	
	
	@Override
	protected void setupModel(HttpServletRequest request, HttpServletResponse response, Model model) 
	{
		List<StudentRegister> studentRegisters = studentRegisterService.search(req -> {
			setupServiceRequest(request, req);
		}).getStudentRegisters();
		
		model.addAttribute("studentRegisters", studentRegisters);
	}
}