package com.otc.kws.firstjobber.lib.domain.service.employer;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.otc.kws.core.domain.entity.UserAccount;
import com.otc.kws.core.domain.entity.UserPersonalInfo;
import com.otc.kws.core.domain.entity.UserRoleMapper;
import com.otc.kws.core.domain.exception.KwsRuntimeException;
import com.otc.kws.firstjobber.lib.domain.entity.FjbAddress;
import com.otc.kws.firstjobber.lib.domain.entity.FjbEmployer;
import com.otc.kws.firstjobber.lib.domain.entity.FjbEmployerUser;
import com.otc.kws.firstjobber.lib.domain.profile.FjbEmployerProfile;
import com.otc.kws.firstjobber.lib.domain.profile.FjbEmployerUserProfile;

@Component
public class FjbEmployerUpdateCommandV1 extends FjbEmployerUpdateCommand
{
	@Override
	public Response updateEmployer(Request request) 
	{
		FjbEmployer employer = request.getEmployerProfile().getEmployer();
		FjbAddress contactAddress = request.getEmployerProfile().getContactAddress();
		List<FjbEmployerUserProfile> employerUserProfiles = request.getEmployerProfile().getEmployerUserProfiles();
		
		// ********** check duplicate employerUser.email ********** //
		if(employerUserProfiles != null && !employerUserProfiles.isEmpty()) {
			for(FjbEmployerUserProfile employerUserProfile : employerUserProfiles) {
				UserAccount userAccount = employerUserProfile.getUserAccount();
				if(userAccount != null && userAccount.getUsername() != null) {
					UserAccount _userAccount = userAccountService.findByUsername(userAccount.getUsername());
					if(_userAccount != null && !_userAccount.getId().equals(userAccount.getId())) {
						throw new KwsRuntimeException("Duplicate UserAccount email ["+userAccount.getUsername()+"]");
					}
				}
			}
		}
		// ********** check duplicate employerUser.email ********** //
		
		employer.setLastModifiedBy(request.getPerformedBy());
		employer.setLastModifiedAt(request.getPerformedAt());
		try {
			employer = fjbEmployerService.update(employer);
			logger.info("update employer [" + employer + "]");
		} catch(Exception ex) {
			throw new KwsRuntimeException(ex);
		}
		
		contactAddress.setLastModifiedBy(request.getPerformedBy());
		contactAddress.setLastModifiedAt(request.getPerformedAt());
		try {
			contactAddress = fjbAddressService.update(contactAddress);
			logger.info("update contactAddress [" + contactAddress + "]");
		} catch(Exception ex) {
			throw new KwsRuntimeException(ex);
		}
		
		FjbEmployerProfile employerProfile = new FjbEmployerProfile();
		employerProfile.setEmployer(employer);
		employerProfile.setContactAddress(contactAddress);
		
		if(employerUserProfiles != null && !employerUserProfiles.isEmpty()) {
			List<FjbEmployerUserProfile> updatedEmployerUserProfiles = new ArrayList<>();
			for(FjbEmployerUserProfile employerUserProfile : employerUserProfiles) {
				FjbEmployerUser employerUser = employerUserProfile.getEmployerUser();
				if(employerUser != null) {
					employerUser.setLastModifiedBy(request.getPerformedBy());
					employerUser.setLastModifiedAt(request.getPerformedAt());
					try {
						employerUser = fjbEmployerUserService.update(employerUser);
						logger.info("update employerUser [" + employerUser + "]");
					} catch(Exception ex) {
						throw new KwsRuntimeException(ex);
					}
				}
				
				UserAccount userAccount = employerUserProfile.getUserAccount();
				if(userAccount != null) {
					userAccount.setLastModifiedBy(request.getPerformedBy());
					userAccount.setLastModifiedAt(request.getPerformedAt());
					try {
						userAccount = userAccountService.update(userAccount);
						logger.info("update userAccount [" + userAccount + "]");
					} catch(Exception ex) {
						throw new KwsRuntimeException(ex);
					}
				}
				
				UserPersonalInfo userPersonalInfo = employerUserProfile.getUserPersonalInfo();
				if(userPersonalInfo != null) {
					userPersonalInfo.setLastModifiedBy(request.getPerformedBy());
					userPersonalInfo.setLastModifiedAt(request.getPerformedAt());
					try {
						userPersonalInfo = userPersonalInfoService.update(userPersonalInfo);
						logger.info("update userPersonalInfo [" + userPersonalInfo + "]");
					} catch(Exception ex) {
						throw new KwsRuntimeException(ex);
					}
				}
				
				List<UserRoleMapper> userRoleMappers = employerUserProfile.getUserRoleMappers();
				if(userRoleMappers != null && !userRoleMappers.isEmpty()) {
					for(UserRoleMapper userRoleMapper : userRoleMappers) {
						userRoleMapper.setLastModifiedBy(request.getPerformedBy());
						userRoleMapper.setLastModifiedAt(request.getPerformedAt());
						try {
							userRoleMapper = userRoleMapperService.update(userRoleMapper);
							logger.info("update userRoleMapper [" + userRoleMapper + "]");
						} catch(Exception ex) {
							throw new KwsRuntimeException(ex);
						}
					}
				}
				
				FjbEmployerUserProfile updatedEmployerUserProfile = new FjbEmployerUserProfile();
				updatedEmployerUserProfile.setEmployerUser(employerUser);
				updatedEmployerUserProfile.setUserAccount(userAccount);
				updatedEmployerUserProfile.setUserPersonalInfo(userPersonalInfo);
				updatedEmployerUserProfile.setUserRoleMappers(userRoleMappers);
				
				updatedEmployerUserProfiles.add(updatedEmployerUserProfile);
			}
			employerProfile.setEmployerUserProfiles(updatedEmployerUserProfiles);
		}
		
		Response response = new Response();
		response.setEmployerProfile(employerProfile);
		
		return response;
	}
}