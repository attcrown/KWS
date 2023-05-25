package com.otc.kws.core.domain.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.otc.kws.core.domain.constant.value.GenderValue;
import com.otc.kws.core.domain.constant.value.MasterStatusValue;
import com.otc.kws.core.domain.constant.value.UserAccountGenusValue;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserProfile 
{
	protected User user;
	protected List<Role> roles;
	protected GrantedAuthority grantedAuthority;
	//protected Employee employee;
	
	
	@Data
	@JsonInclude(JsonInclude.Include.NON_NULL)
	public static class User
	{
		protected String id;
		protected String username;
		@JsonIgnore
		protected String password;
		protected String email;
		protected UserAccountGenusValue genus;
		protected String profileImageURI;
		protected String profileImageURL;
		@JsonIgnore
		protected boolean accountLocked;
		@JsonIgnore
		protected boolean passwordLocked;
		@JsonIgnore
		protected Date activatedDate;
		@JsonIgnore
		protected Date expiredDate;
		@JsonIgnore
		protected MasterStatusValue status;
		
		protected GenderValue gender;
		protected String genderTitle;
		protected String prefixNameId;
		protected String perfixNameTitle;
		protected String firstName;
		protected String lastName;
		protected String nickName;
		@JsonIgnore
		protected List<String> roleIds;
	}
	
	
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	@JsonInclude(JsonInclude.Include.NON_NULL)
	public static class Role
	{
		protected String id;
		protected String name;
	}
	
	
	/*
	@Data
	@JsonInclude(JsonInclude.Include.NON_NULL)
	public static class Employee
	{
		protected String employeeId;
		protected String employeeCode;
		protected String employeeTypeId;
		protected String employeeTypeName;
		protected String employeeStatusId;
		protected String employeeStatusName;
		protected String companyId;
		protected String companyName;
		protected String departmentId;
		protected String departmentName;
		protected String positionId;
		protected String positionName;
	}
	*/
}