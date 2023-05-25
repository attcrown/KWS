package com.otc.kws.core.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.otc.kws.core.domain.service.auth.AuthSessionService;

@Component
public class ClearExpiredAuthSessionsScheduler 
{
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	protected AuthSessionService authSessionService;
	
	
	@Scheduled(fixedRate = 5 * 60 * 1_000) // run every 5 min.
	public void clearExpiredAuthSessions() 
	{
		logger.debug("### ClearExpiredAuthSessionsScheduler.clearExpiredAuthSessions ###");
		authSessionService.removeExpiredAuthSessions();
	}
}