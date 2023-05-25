package com.otc.kws.core.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import com.otc.kws.core.domain.constant.value.MasterStatusValue;

import lombok.Data;

@Entity
@Table(name = "core_m_education_class_level")
@Data
public class EducationClassLevel extends BaseKwsEntity
{
	@Id
	@Column(name = "id")
	protected String id;
	
	@Column(name = "education_level_id")
	protected String educationLevelId;
	
	@Column(name = "class_level")
	protected int classLevel;
	
	@Column(name = "title")
	protected String title;
	
	@Column(name = "title_abbr")
	protected String titleAbbr;
	
	@Column(name = "seq_no")
	protected int seqNo;
	
	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	protected MasterStatusValue status;
}