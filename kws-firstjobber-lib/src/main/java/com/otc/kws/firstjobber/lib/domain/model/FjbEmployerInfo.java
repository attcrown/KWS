package com.otc.kws.firstjobber.lib.domain.model;

import com.otc.kws.core.domain.constant.value.MasterStatusValue;
import com.otc.kws.firstjobber.lib.domain.entity.FjbAddress;
import com.otc.kws.firstjobber.lib.domain.entity.FjbEmployer;

import lombok.Data;

@Data
public class FjbEmployerInfo 
{
	protected String id;
	protected String employerTypeId;
	protected String name;
	protected String bizType;
	protected String bizBackground;
	protected String registNo;
	protected String telNo;
	protected String faxNo;
	protected String email;
	protected String website;
	protected String facebookPageURL;
	protected ContactAddress contactAddress;
	protected String remark;
	protected MasterStatusValue status;
	
	@Data
	public static class ContactAddress
	{
		protected String id;
		protected String addressInfo;
		protected String countryId;
		protected String countryName;
		protected String provinceId;
		protected String provinceName;
		protected String amphurId;
		protected String amphurName;
		protected String districtId;
		protected String districtName;
		protected String postCode;
		protected Double locationLat;
		protected Double locationLon;
		protected MasterStatusValue status;
	}
	
	
	public static FjbEmployer extractFjbEmployer(FjbEmployerInfo fjbEmployerInfo)
	{
		FjbEmployer fjbEmployer = new FjbEmployer();
		fjbEmployer.setId(fjbEmployerInfo.getId());
		fjbEmployer.setEmployerTypeId(fjbEmployerInfo.getEmployerTypeId());
		fjbEmployer.setName(fjbEmployerInfo.getName());
		fjbEmployer.setBizType(fjbEmployerInfo.getBizType());
		fjbEmployer.setBizBackground(fjbEmployerInfo.getBizBackground());
		fjbEmployer.setRegistNo(fjbEmployerInfo.getRegistNo());
		fjbEmployer.setTelNo(fjbEmployerInfo.getTelNo());
		fjbEmployer.setFaxNo(fjbEmployerInfo.getFaxNo());
		fjbEmployer.setEmail(fjbEmployerInfo.getEmail());
		fjbEmployer.setWebsite(fjbEmployerInfo.getWebsite());
		fjbEmployer.setFacebookPageURL(fjbEmployerInfo.getFacebookPageURL());
		fjbEmployer.setRemark(fjbEmployerInfo.getRemark());
		fjbEmployer.setStatus(fjbEmployerInfo.getStatus());
		
		return fjbEmployer;
	}
	
	
	public static FjbAddress extractFjbAddress(FjbEmployerInfo fjbEmployerInfo)
	{
		if(fjbEmployerInfo.getContactAddress() != null) {
			FjbAddress fjbAddress = new FjbAddress();
			
			fjbAddress.setId(fjbEmployerInfo.getContactAddress().getId());
			fjbAddress.setAddressInfo(fjbEmployerInfo.getContactAddress().getAddressInfo());
			fjbAddress.setCountryId(fjbEmployerInfo.getContactAddress().getCountryId());
			fjbAddress.setCountryName(fjbEmployerInfo.getContactAddress().getCountryName());
			fjbAddress.setProvinceId(fjbEmployerInfo.getContactAddress().getProvinceId());
			fjbAddress.setProvinceName(fjbEmployerInfo.getContactAddress().getProvinceName());
			fjbAddress.setAmphurId(fjbEmployerInfo.getContactAddress().getAmphurId());
			fjbAddress.setAmphurName(fjbEmployerInfo.getContactAddress().getAmphurName());
			fjbAddress.setDistrictId(fjbEmployerInfo.getContactAddress().getDistrictId());
			fjbAddress.setDistrictName(fjbEmployerInfo.getContactAddress().getDistrictName());
			fjbAddress.setPostCode(fjbEmployerInfo.getContactAddress().getPostCode());
			fjbAddress.setLocationLat(fjbEmployerInfo.getContactAddress().getLocationLat());
			fjbAddress.setLocationLon(fjbEmployerInfo.getContactAddress().getLocationLon());
			fjbAddress.setStatus(fjbEmployerInfo.getContactAddress().getStatus());
			
			return fjbAddress;
		}
		
		return null;
	}
}