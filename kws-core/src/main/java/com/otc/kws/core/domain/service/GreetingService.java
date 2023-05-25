package com.otc.kws.core.domain.service;

import org.springframework.stereotype.Service;

@Service
public class GreetingService 
{
	public String sayHello(String name)
	{
		return "Hello " + name;
	}
	
	public String sayHi(String name)
	{
		return "Hi " + name;
	}
}