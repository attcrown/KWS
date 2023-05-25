package com.otc.kws.fishsix.lib.gateway.web.controller.registration;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.otc.kws.fishsix.lib.gateway.web.controller.KwsFishsixBaseController;

@Controller
@RequestMapping(KwsFishsixBaseController.PREFIX_PATH + "/registration/student")
public class KwsFishsixStudentRegistrationController extends KwsFishsixBaseController
{
	@GetMapping
	public String handle(HttpServletRequest request, Model model)
	{
		logger.info("### {}.handle ###", getClass().getSimpleName());
		
		Map<String, Object> data = new HashMap<>();
		
		model.addAttribute("kws_webEnv", getWebEnv(request));
	    model.addAttribute("kws_serverURL", getServerURL(request));
	    model.addAttribute("kws_data", data);
		
		return getCurrentWeb(request).getViewFile().replace("${web_template}", getCurrentWebTemplate(request));
	}
}