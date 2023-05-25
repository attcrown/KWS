package com.otc.kws.core.domain.service.auth;

import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.otc.kws.core.domain.service.BaseKwsService;

@Service
public class AuthService extends BaseKwsService
{
	@Autowired
	protected AuthCheckAccessPermissionCommand authCheckAccessPermissionCommand;
	
	@Autowired
	protected FetchGrantedAuthorityCommand fetchGrantedAuthorityCommand;
	
	
	// ****************************** authCheckAccessPermission ****************************** //
	public AuthCheckAccessPermissionCommand.Response checkAccessPermission(Consumer<AuthCheckAccessPermissionCommand.Request> consumer)
	{
		AuthCheckAccessPermissionCommand.Request request = new AuthCheckAccessPermissionCommand.Request();
		consumer.accept(request);
		return checkAccessPermission(request);
	}
	
	public AuthCheckAccessPermissionCommand.Response checkAccessPermission(AuthCheckAccessPermissionCommand.Request request)
	{
		return authCheckAccessPermissionCommand.checkAccessPermission(request);
	}
	// ****************************** authCheckAccessPermission ****************************** //
	
	
	// ****************************** fetchGrantedAuthority ****************************** //
	public FetchGrantedAuthorityCommand.Response fetchGrantedAuthority(Consumer<FetchGrantedAuthorityCommand.Request> consumer)
	{
		FetchGrantedAuthorityCommand.Request request = new FetchGrantedAuthorityCommand.Request();
		consumer.accept(request);
		return fetchGrantedAuthority(request);
	}
	
	public FetchGrantedAuthorityCommand.Response fetchGrantedAuthority(FetchGrantedAuthorityCommand.Request request)
	{
		return fetchGrantedAuthorityCommand.fetchGrantedAuthority(request);
	}
	// ****************************** fetchGrantedAuthority ****************************** //
}