package com.otc.kws.console.gateway.web.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.otc.kws.core.gateway.web.api.BaseKwsAPI;

@RestController
@RequestMapping("/api/test")
public class TestAPI extends BaseKwsAPI
{
	@GetMapping
	public void test(@RequestParam String data)
	{
		System.out.println("### TestAPI.test ###");
		System.out.println("data => " + data);
	}
}