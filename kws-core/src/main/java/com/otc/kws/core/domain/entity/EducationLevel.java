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

import com.otc.kws.core.domain.constant.value.MasterStatusValue;

import lombok.Data;

@Entity
@Table(name = "core_m_education_level")
@Data
public class EducationLevel extends BaseKwsEntity
{
	@Id
	@Column(name = "id")
	protected String id;
	
	@Column(name = "root_id")
	protected String rootId;
	
	@Column(name = "parent_id")
	protected String parentId;
	
	@Column(name = "hierachy_level")
	protected int hierachyLevel;
	
	@Column(name = "school_type_id")
	protected String schoolTypeId;
	
	@Column(name = "name")
	protected String name;
	
	@Column(name = "class_title")
	protected String classTitle;
	
	@Column(name = "class_title_abbr")
	protected String classTitleAbbr;
	
	@Column(name = "class_levels")
	protected String classLevels;
	
	@Column(name = "description")
	protected String description;
	
	@Column(name = "seq_no")
	protected int seqNo;
	
	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	protected MasterStatusValue status;
	
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