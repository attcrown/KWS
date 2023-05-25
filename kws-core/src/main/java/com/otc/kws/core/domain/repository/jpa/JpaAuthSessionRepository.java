package com.otc.kws.core.domain.repository.jpa;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.otc.kws.core.domain.entity.AuthSession;

@Repository
public interface JpaAuthSessionRepository extends KwsJpaRepository<AuthSession, String>
{
	public AuthSession findBySessionId(String sessionId);
	public AuthSession findByAccessToken(String accessToken);
	
	public AuthSession removeByAccessToken(String accessToken);
	public AuthSession removeBySessionId(String sessionId);
	
	public List<AuthSession> removeByExpiredAtLessThan(Date date);
	
	public default List<AuthSession> removeExpiredAuthSessions()
	{
		return removeByExpiredAtLessThan(new Date());
	}
}