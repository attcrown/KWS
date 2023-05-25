package com.otc.kws.core.domain.service.auth;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.otc.kws.core.domain.constant.value.AuthPermissionValue;
import com.otc.kws.core.domain.constant.value.ResourceActionValue;
import com.otc.kws.core.domain.constant.value.ResourceTypeValue;
import com.otc.kws.core.domain.entity.AuthPrivilege;
import com.otc.kws.core.domain.repository.jpa.JpaAuthPrivilegeRepository;
import com.otc.kws.core.domain.service.CacheKwsService;

@Service
public class AuthPrivilegeService extends CacheKwsService<AuthPrivilege, String>
{
	@Autowired
	protected JpaAuthPrivilegeRepository authPrivilegeRepository;

	@Override
	public List<AuthPrivilege> loadDatas() 
	{
		return authPrivilegeRepository.findAll();
	}

	@Override
	protected String extractId(AuthPrivilege entity) 
	{
		return entity.getId();
	}
	
	public List<AuthPrivilege> getByApiId(String apiId)
	{
		return getByResourceTypeAndResourceRefId(ResourceTypeValue.api, apiId);
	}
	
	public List<AuthPrivilege> getByWebId(String webId)
	{
		return getByResourceTypeAndResourceRefId(ResourceTypeValue.web, webId);
	}
	
	public List<AuthPrivilege> getByResourceTypeAndResourceRefId(ResourceTypeValue resourceType, String resourceRefId)
	{
		return getAll().
				stream().
				filter(e -> e.getResourceType() == resourceType).
				filter(e -> e.getResourceRefId().equals(resourceRefId)).
				collect(Collectors.toList());
	}
	
	public List<AuthPrivilege> getGrantedByResourceTypeAndResourceRefId(ResourceTypeValue resourceType, String resourceRefId)
	{
		return getAll().
				stream().
				filter(e -> e.getResourceType() == resourceType).
				filter(e -> e.getResourceRefId().equals(resourceRefId)).
				filter(e -> e.getPermission() == AuthPermissionValue.Granted).
				collect(Collectors.toList());
	}
	
	public List<AuthPrivilege> getByResourceTypeAndResourceRefIdAndResourceAction(ResourceTypeValue resourceType, String resourceRefId, String resourceAction)
	{
		return getAll().
				stream().
				filter(e -> e.getResourceType() == resourceType).
				filter(e -> e.getResourceRefId().equals(resourceRefId)).
				filter(e -> e.getResourceAction().equals(resourceAction)).
				collect(Collectors.toList());
	}
	
	public List<AuthPrivilege> getGrantedByResourceTypeAndResourceRefIdAndResourceAction(ResourceTypeValue resourceType, String resourceRefId, String resourceAction)
	{
		return getAll().
				stream().
				filter(e -> e.getResourceType() == resourceType).
				filter(e -> e.getResourceRefId().equals(resourceRefId)).
				filter(e -> e.getResourceAction().equals(resourceAction)).
				filter(e -> e.getPermission() == AuthPermissionValue.Granted).
				collect(Collectors.toList());
	}
	
	public List<AuthPrivilege> getGrantedByPrincipalRefIds(List<String> principalRefIds)
	{
		return getAll().
				stream().
				filter(e -> principalRefIds.contains(e.getPrincipalRefId())).
				filter(e -> e.getPermission() == AuthPermissionValue.Granted).
				collect(Collectors.toList());
	}
	
	public List<AuthPrivilege> getGrantedByPrincipalRefIdsAndResourceRefId(List<String> principalRefIds, String resourceRefId)
	{
		return getAll().
				stream().
				filter(e -> principalRefIds.contains(e.getPrincipalRefId())).
				filter(e -> e.getResourceRefId().equals(resourceRefId)).
				filter(e -> e.getPermission() == AuthPermissionValue.Granted).
				collect(Collectors.toList());
	}
	
	public List<AuthPrivilege> getGrantedAccessByPrincipalRefIdsAndResourceRefId(List<String> principalRefIds, String resourceRefId)
	{
		return getAll().
				stream().
				filter(e -> principalRefIds.contains(e.getPrincipalRefId())).
				filter(e -> e.getResourceRefId().equals(resourceRefId)).
				filter(e -> e.getResourceAction().equals(ResourceActionValue.Access.name())).
				filter(e -> e.getPermission() == AuthPermissionValue.Granted).
				collect(Collectors.toList());
	}
}