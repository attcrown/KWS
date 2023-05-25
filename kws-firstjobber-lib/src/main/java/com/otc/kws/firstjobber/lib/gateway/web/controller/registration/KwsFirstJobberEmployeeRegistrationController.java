package com.otc.kws.firstjobber.lib.gateway.web.controller.registration;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.otc.kws.firstjobber.lib.gateway.web.controller.KwsFirstJobberBaseController;

@Controller
@RequestMapping(KwsFirstJobberBaseController.PREFIX_PATH + "/registration/employee")
public class KwsFirstJobberEmployeeRegistrationController extends KwsFirstJobberBaseController
{
	@GetMapping
	public String handle(HttpServletRequest request)
	{
		logger.info("### KwsFirstJobberInternshipEmployeeController.handle ###");
		return getCurrentWeb(request).getViewFile().replace("${web_template}", getCurrentWebTemplate(request));
	}
}