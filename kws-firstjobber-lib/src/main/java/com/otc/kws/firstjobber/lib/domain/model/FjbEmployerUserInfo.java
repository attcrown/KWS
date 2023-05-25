package com.otc.kws.firstjobber.lib.domain.model;

import com.otc.kws.core.domain.constant.value.GenderValue;
import com.otc.kws.core.domain.constant.value.MasterStatusValue;
import com.otc.kws.core.domain.constant.value.UserAccountGenusValue;
import com.otc.kws.core.domain.entity.UserAccount;
import com.otc.kws.core.domain.entity.UserPersonalInfo;
import com.otc.kws.firstjobber.lib.domain.entity.FjbEmployerUser;

import lombok.Data;

@Data
public class FjbEmployerUserInfo 
{
	protected String id;
	protected String userId;
	protected String employerId;
	protected GenderValue gender;
	protected String firstName;
	protected String lastName;
	protected String nickName;
	protected String mobileNo;
	protected String email;
	protected String lineId;
	protected String department;
	protected String position;
	protected MasterStatusValue status;
	
	
	public static FjbEmployerUser extractFjbEmployerUser(FjbEmployerUserInfo fjbEmployerUserInfo)
	{
		if(fjbEmployerUserInfo != null) {
			FjbEmployerUser fjbEmployerUser = new FjbEmployerUser();
			
			fjbEmployerUser.setId(fjbEmployerUserInfo.getId());
			fjbEmployerUser.setUserId(fjbEmployerUserInfo.getUserId());
			fjbEmployerUser.setEmployerId(fjbEmployerUserInfo.getEmployerId());
			fjbEmployerUser.setDepartment(fjbEmployerUserInfo.getDepartment());
			fjbEmployerUser.setPosition(fjbEmployerUserInfo.getPosition());
			fjbEmployerUser.setStatus(fjbEmployerUserInfo.getStatus());
			
			return fjbEmployerUser;
		}
		
		return null;
	}
	
	
	public static UserPersonalInfo extractUserPersonalInfo(FjbEmployerUserInfo fjbEmployerUserInfo)
	{
		if(fjbEmployerUserInfo != null) {
			UserPersonalInfo userPersonalInfo = new UserPersonalInfo();
			
			userPersonalInfo.setUserId(fjbEmployerUserInfo.getUserId());
			userPersonalInfo.setGender(fjbEmployerUserInfo.getGender());
			userPersonalInfo.setFirstName(fjbEmployerUserInfo.getFirstName());
			userPersonalInfo.setLastName(fjbEmployerUserInfo.getLastName());
			userPersonalInfo.setNickName(fjbEmployerUserInfo.getNickName());
			userPersonalInfo.setMobileNo(fjbEmployerUserInfo.getMobileNo());
			userPersonalInfo.setLineId(fjbEmployerUserInfo.getLineId());
			
			return userPersonalInfo;
		}
		
		return null;
	}
	
	
	public static UserAccount extractUserAccount(FjbEmployerUserInfo fjbEmployerUserInfo)
	{
		if(fjbEmployerUserInfo != null) {
			UserAccount userAccount = new UserAccount();
			
			userAccount.setId(fjbEmployerUserInfo.getUserId());
			userAccount.setUsername(fjbEmployerUserInfo.getEmail());
			userAccount.setEmail(fjbEmployerUserInfo.getEmail());
			userAccount.setGenus(UserAccountGenusValue.Human);
			
			return userAccount;
		}
		
		return null;
	}
}