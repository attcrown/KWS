package com.otc.kws.core.domain.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.otc.kws.core.domain.constant.KwsConst;
import com.otc.kws.core.domain.constant.value.MasterStatusValue;
import com.otc.kws.core.domain.constant.value.SchoolOwnerTypeValue;

import lombok.Data;

@Entity
@Table(name = "core_m_school")
@Data
public class School extends BaseKwsEntity
{
	@Id
	@Column(name = "id")
	protected String id;
	
	@Column(name = "school_type_id")
	protected String schoolTypeId;
	
	@Column(name = "school_owner_type_id")
	@Enumerated(EnumType.STRING)
	protected SchoolOwnerTypeValue schoolOwnerTypeId;
	
	@Column(name = "name")
	protected String name;
	
	@Column(name = "remark")
	protected String remark;
	
	@Column(name = "phone_no")
	protected String phoneNo;
	
	@Column(name = "fax_no")
	protected String faxNo;
	
	@Column(name = "email")
	protected String email;
	
	@Column(name = "website")
	protected String website;
	
	@Column(name = "facebook_page_url")
	protected String facebookPageURL;
	
	@Column(name = "logo_image_uri")
	protected String logoImageURI;
	
	@Column(name = "address_id")
	protected String addressId;
	
	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	protected MasterStatusValue status;
	
	@Column(name = "created_by")
	protected String createdBy;
	
	@Column(name = "created_at")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = KwsConst.DEFAULT_DATETIME_FORMAT, timezone = KwsConst.DEFAULT_TIMEZONE)
	protected Date createdAt;
	
	@Column(name = "last_modified_by")
	protected String lastModifiedBy;
	
	@Column(name = "last_modified_at")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = KwsConst.DEFAULT_DATETIME_FORMAT, timezone = KwsConst.DEFAULT_TIMEZONE)
	protected Date lastModifiedAt;
}