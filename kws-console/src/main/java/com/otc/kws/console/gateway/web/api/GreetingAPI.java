package com.otc.kws.console.gateway.web.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.otc.kws.core.domain.service.GreetingService;

@RestController
@RequestMapping("/api/greeting")
public class GreetingAPI 
{
	@Autowired
	protected GreetingService greetingService;
	
	
	@GetMapping("/hello")
	public String sayHello(@RequestParam String name)
	{
		return greetingService.sayHello(name);
	}
	
	
	@GetMapping("/hi")
	public String sayHi(@RequestParam String name)
	{
		return greetingService.sayHi(name);
	}
}