package com.otc.kws.core.domain.service.user;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.stereotype.Component;

import com.otc.kws.core.domain.constant.value.MasterStatusValue;
import com.otc.kws.core.domain.constant.value.UserAccountGenusValue;
import com.otc.kws.core.domain.entity.Role;
import com.otc.kws.core.domain.entity.UserAccount;
import com.otc.kws.core.domain.entity.UserPersonalInfo;
import com.otc.kws.core.domain.entity.UserRoleMapper;
import com.otc.kws.core.domain.exception.KwsRuntimeException;
import com.otc.kws.core.domain.exception.user.UserAccountNotFoundException;
import com.otc.kws.core.domain.exception.user.UserPersonalInfoNotFoundException;
import com.otc.kws.core.domain.model.GrantedAuthority;
import com.otc.kws.core.domain.model.UserProfile;

@Component
public class BuildUserProfileCommandV2 extends BuildUserProfileCommand
{

	@Override
	public Response buildUserProfile(Request request) throws UserAccountNotFoundException, UserPersonalInfoNotFoundException
	{
		UserAccount userAccount = request.getUserAccount();
		
		if(userAccount == null) {
			if(request.getSearchCriteria() != null) {
				if(request.getSearchCriteria() == SearchCriteria.USER_ID) {
					userAccount = userAccountService.findById(request.getUserId());
				} else if(request.getSearchCriteria() == SearchCriteria.USERNAME) {
					userAccount = userAccountService.findByUsername(request.getUsername());
				} else if(request.getSearchCriteria() == SearchCriteria.USER_ACCOUNT) {
					userAccount = request.getUserAccount();
				}
			}
		}
		
		if(userAccount == null) {
			if(request.getSearchCriteria() == SearchCriteria.USER_ID) {
				throw new UserAccountNotFoundException(UserAccountNotFoundException.FindBy.USER_ID, request.getUserId(), null);
			} else if(request.getSearchCriteria() == SearchCriteria.USERNAME) {
				throw new UserAccountNotFoundException(UserAccountNotFoundException.FindBy.USERNAME, null, request.getUsername());
			}
		}
		
		CompletableFuture<UserPersonalInfo> userPersonalInfoFuture = CompletableFuture.completedFuture(null);
		CompletableFuture<List<UserRoleMapper>> userRoleMappersFuture = userRoleMapperService.findAllActivatedUserIdAsync(userAccount.getId());
		
		if(userAccount.getGenus() == UserAccountGenusValue.Human) {
			userPersonalInfoFuture = userPersonalInfoService.findByIdAsync(userAccount.getId());
		}
		
		CompletableFuture.allOf(userPersonalInfoFuture, userRoleMappersFuture).join();
		
		UserPersonalInfo userPersonalInfo = null;
		try {
			userPersonalInfo = userPersonalInfoFuture.get();
		} catch(Exception ex) {
			throw new KwsRuntimeException(ex);
		}
		
		List<UserRoleMapper> userRoleMappers = null;
		try {
			userRoleMappers = userRoleMappersFuture.get();
		} catch(Exception ex) {
			throw new KwsRuntimeException(ex);
		}
		
		if(userAccount.getGenus() == UserAccountGenusValue.Human && userPersonalInfo == null) {
			throw new UserPersonalInfoNotFoundException(userAccount.getId());
		}
		
		UserProfile.User user = new UserProfile.User();
		user.setId(userAccount.getId());
		user.setUsername(userAccount.getUsername());
		user.setEmail(userAccount.getEmail());
		user.setGenus(userAccount.getGenus());
		user.setProfileImageURI(userAccount.getProfileImageURI());
		
		if(userAccount.getProfileImageURI() != null) {
			final UserAccount _userAccount = userAccount;
			String profileImageURL = fileStoreService.getURL(req -> {
				req.copyFrom(request);
				req.setFileURI(_userAccount.getProfileImageURI());
			}).getUrl();
			user.setProfileImageURL(profileImageURL);
		}
		
		user.setGender(userPersonalInfo.getGender());
		user.setFirstName(userPersonalInfo.getFirstName());
		user.setLastName(userPersonalInfo.getLastName());
		user.setNickName(userPersonalInfo.getNickName());
		
		List<UserProfile.Role> roles = new ArrayList<>();
		
		if(userRoleMappers != null && !userRoleMappers.isEmpty()) {
			for(UserRoleMapper userRoleMapper : userRoleMappers) {
				Role role = roleService.getById(userRoleMapper.getRoleId());
				if(role != null && role.getStatus() == MasterStatusValue.Active) {
					roles.add(new UserProfile.Role(role.getId(), role.getName()));
				}
			}
		}
		
		UserProfile userProfile = new UserProfile();
		userProfile.setUser(user);
		userProfile.setRoles(roles);
		
		if(request.isLoadGrantedAuthority()) {
			GrantedAuthority grantedAuthority = authService.fetchGrantedAuthority(req -> {
				req.setUserProfile(userProfile);
			}).getGrantedAuthority();
			
			userProfile.setGrantedAuthority(grantedAuthority);
		}
		
		Response response = new Response();
		response.setUserProfile(userProfile);
		
		return response;
	}
}