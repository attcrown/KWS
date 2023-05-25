package com.otc.kws.console.gateway.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test")
public class TestController 
{
	@GetMapping
	public String handle()
	{
		System.out.println("### TestController.handle ###");
		return "test";
	}
}