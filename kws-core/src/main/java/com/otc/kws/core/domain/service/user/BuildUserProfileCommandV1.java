package com.otc.kws.core.domain.service.user;

import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.otc.kws.core.domain.exception.user.UserAccountNotFoundException;
import com.otc.kws.core.domain.exception.user.UserPersonalInfoNotFoundException;
import com.otc.kws.core.domain.model.UserProfile;

//@Component
public class BuildUserProfileCommandV1 extends BuildUserProfileCommand
{
	@Override
	public Response buildUserProfile(Request request) throws UserAccountNotFoundException, UserPersonalInfoNotFoundException
	{
		Response response = new Response();
		
		UserProfile.User user = null;
		/*
		if(request.getSearchCriteria() != null) {
			if(request.getSearchCriteria() == SearchCriteria.USER_ID) {
				user = userService.findUserByUserId(request.getUserId());
			} else if(request.getSearchCriteria() == SearchCriteria.USERNAME) {
				user = userService.findUserByUsername(request.getUsername());
			} 
		}
		*/
		
		if(user == null) {
			if(request.getSearchCriteria() == SearchCriteria.USER_ID) {
				throw new UserAccountNotFoundException(UserAccountNotFoundException.FindBy.USER_ID, request.getUserId(), null);
			} else if(request.getSearchCriteria() == SearchCriteria.USERNAME) {
				throw new UserAccountNotFoundException(UserAccountNotFoundException.FindBy.USERNAME, null, request.getUsername());
			}
		}
		
		if(user != null) {
			UserProfile userProfile = new UserProfile();
			userProfile.setUser(user);
			if(user.getRoleIds() != null && !user.getRoleIds().isEmpty()) {
				userProfile.setRoles(user.getRoleIds().stream().map(roleId -> {
					UserProfile.Role role = new UserProfile.Role();
					role.setId(roleId);
					role.setName(roleService.getById(roleId).getName());
					return role;
				}).collect(Collectors.toList()));
			}
			response.setUserProfile(userProfile);
		}
		
		return response;
	}
}