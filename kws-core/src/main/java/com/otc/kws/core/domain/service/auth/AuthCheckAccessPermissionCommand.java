package com.otc.kws.core.domain.service.auth;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.otc.kws.core.domain.constant.value.MasterStatusValue;
import com.otc.kws.core.domain.constant.value.AuthPermissionValue;
import com.otc.kws.core.domain.constant.value.ResourceTypeValue;
import com.otc.kws.core.domain.entity.AuthPolicy;
import com.otc.kws.core.domain.entity.AuthPrivilege;
import com.otc.kws.core.domain.model.UserProfile;
import com.otc.kws.core.domain.service.BaseKwsCommand;
import com.otc.kws.core.domain.service.BaseKwsCommandRequest;
import com.otc.kws.core.domain.service.BaseKwsCommandResponse;

import lombok.Data;

@Component
public class AuthCheckAccessPermissionCommand extends BaseKwsCommand
{
	@Autowired
	protected AuthPolicyService authPolicyService;
	
	@Autowired
	protected AuthPrivilegeService authPrivilegeService;
	
	
	public Response checkAccessPermission(Request request)
	{
		Response response = new Response();
		
		List<AuthPrivilege> authPrivileges = authPrivilegeService.getGrantedAccessByPrincipalRefIdsAndResourceRefId(getPrincipalRefIds(request), request.getResourceRefId());
		
		if(authPrivileges != null && !authPrivileges.isEmpty()) {
			for(AuthPrivilege authPrivilege : authPrivileges) {
				AuthPolicy authPolicy = authPolicyService.getById(authPrivilege.getAuthPolicyId());
				if(authPolicy != null && authPolicy.getStatus() == MasterStatusValue.Active) {
					response.setPermission(AuthPermissionValue.Granted);
					break;
				}
			}
		}
		
		return response;
	}
	
	
	protected List<String> getPrincipalRefIds(Request request)
	{
		if(request.getUserProfile() != null) {
			List<String> principalRefIds = new ArrayList<>();
			if(request.getUserProfile().getUser() != null) {
				principalRefIds.add(request.getUserProfile().getUser().getId());
			}
			if(request.getUserProfile().getRoles() != null && !request.getUserProfile().getRoles().isEmpty()) {
				for(UserProfile.Role role : request.getUserProfile().getRoles()) {
					principalRefIds.add(role.getId());
				}
			}
			return principalRefIds;
		}
		
		return null;
	}
	
	
	@Data
	public static class Request extends BaseKwsCommandRequest
	{
		protected UserProfile userProfile;
		protected ResourceTypeValue resourceType;
		protected String resourceRefId;
	}
	
	
	@Data
	public static class Response extends BaseKwsCommandResponse
	{
		protected AuthPermissionValue permission = AuthPermissionValue.Denied;
	}
}