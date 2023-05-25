package com.otc.kws.core.gateway.web.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.otc.kws.core.domain.service.auth.AuthSessionService;

@RestController
@RequestMapping(BaseKwsAPI.PREFIX_PATH + "/logout")
public class LogoutAPI extends BaseKwsAPI
{
	@Autowired
	protected AuthSessionService authSessionService;
}