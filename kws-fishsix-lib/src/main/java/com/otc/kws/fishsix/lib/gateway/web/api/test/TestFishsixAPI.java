package com.otc.kws.fishsix.lib.gateway.web.api.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.otc.kws.core.gateway.web.api.BaseKwsAPI;

@RestController
@RequestMapping("/api/fishsix/test")
public class TestFishsixAPI extends BaseKwsAPI
{
	@GetMapping("/echo")
	public String echo()
	{
		return "hi";
	}
}