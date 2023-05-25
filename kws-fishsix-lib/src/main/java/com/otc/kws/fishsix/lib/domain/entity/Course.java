package com.otc.kws.fishsix.lib.domain.entity;

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
@Table(name = "fs_m_course")
@Data
public class Course extends BaseKwsFishsixEntity
{
	@Id
	@Column(name = "id")
	protected String id;
	
	@Column(name = "class_channel_id")
	protected String classChannelId;
	
	@Column(name = "class_type_id")
	protected String classTypeId;
	
	@Column(name = "name")
	protected String name;
	
	@Column(name = "description")
	protected String description;
	
	@Column(name = "hour")
	protected int hour;
	
	@Column(name = "extra_hour")
	protected int extraHour;
	
	@Column(name = "price")
	protected BigDecimal price;
	
	@Column(name = "discount_price")
	protected BigDecimal discountPrice;
	
	@Column(name = "expiration_period")
	protected String expirationPeriod;
	
	@Column(name = "max_subject")
	protected int maxSubject;
	
	@Column(name = "thumbnail_image_file_uri")
	protected String thumbnailImageFileURI;
	
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