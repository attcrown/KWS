package com.otc.kws.core.domain.service.user;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.otc.kws.core.domain.entity.UserAccount;
import com.otc.kws.core.domain.repository.jpa.JpaUserAccountRepository;
import com.otc.kws.core.domain.service.RepositoryKwsService;

@Service
public class UserAccountService extends RepositoryKwsService<JpaUserAccountRepository, UserAccount, String>
{
	@Autowired
	protected JpaUserAccountRepository userAccountRepository;

	
	@Override
	protected JpaUserAccountRepository getRepository() 
	{
		return userAccountRepository;
	}

	@Override
	protected String getId(UserAccount entity) 
	{
		return entity.getId();
	}
	
	
	public UserAccount findByUsername(String username)
	{
		return userAccountRepository.findByUsername(username);
	}
	
	@Async
	public CompletableFuture<UserAccount> findByUsernameAsync(String username)
	{
		return CompletableFuture.completedFuture(findByUsername(username));
	}
	
	
	public UserAccount findByEmail(String email)
	{
		return userAccountRepository.findByEmail(email);
	}
	
	@Async
	public CompletableFuture<UserAccount> findByEmailAsync(String email)
	{
		return CompletableFuture.completedFuture(findByEmail(email));
	}
}