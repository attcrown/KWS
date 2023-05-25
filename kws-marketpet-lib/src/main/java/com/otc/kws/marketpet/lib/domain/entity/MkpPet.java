package com.otc.kws.marketpet.lib.domain.entity;

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
@Table(name = "mkp_pet")
@Data
public class MkpPet extends BaseKwsMarketPetEntity
{
	@Id
	@Column(name = "id")
	protected String id;
	
	@Column(name = "name")
	protected String name;
	
	@Column(name = "appearance_info")
	protected String appearance;
	
	@Column(name = "character_info")
	protected String character;
	
	@Column(name = "description")
	protected String description;
	
	@Column(name = "remark")
	protected String remark;
	
	@Column(name = "pet_category_id")
	protected String petCategoryId;
	
	@Column(name = "pet_type_id")
	protected String petTypeId;
	
	@Column(name = "customer_id")
	protected String customerId;
	
	@Column(name = "birth_date")
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = KwsConst.DEFAULT_DATE_FORMAT, timezone = KwsConst.DEFAULT_TIMEZONE)
	protected Date birthDate;
	
	@Column(name = "profile_image_uri")
	protected String profileImageURI;
	
	@Column(name = "is_alive")
	protected boolean alive;
	
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