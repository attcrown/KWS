package com.otc.kws.core.domain.service.auth;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.otc.kws.core.domain.entity.AuthSession;
import com.otc.kws.core.domain.repository.jpa.JpaAuthSessionRepository;
import com.otc.kws.core.domain.service.BaseKwsService;

@Service
public class AuthSessionService extends BaseKwsService
{
	@Autowired
	protected JpaAuthSessionRepository authSessionRepository;
	
	
	public AuthSession findById(String id)
	{
		return authSessionRepository.findById(id).orElse(null);
	}
	
	public AuthSession findBySessionId(String sessionId)
	{
		return authSessionRepository.findBySessionId(sessionId);
	}
	
	public AuthSession findByAccessToken(String accessToken)
	{
		return authSessionRepository.findByAccessToken(accessToken);
	}
	
	@Transactional
	public AuthSession create(AuthSession authSession)
	{
		return authSessionRepository.save(authSession);
	}
	
	@Transactional
	public AuthSession update(AuthSession authSession)
	{
		return authSessionRepository.save(authSession);
	}
	
	@Transactional
	public void removeById(String id)
	{
		authSessionRepository.deleteById(id);
	}
	
	@Transactional
	public void removeByAccessToken(String accessToken)
	{
		authSessionRepository.removeByAccessToken(accessToken);
	}
	
	@Transactional
	public void removeBySessionId(String sessionId)
	{
		authSessionRepository.removeBySessionId(sessionId);
	}
	
	@Transactional
	public List<AuthSession> removeExpiredAuthSessions()
	{
		return authSessionRepository.removeExpiredAuthSessions();
	}
}