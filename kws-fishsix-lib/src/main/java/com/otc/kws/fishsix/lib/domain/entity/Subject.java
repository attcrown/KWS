package com.otc.kws.fishsix.lib.domain.entity;

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
@Table(name = "fs_m_subject")
@Data
public class Subject extends BaseKwsFishsixEntity
{
	@Id
	@Column(name = "id")
	protected String id;
	
	@Column(name = "subject_category_id")
	protected String subjectCategoryId;
	
	@Column(name = "name")
	protected String name;
	
	@Column(name = "title")
	protected String title;
	
	@Column(name = "description")
	protected String description;
	
	@Column(name = "education_class_id")
	protected String educationClassId;
	
	@Column(name = "education_level_id")
	protected String educationLevelId;
	
	@Column(name = "class_level")
	protected int classLevel;
	
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