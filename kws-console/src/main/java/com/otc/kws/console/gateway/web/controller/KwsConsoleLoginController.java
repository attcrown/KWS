package com.otc.kws.console.gateway.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(KwsConsoleBaseController.PREFIX_PATH + "/login")
public class KwsConsoleLoginController extends KwsConsoleBaseController
{
	@GetMapping
	public String handle(HttpServletRequest request, Model model)
	{
		logger.debug("### " + getClass().getSimpleName() + ".handle ###");
		
		String callbackURL = request.getParameter("callbackURL");
		if(callbackURL == null) {
			callbackURL = request.getContextPath() + "/views/workspace/home";
		}
		
		logger.debug("callbackURL => " + callbackURL);
		
		model.addAttribute("kws_webEnv", getWebEnv(request));
		model.addAttribute("kws_contextPath", request.getContextPath());
		model.addAttribute("kws_callbackURL", callbackURL);
		return getRuntimePrefixViewFilePath(request) + "/login";
	}
}