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
@Table(name = "fjb_t_internship_register_test")
@Data
public class FjbInternshipRegisterTest extends BaseKwsFirstJobberEntity
{
	@Id
	@Column(name = "id")
	protected String id;
	
	@Column(name = "internship_register_id")
	protected String internshipRegisterId;
	
	@Column(name = "sixteen_personality_category_id")
	protected String sixteenPersonalityCategoryId;
	
	@Column(name = "sixteen_personality_image_uri")
	protected String sixteenPersonalityImageURI;
	
	@Column(name = "high5_category_id_1")
	protected String high5CategoryId1;
	
	@Column(name = "high5_category_id_2")
	protected String high5CategoryId2;
	
	@Column(name = "high5_category_id_3")
	protected String high5CategoryId3;
	
	@Column(name = "high5_category_id_4")
	protected String high5CategoryId4;
	
	@Column(name = "high5_category_id_5")
	protected String high5CategoryId5;
	
	@Column(name = "high5_image_uri")
	protected String high5ImageURI;
	
	@Column(name = "iq_score")
	protected int iqScore;
	
	@Column(name = "iq_image_uri")
	protected String iqImageURI;
	
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