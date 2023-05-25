package com.otc.kws.firstjobber.lib.domain.service.employer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.otc.kws.core.domain.entity.UserAccount;
import com.otc.kws.core.domain.entity.UserPersonalInfo;
import com.otc.kws.core.domain.entity.UserRoleMapper;
import com.otc.kws.core.domain.exception.KwsRuntimeException;
import com.otc.kws.firstjobber.lib.domain.constant.FjbEmployerConst;
import com.otc.kws.firstjobber.lib.domain.entity.FjbAddress;
import com.otc.kws.firstjobber.lib.domain.entity.FjbEmployer;
import com.otc.kws.firstjobber.lib.domain.entity.FjbEmployerUser;
import com.otc.kws.firstjobber.lib.domain.profile.FjbEmployerProfile;
import com.otc.kws.firstjobber.lib.domain.profile.FjbEmployerUserProfile;

@Component
public class FjbEmployerBuildProfileCommandV1 extends FjbEmployerBuildProfileCommand
{
	@Override
	public Response buildEmployerProfile(Request request) 
	{
		FjbEmployerProfile employerProfile = new FjbEmployerProfile();
		
		CompletableFuture<FjbEmployer> employerFuture = fjbEmployerService.findByIdAsync(request.getEmployerId());
		CompletableFuture<List<FjbAddress>> addressesFuture = fjbAddressService.findByAddressTypeAndAddressRefIdAsync(FjbEmployerConst.ADDRESS_TYPE_CONTACT, request.getEmployerId());
		CompletableFuture<List<FjbEmployerUser>> employerUsersFuture = fjbEmployerUserService.findByEmployerIdAsync(request.getEmployerId());
		
		CompletableFuture.allOf(employerFuture, addressesFuture, employerUsersFuture).join();
		
		FjbEmployer employer = null;
		try {
			employer = employerFuture.get();
		} catch(Exception ex) {
			throw new KwsRuntimeException(ex);
		}
		
		if(employer == null) {
			throw new KwsRuntimeException("Employer id ["+request.getEmployerId()+"] Not Found");
		}
		
		FjbAddress employerContactAddress = null;
		try {
			List<FjbAddress> addresses = addressesFuture.get();
			if(addresses != null && !addresses.isEmpty()) {
				employerContactAddress = addresses.get(0);
			}
		} catch(Exception ex) {
			throw new KwsRuntimeException(ex);
		}
		
		if(employerContactAddress == null) {
			throw new KwsRuntimeException("EmployerConactAddress employerId ["+request.getEmployerId()+"] Not Found");
		}
		
		employerProfile.setEmployer(employer);
		employerProfile.setContactAddress(employerContactAddress);
		
		List<FjbEmployerUser> employerUsers = null;
		try {
			employerUsers = employerUsersFuture.get();
		} catch(Exception ex) {
			throw new KwsRuntimeException(ex);
		}
		
		if(employerUsers != null && !employerUsers.isEmpty()) {
			List<String> userIds = employerUsers.stream().map(FjbEmployerUser::getUserId).collect(Collectors.toList());
			if(userIds != null && !userIds.isEmpty()) {
				CompletableFuture<List<UserAccount>> userAccountsFuture = userAccountService.findByIdsAsync(userIds);
				CompletableFuture<List<UserPersonalInfo>> userPersonalInfosFuture = userPersonalInfoService.findByIdsAsync(userIds);
				CompletableFuture<List<UserRoleMapper>> userRoleMappersFuture = userRoleMapperService.findByUserIdsAsync(userIds);
				
				CompletableFuture.allOf(userAccountsFuture, userPersonalInfosFuture).join();
				
				List<UserAccount> userAccounts = null;
				try {
					userAccounts = userAccountsFuture.get();
				} catch(Exception ex) {
					throw new KwsRuntimeException(ex);
				}
				
				List<UserRoleMapper> userRoleMappers = null;
				try {
					userRoleMappers = userRoleMappersFuture.get();
				} catch(Exception ex) {
					throw new KwsRuntimeException(ex);
				}
				
				List<UserPersonalInfo> userPersonalInfos = null;
				try {
					userPersonalInfos = userPersonalInfosFuture.get();
				} catch(Exception ex) {
					throw new KwsRuntimeException(ex);
				}
				
				List<FjbEmployerUserProfile> employerUserProfiles = new ArrayList<>();
				
				for(FjbEmployerUser employerUser : employerUsers) {
					FjbEmployerUserProfile employerUserProfile = new FjbEmployerUserProfile();
					
					employerUserProfile.setEmployerUser(employerUser);
					
					UserAccount userAccount = null;
					if(userAccounts != null && !userAccounts.isEmpty()) {
						userAccount = userAccounts.stream().filter(e -> e.getId().equals(employerUser.getUserId())).findFirst().orElse(null);
					}
					employerUserProfile.setUserAccount(userAccount);
					
					List<UserRoleMapper> _userRoleMappers = null;
					if(userRoleMappers != null && !userRoleMappers.isEmpty()) {
						_userRoleMappers = userRoleMappers.stream().filter(e -> e.getUserId().equals(employerUser.getUserId())).collect(Collectors.toList());
					}
					employerUserProfile.setUserRoleMappers(_userRoleMappers);
					
					UserPersonalInfo userPersonalInfo = null;
					if(userPersonalInfos != null && !userPersonalInfos.isEmpty()) {
						userPersonalInfo = userPersonalInfos.stream().filter(e -> e.getUserId().equals(employerUser.getUserId())).findFirst().orElse(null);
					}
					employerUserProfile.setUserPersonalInfo(userPersonalInfo);
					
					employerUserProfiles.add(employerUserProfile);
				}
				
				employerProfile.setEmployerUserProfiles(employerUserProfiles);
			}
		}
		
		Response response = new Response();
		response.setEmployerProfile(employerProfile);
		
		return response;
	}
}