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
@Table(name = "mkp_product_brand")
@Data
public class MkpProductBrand extends BaseKwsMarketPetEntity
{
	@Id
	@Column(name = "id")
	protected String id;
	
	@Column(name = "name")
	protected String name;
	
	@Column(name = "description")
	protected String description;
	
	@Column(name = "remark")
	protected String remark;
	
	@Column(name = "thumbnail_image_uri")
	protected String thumbnailImageURI;
	
	@Column(name = "full_image_uri")
	protected String fullImageURI;
	
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