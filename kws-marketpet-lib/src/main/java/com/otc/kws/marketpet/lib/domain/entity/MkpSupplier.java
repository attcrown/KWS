package com.otc.kws.marketpet.lib.domain.entity;

import java.math.BigDecimal;
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
@Table(name = "mkp_supplier")
@Data
public class MkpSupplier extends BaseKwsMarketPetEntity
{
	@Id
	@Column(name = "id")
	protected String id;
	
	@Column(name = "name")
	protected String name;
	
	@Column(name = "regist_no")
	protected String registNo;
	
	@Column(name = "mobile_no")
	protected String mobileNo;
	
	@Column(name = "email")
	protected String email;
	
	@Column(name = "line_id")
	protected String lineId;
	
	@Column(name = "facebook_page")
	protected String facebookPage;
	
	@Column(name = "description")
	protected String description;
	
	@Column(name = "remark")
	protected String remark;
	
	@Column(name = "contact_person_name")
	protected String contactPersonName;
	
	@Column(name = "contact_person_mobile_no")
	protected String contactPersonMobileNo;
	
	@Column(name = "contact_person_email")
	protected String contactPersonEmail;
	
	@Column(name = "is_admit_stock_dealership")
	protected boolean admitStockDealership;
	
	@Column(name = "is_admit_dropship")
	protected boolean admitDropship;
	
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