package com.otc.kws.console.gateway.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.otc.kws.core.domain.service.auth.AuthSessionService;

@Controller
@RequestMapping(KwsConsoleBaseController.PREFIX_PATH + "/logout")
public class KwsConsoleLogoutController extends KwsConsoleBaseController
{
	@Autowired
	protected AuthSessionService authSessionService;
	
	
	@GetMapping
	public String handle(HttpServletRequest request, Model model)
	{
		logger.info("### " + getClass().getSimpleName() + ".handle ###");
		
		String sessionId = request.getSession().getId();
		logger.info("logout and clear session ["+sessionId+"]");
		authSessionService.removeById(getAuthSession(request).getId());
		
		request.getSession().invalidate();
		
		return "redirect:" + KwsConsoleBaseController.PREFIX_PATH + "/login";
	}
}