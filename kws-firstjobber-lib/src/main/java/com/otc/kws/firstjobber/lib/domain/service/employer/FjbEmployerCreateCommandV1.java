package com.otc.kws.firstjobber.lib.domain.service.employer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.otc.kws.core.domain.constant.RoleConst;
import com.otc.kws.core.domain.constant.value.MasterStatusValue;
import com.otc.kws.core.domain.entity.UserAccount;
import com.otc.kws.core.domain.entity.UserPersonalInfo;
import com.otc.kws.core.domain.entity.UserRoleMapper;
import com.otc.kws.core.domain.exception.KwsRuntimeException;
import com.otc.kws.firstjobber.lib.domain.constant.FjbEmployerConst;
import com.otc.kws.firstjobber.lib.domain.entity.FjbAddress;
import com.otc.kws.firstjobber.lib.domain.entity.FjbEmployer;
import com.otc.kws.firstjobber.lib.domain.entity.FjbEmployerUser;
import com.otc.kws.firstjobber.lib.domain.model.FjbEmployerInfo;
import com.otc.kws.firstjobber.lib.domain.model.FjbEmployerUserInfo;
import com.otc.kws.firstjobber.lib.domain.profile.FjbEmployerProfile;
import com.otc.kws.firstjobber.lib.domain.profile.FjbEmployerUserProfile;

//@Component
public class FjbEmployerCreateCommandV1 extends FjbEmployerCreateCommand
{
	@Override
	public Response createEmployer(Request request) 
	{
		FjbEmployerProfile employerProfile = new FjbEmployerProfile();
		
		FjbEmployer employer = FjbEmployerInfo.extractFjbEmployer(request.getFjbEmployerInfo());
		if(employer.getId() == null) {
			employer.setId(UUID.randomUUID().toString());
		}
		if(employer.getStatus() == null) {
			employer.setStatus(MasterStatusValue.Active);
		}
		employer.setCreatedBy(request.getPerformedBy());
		employer.setCreatedAt(request.getPerformedAt());
		
		FjbAddress employerContactAddress = FjbEmployerInfo.extractFjbAddress(request.getFjbEmployerInfo());
		if(employerContactAddress.getId() == null) {
			employerContactAddress.setId(UUID.randomUUID().toString());
		}
		if(employerContactAddress.getStatus() == null) {
			employerContactAddress.setStatus(employer.getStatus());
		}
		if(employerContactAddress.getAddressType() == null) {
			employerContactAddress.setAddressType(FjbEmployerConst.ADDRESS_TYPE_CONTACT);
		}
		if(employerContactAddress.getAddressRefId() == null) {
			employerContactAddress.setAddressRefId(employer.getId());
		}
		employerContactAddress.setCreatedBy(request.getPerformedBy());
		employerContactAddress.setCreatedAt(request.getPerformedAt());
		
		employer.setContactAddressId(employerContactAddress.getId());
		
		try {
			employer = fjbEmployerService.create(employer);
			logger.info("create employer [" + employer + "]");
		} catch(Exception ex) {
			throw new KwsRuntimeException(ex);
		}
		
		try {
			employerContactAddress = fjbAddressService.create(employerContactAddress);
			logger.info("create employerContactAddress [" + employerContactAddress + "]");
		} catch(Exception ex) {
			throw new KwsRuntimeException(ex);
		}
		
		employerProfile.setEmployer(employer);
		employerProfile.setContactAddress(employerContactAddress);
		
		if(request.getFjbEmployerUserInfos() != null && !request.getFjbEmployerUserInfos().isEmpty()) {
			
			List<FjbEmployerUserProfile> employerUserProfiles = new ArrayList<>();
			
			for(FjbEmployerUserInfo employerUserInfo : request.getFjbEmployerUserInfos()) {
				
				UserAccount userAccount = FjbEmployerUserInfo.extractUserAccount(employerUserInfo);
				if(userAccount.getId() == null) {
					userAccount.setId(UUID.randomUUID().toString());
				}
				if(userAccount.getStatus() == null) {
					userAccount.setStatus(employer.getStatus());
				}
				userAccount.setCreatedBy(request.getPerformedBy());
				userAccount.setCreatedAt(request.getPerformedAt());
				try {
					userAccount = userAccountService.create(userAccount);
					logger.info("create userAccount [" + userAccount + "]");
				} catch(Exception ex) {
					throw new KwsRuntimeException(ex);
				}
				
				UserRoleMapper userRoleMapper = new UserRoleMapper();
				userRoleMapper.setId(UUID.randomUUID().toString());
				userRoleMapper.setUserId(userAccount.getId());
				userRoleMapper.setRoleId(RoleConst.ID_FJB_EMPLOYER);
				userRoleMapper.setStatus(MasterStatusValue.Active);
				userRoleMapper.setCreatedBy(request.getPerformedBy());
				userRoleMapper.setCreatedAt(request.getPerformedAt());
				try {
					userRoleMapper = userRoleMapperService.create(userRoleMapper);
					logger.info("create userRoleMapper [" + userRoleMapper + "]");
				} catch(Exception ex) {
					throw new KwsRuntimeException(ex);
				}
				
				UserPersonalInfo userPersonalInfo = FjbEmployerUserInfo.extractUserPersonalInfo(employerUserInfo);
				if(userPersonalInfo.getUserId() == null) {
					userPersonalInfo.setUserId(userAccount.getId());
				}
				if(userPersonalInfo.getStatus() == null) {
					userPersonalInfo.setStatus(MasterStatusValue.Active);
				}
				userPersonalInfo.setCreatedBy(request.getPerformedBy());
				userPersonalInfo.setCreatedAt(request.getPerformedAt());
				try {
					userPersonalInfo = userPersonalInfoService.create(userPersonalInfo);
					logger.info("create userPersonalInfo [" + userPersonalInfo + "]");
				} catch(Exception ex) {
					throw new KwsRuntimeException(ex);
				}
				
				FjbEmployerUser employerUser = FjbEmployerUserInfo.extractFjbEmployerUser(employerUserInfo);
				if(employerUser.getId() == null) {
					employerUser.setId(UUID.randomUUID().toString());
				}
				if(employerUser.getUserId() == null) {
					employerUser.setUserId(userAccount.getId());
				}
				if(employerUser.getEmployerId() == null) {
					employerUser.setEmployerId(employer.getId());
				}
				if(employerUser.getStatus() == null) {
					employerUser.setStatus(employer.getStatus());
				}
				employerUser.setCreatedBy(request.getPerformedBy());
				employerUser.setCreatedAt(request.getPerformedAt());
				try {
					employerUser = fjbEmployerUserService.create(employerUser);
					logger.info("create employerUser [" + employerUser + "]");
				} catch(Exception ex) {
					throw new KwsRuntimeException(ex);
				}
				
				FjbEmployerUserProfile employerUserProfile = new FjbEmployerUserProfile();
				employerUserProfile.setEmployerUser(employerUser);
				employerUserProfile.setUserAccount(userAccount);
				employerUserProfile.setUserRoleMappers(Arrays.asList(userRoleMapper));
				employerUserProfile.setUserPersonalInfo(userPersonalInfo);
				employerUserProfiles.add(employerUserProfile);
			}
			
			employerProfile.setEmployerUserProfiles(employerUserProfiles);
		}
		
		Response response = new Response();
		response.setEmployerProfile(employerProfile);
		
		return response;
	}
}