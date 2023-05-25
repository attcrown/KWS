package com.otc.kws.core.domain.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import com.otc.kws.core.domain.entity.UserAccount;
import com.otc.kws.core.domain.exception.user.UserAccountNotFoundException;
import com.otc.kws.core.domain.exception.user.UserPersonalInfoNotFoundException;
import com.otc.kws.core.domain.model.UserProfile;
import com.otc.kws.core.domain.service.BaseKwsCommand;
import com.otc.kws.core.domain.service.BaseKwsCommandRequest;
import com.otc.kws.core.domain.service.BaseKwsCommandResponse;
import com.otc.kws.core.domain.service.RoleService;
import com.otc.kws.core.domain.service.auth.AuthService;
import com.otc.kws.core.domain.service.filestore.FileStoreService;

import lombok.Data;

public abstract class BuildUserProfileCommand extends BaseKwsCommand
{
	@Autowired
	@Lazy
	protected UserService userService;
	
	@Autowired
	protected UserAccountService userAccountService;
	
	@Autowired
	protected UserPersonalInfoService userPersonalInfoService;
	
	@Autowired
	protected UserRoleMapperService userRoleMapperService;
	
	@Autowired
	protected RoleService roleService;
	
	@Autowired
	protected AuthService authService;
	
	@Autowired
	protected FileStoreService fileStoreService;
	
	
	public abstract Response buildUserProfile(Request request) throws UserAccountNotFoundException, UserPersonalInfoNotFoundException;
	
	
	@Data
	public static class Request extends BaseKwsCommandRequest
	{
		protected SearchCriteria searchCriteria;
		protected String userId;
		protected String username;
		protected UserAccount userAccount;
		protected boolean loadGrantedAuthority = false;
	}
	
	@Data
	public static class Response extends BaseKwsCommandResponse
	{
		protected UserProfile userProfile;
	}
	
	public static enum SearchCriteria
	{
		USER_ID, USERNAME, USER_ACCOUNT
	}
}