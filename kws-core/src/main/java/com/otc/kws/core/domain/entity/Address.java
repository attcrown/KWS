package com.otc.kws.core.domain.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Entity
@Table(name = "core_m_address")
@Data
public class Address extends BaseKwsEntity
{
	@Id
	@Column(name = "id")
	protected String id;
	
	@Column(name = "address_type")
	protected String addressType;
	
	@Column(name = "address_ref_id")
	protected String addressRefId;
	
	@Column(name = "address_info")
	protected String addressInfo;
	
	@Column(name = "country_id")
	protected String countryId;
	
	@Column(name = "country_name")
	protected String countryName;
	
	@Column(name = "province_id")
	protected String provinceId;
	
	@Column(name = "province_name")
	protected String provinceName;
	
	@Column(name = "amphur_id")
	protected String amphurId;
	
	@Column(name = "amphur_name")
	protected String amphurName;
	
	@Column(name = "district_id")
	protected String districtId;
	
	@Column(name = "district_name")
	protected String districtName;
	
	@Column(name = "post_code")
	protected String postCode;
	
	@Column(name = "location_lat")
	protected Double locationLat;
	
	@Column(name = "location_lon")
	protected Double locationLon;
	
	@Column(name = "created_by")
	protected String createdBy;
	
	@Column(name = "created_at")
	@Temporal(TemporalType.TIMESTAMP)
	protected Date createdAt;
	
	@Column(name = "last_modified_by")
	protected String lastModifiedBy;
	
	@Column(name = "last_modified_at")
	@Temporal(TemporalType.TIMESTAMP)
	protected Date lastModifiedAt;
}