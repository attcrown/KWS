package com.otc.kws.console.gateway.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(KwsConsoleBaseController.PREFIX_PATH + "/forgot-password")
public class ForgotPasswordController extends KwsConsoleBaseController
{
	@GetMapping
	public String handle(HttpServletRequest request, Model model)
	{
		logger.debug("### " + getClass().getSimpleName() + ".handle ###");
		model.addAttribute("kws_contextPath", request.getContextPath());
		return getRuntimePrefixViewFilePath(request) + "/forgot_password";
	}
}