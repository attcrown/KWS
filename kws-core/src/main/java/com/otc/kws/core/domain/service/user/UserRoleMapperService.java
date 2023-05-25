package com.otc.kws.core.domain.service.user;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.otc.kws.core.domain.entity.UserRoleMapper;
import com.otc.kws.core.domain.repository.jpa.JpaUserRoleMapperRepository;
import com.otc.kws.core.domain.service.RepositoryKwsService;

@Service
public class UserRoleMapperService extends RepositoryKwsService<JpaUserRoleMapperRepository, UserRoleMapper, String>
{
	@Autowired
	protected JpaUserRoleMapperRepository userRoleMapperRepository;

	
	@Override
	protected JpaUserRoleMapperRepository getRepository() 
	{
		return userRoleMapperRepository;
	}

	@Override
	protected String getId(UserRoleMapper entity) 
	{
		return entity.getId();
	}
	
	
	public List<UserRoleMapper> findByUserId(String userId)
	{
		return userRoleMapperRepository.findByUserId(userId);
	}
	
	@Async
	public CompletableFuture<List<UserRoleMapper>> findByUserIdAsync(String userId)
	{
		return CompletableFuture.completedFuture(findByUserId(userId));
	}
	
	
	public List<UserRoleMapper> findByUserIds(Collection<String> userIds)
	{
		return userRoleMapperRepository.findByUserIds(userIds);
	}
	
	@Async
	public CompletableFuture<List<UserRoleMapper>> findByUserIdsAsync(Collection<String> userIds)
	{
		return CompletableFuture.completedFuture(findByUserIds(userIds));
	}
	
	
	public List<UserRoleMapper> findAllActivatedUserId(String userId)
	{
		return userRoleMapperRepository.findAllActivatedUserId(userId);
	}
	
	@Async
	public CompletableFuture<List<UserRoleMapper>> findAllActivatedUserIdAsync(String userId)
	{
		return CompletableFuture.completedFuture(findAllActivatedUserId(userId));
	}
}