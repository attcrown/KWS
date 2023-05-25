package com.otc.kws.esc.lib.domain.entity;

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
import com.otc.kws.esc.lib.domain.constant.value.CourseChannelValue;

import lombok.Data;

@Entity
@Table(name = "course")
@Data
public class Course extends BaseKwsEscEntity
{
	@Id
	@Column(name = "id")
	protected String id;
	
	@Column(name = "code")
	protected String code;
	
	@Column(name = "name")
	protected String name;
	
	@Column(name = "course_type_id")
	protected String course_type_id;
	
	@Column(name = "course_channel_id")
	@Enumerated(EnumType.STRING)
	protected CourseChannelValue courseChannelId;
	
	@Column(name = "education_level_id")
	protected String educationLevelId;
	
	@Column(name = "description")
	protected String description;
	
	@Column(name = "background")
	protected String background;
	
	@Column(name = "objective")
	protected String objective;
	
	@Column(name = "achievement")
	protected String achievement;
	
	@Column(name = "remark")
	protected String remark;
	
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
	protected Date lastModifieddAt;
}