package com.otc.kws.core.domain.repository.jpa;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.otc.kws.core.domain.constant.value.MasterStatusValue;
import com.otc.kws.core.domain.entity.UserRoleMapper;

@Repository
public interface JpaUserRoleMapperRepository extends KwsJpaRepository<UserRoleMapper, String>
{
	public List<UserRoleMapper> findByUserId(String userId);
	public List<UserRoleMapper> findByUserIdIn(Collection<String> userIds);
	public List<UserRoleMapper> findByRoleId(String roleId);
	public UserRoleMapper findByUserIdAndRoleId(String userId, String roleId);
	public List<UserRoleMapper> findByUserIdAndStatus(String userId, MasterStatusValue status);
	
	public default List<UserRoleMapper> findByUserIds(Collection<String> userIds)
	{
		return findByUserIdIn(userIds);
	}
	
	public default List<UserRoleMapper> findAllActivatedUserId(String userId)
	{
		Date now = new Date();
		List<UserRoleMapper> userRoleMappers = findByUserIdAndStatus(userId, MasterStatusValue.Active);
		
		if(userRoleMappers != null && !userRoleMappers.isEmpty()) {
			userRoleMappers = userRoleMappers.
					stream().
					filter(e -> e.getActivatedAt() == null || now.getTime() >= e.getActivatedAt().getTime()).
					filter(e -> e.getExpiredAt() == null || now.getTime() <= e.getExpiredAt().getTime()).
					collect(Collectors.toList());
		}
		
		return userRoleMappers;
	}
	
	public List<UserRoleMapper> removeByUserId(String userId);
}