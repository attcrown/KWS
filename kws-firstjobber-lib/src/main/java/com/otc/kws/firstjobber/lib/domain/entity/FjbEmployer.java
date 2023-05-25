package com.otc.kws.firstjobber.lib.domain.entity;

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

import lombok.Data;

@Entity
@Table(name = "fjb_m_employer")
@Data
public class FjbEmployer extends BaseKwsFirstJobberEntity
{
	@Id
	@Column(name = "id")
	protected String id;
	
	@Column(name = "employer_type_id")
	protected String employerTypeId;
	
	@Column(name = "name")
	protected String name;
	
	@Column(name = "biz_type")
	protected String bizType;
	
	@Column(name = "biz_background")
	protected String bizBackground;
	
	@Column(name = "regist_no")
	protected String registNo;
	
	@Column(name = "tel_no")
	protected String telNo;
	
	@Column(name = "fax_no")
	protected String faxNo;
	
	@Column(name = "email")
	protected String email;
	
	@Column(name = "website")
	protected String website;
	
	@Column(name = "facebook_page_url")
	protected String facebookPageURL;
	
	@Column(name = "contact_address_id")
	protected String contactAddressId;
	
	@Column(name = "logo_image_uri")
	protected String logoImageURI;
	
	@Column(name = "map_image_uri")
	protected String mapImageURI;
	
	@Column(name = "remark")
	protected String remark;
	
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