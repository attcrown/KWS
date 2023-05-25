package com.otc.kws.core.domain.service;

import java.util.Date;

import com.otc.kws.core.domain.constant.value.PlatformValue;
import com.otc.kws.core.domain.entity.AuthSession;
import com.otc.kws.core.domain.model.UserProfile;

import lombok.Data;

@Data
public abstract class BaseKwsCommandRequest 
{
	protected PlatformValue performedPlatform;
	protected String performedAPI;
	protected AuthSession performedAuthSession;
	protected UserProfile performedUserProfile;
	protected String performedBy;
	protected Date performedAt;
	
	public void copyFrom(BaseKwsCommandRequest request)
	{
		performedPlatform = request.getPerformedPlatform();
		performedAPI = request.getPerformedAPI();
		performedAuthSession = request.getPerformedAuthSession();
		performedUserProfile = request.getPerformedUserProfile();
		performedBy = request.getPerformedBy();
		performedAt = request.getPerformedAt();
	}
}