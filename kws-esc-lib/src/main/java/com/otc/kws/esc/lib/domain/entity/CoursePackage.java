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
import com.otc.kws.esc.lib.domain.constant.value.CoursePackageTypeValue;

import lombok.Data;

@Entity
@Table(name = "course_package")
@Data
public class CoursePackage extends BaseKwsEscEntity
{
	@Id
	@Column(name = "id")
	protected String id;
	
	@Column(name = "course_id")
	protected String courseId;
	
	@Column(name = "course_package_type_id")
	@Enumerated(EnumType.STRING)
	protected CoursePackageTypeValue coursePackageTypeId;
	
	@Column(name = "times_limit")
	protected int timesLimit;
	
	@Column(name = "compensate_limt")
	protected String compensateLimit;
	
	@Column(name = "price")
	protected double price;
	
	@Column(name = "remark")
	protected String remark;
	
	@Column(name = "quota_available_after_reserved")
	protected int quotaAvailableAfterReserved;
	
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