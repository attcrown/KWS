package com.otc.kws.firstjobber.lib.domain.service.employer;

import org.springframework.beans.factory.annotation.Autowired;

import com.otc.kws.core.domain.service.BaseKwsCommand;
import com.otc.kws.core.domain.service.BaseKwsCommandRequest;
import com.otc.kws.core.domain.service.user.UserAccountService;
import com.otc.kws.core.domain.service.user.UserPersonalInfoService;
import com.otc.kws.core.domain.service.user.UserRoleMapperService;
import com.otc.kws.firstjobber.lib.domain.profile.FjbEmployerProfile;
import com.otc.kws.firstjobber.lib.domain.service.FjbAddressService;

import lombok.Data;

public abstract class FjbEmployerBuildProfileCommand extends BaseKwsCommand
{
	@Autowired
	protected FjbEmployerService fjbEmployerService;
	
	@Autowired
	protected FjbEmployerUserService fjbEmployerUserService;
	
	@Autowired
	protected FjbAddressService fjbAddressService;
	
	@Autowired
	protected UserAccountService userAccountService;
	
	@Autowired
	protected UserPersonalInfoService userPersonalInfoService;
	
	@Autowired
	protected UserRoleMapperService userRoleMapperService;
	
	
	public abstract Response buildEmployerProfile(Request request);
	
	
	@Data
	public static class Request extends BaseKwsCommandRequest
	{
		protected String employerId;
	}
	
	
	@Data
	public static class Response extends BaseKwsCommandRequest
	{
		protected FjbEmployerProfile employerProfile;
	}
}