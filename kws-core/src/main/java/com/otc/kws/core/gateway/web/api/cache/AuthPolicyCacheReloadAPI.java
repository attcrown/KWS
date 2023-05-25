package com.otc.kws.core.gateway.web.api.cache;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.otc.kws.core.domain.entity.AuthPolicy;
import com.otc.kws.core.domain.service.auth.AuthPolicyService;
import com.otc.kws.core.gateway.web.api.BaseKwsAPI;
import com.otc.kws.core.gateway.web.api.CacheReloadKwsAPI;

@RestController
@RequestMapping(BaseKwsAPI.PREFIX_PATH + "/auth-policy")
public class AuthPolicyCacheReloadAPI extends CacheReloadKwsAPI<AuthPolicy>
{
	@Autowired
	protected AuthPolicyService authPolicyService;

	@Override
	protected List<AuthPolicy> reloadCache() 
	{
		authPolicyService.reloadCache();
		return authPolicyService.getAll();
	}
}