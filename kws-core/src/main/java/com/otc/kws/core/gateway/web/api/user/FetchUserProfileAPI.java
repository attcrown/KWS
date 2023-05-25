package com.otc.kws.core.gateway.web.api.user;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.otc.kws.core.domain.exception.user.UserAccountNotFoundException;
import com.otc.kws.core.domain.exception.user.UserPersonalInfoNotFoundException;
import com.otc.kws.core.domain.model.UserProfile;
import com.otc.kws.core.domain.service.user.BuildUserProfileCommand;
import com.otc.kws.core.domain.service.user.UserService;

import com.otc.kws.core.gateway.web.api.BaseKwsAPI;
import com.otc.kws.core.gateway.web.api.ResponseMessage;

import lombok.Data;

@RestController
@RequestMapping("/api/user")
public class FetchUserProfileAPI extends BaseKwsAPI
{
	@Autowired
	protected UserService userService;
	
	
	@PostMapping("/fetch-user-profile")
	public ResponseEntity<?> fetchUserProfile(HttpServletRequest request, @RequestBody RequestMessage requestMessage) throws UserAccountNotFoundException, UserPersonalInfoNotFoundException
	{
		logger.info("### FetchUserProfileAPI.fetchUserProfile ###");
		logger.info("requestMessage => " + requestMessage);
		
		UserProfile userProfile = userService.buildUserProfile(req -> {
			setupServiceRequest(request, req);
			if(requestMessage.getUserId() != null) {
				req.setSearchCriteria(BuildUserProfileCommand.SearchCriteria.USER_ID);
				req.setUserId(requestMessage.getUserId());
			} else if(requestMessage.getUsername() != null) {
				req.setSearchCriteria(BuildUserProfileCommand.SearchCriteria.USERNAME);
				req.setUsername(requestMessage.getUsername());
			}
			req.setLoadGrantedAuthority(requestMessage.isLoadGrantedAuthority());
		}).getUserProfile();
		
		BodyResponseMessage bodyResponseMessage = new BodyResponseMessage();
		bodyResponseMessage.setUserProfile(userProfile);
		
		return replySuccess(request, bodyResponseMessage);
	}
	
	
	@Data
	public static class RequestMessage
	{
		protected String userId;
		protected String username;
		protected boolean loadGrantedAuthority = false;
	}
	
	
	@Data
	public static class BodyResponseMessage extends ResponseMessage.BaseBody
	{
		protected UserProfile userProfile;
	}
}