package com.otc.kws.core.domain.service.auth;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.otc.kws.core.domain.constant.value.MasterStatusValue;
import com.otc.kws.core.domain.entity.AuthPolicy;
import com.otc.kws.core.domain.entity.AuthPrivilege;
import com.otc.kws.core.domain.model.GrantedAuthority;
import com.otc.kws.core.domain.model.UserProfile;
import com.otc.kws.core.domain.service.BaseKwsCommand;
import com.otc.kws.core.domain.service.BaseKwsCommandRequest;
import com.otc.kws.core.domain.service.BaseKwsCommandResponse;

import lombok.Data;

@Component
public class FetchGrantedAuthorityCommand extends BaseKwsCommand
{
	@Autowired
	protected AuthPolicyService authPolicyService;
	
	@Autowired
	protected AuthPrivilegeService authPrivilegeService;
	
	
	public Response fetchGrantedAuthority(Request request)
	{
		GrantedAuthority grantedAuthority = new GrantedAuthority();
		grantedAuthority.setPrivileges(new ArrayList<>());
		
		List<AuthPrivilege> grantedPrivileges = null;
		
		if(request.getResourceRefId() != null && !request.getResourceRefId().isEmpty()) {
			List<String> principalRefIds = getPrincipalRefIds(request);
			if(principalRefIds != null && !principalRefIds.isEmpty()) {
				grantedPrivileges = authPrivilegeService.getGrantedByPrincipalRefIdsAndResourceRefId(getPrincipalRefIds(request), request.getResourceRefId());
			}
		} else {
			List<String> principalRefIds = getPrincipalRefIds(request);
			if(principalRefIds != null && !principalRefIds.isEmpty()) {
				grantedPrivileges = authPrivilegeService.getGrantedByPrincipalRefIds(getPrincipalRefIds(request));
			}
		}
		
		if(grantedPrivileges != null && !grantedPrivileges.isEmpty()) {
			grantedPrivileges = grantedPrivileges.
					stream().
					filter(e -> {
						AuthPolicy authPolicy = authPolicyService.getById(e.getAuthPolicyId());
						return authPolicy != null && authPolicy.getStatus() == MasterStatusValue.Active;
					}).
					collect(Collectors.toList());
		}
		
		if(grantedPrivileges != null && !grantedPrivileges.isEmpty()) {
			List<String> resourceRefIds = grantedPrivileges.
					stream().
					map(AuthPrivilege::getResourceRefId).
					distinct().
					collect(Collectors.toList());
			
			if(resourceRefIds != null && !resourceRefIds.isEmpty()) {
				for(String resourceRefId : resourceRefIds) {
					List<AuthPrivilege> filterAuthPrivileges = grantedPrivileges.
							stream().
							filter(e -> e.getResourceRefId().equals(resourceRefId)).
							distinct().
							collect(Collectors.toList());
					
					if(filterAuthPrivileges != null && !filterAuthPrivileges.isEmpty()) {
						GrantedAuthority.Privilege privilege = new GrantedAuthority.Privilege();
						privilege.setResourceType(filterAuthPrivileges.get(0).getResourceType());
						privilege.setResourceRefId(filterAuthPrivileges.get(0).getResourceRefId());
						privilege.setResourceActions(filterAuthPrivileges.stream().map(AuthPrivilege::getResourceAction).distinct().collect(Collectors.toList()));
						
						grantedAuthority.getPrivileges().add(privilege);
					}
				}
			}
		}
		
		Response response = new Response();
		response.setGrantedAuthority(grantedAuthority);
		
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
		protected String resourceRefId;
	}
	
	
	@Data
	public static class Response extends BaseKwsCommandResponse
	{
		protected GrantedAuthority grantedAuthority;
	}
}