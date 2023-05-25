package com.otc.kws.esc.lib.domain.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Entity
@Table(name = "course_student_quota")
@Data
public class CourseStudentQuota extends BaseKwsEscEntity
{
	@Id
	@Column(name = "id")
	protected String id;
	
	@Column(name = "student_id")
	protected String studentId;
	
	@Column(name = "course_id")
	protected String courseId;
	
	@Column(name = "course_package_id")
	protected String coursePackageId;
	
	@Column(name = "course_order_id")
	protected String courseOrderId;
	
	@Column(name = "course_order_item_id")
	protected String courseOrderItemId;
	
	@Column(name = "times_limit")
	protected int timesLimit;
	
	@Column(name = "times_remain")
	protected int timesRemain;
	
	@Column(name = "compensate_limit")
	protected int compensateLimit;
	
	@Column(name = "compensate_remain")
	protected int compensateRemain;
	
	@Column(name = "start_date")
	@Temporal(TemporalType.DATE)
	protected Date startDate;
	
	@Column(name = "expired_date")
	@Temporal(TemporalType.DATE)
	protected Date expiredDate;
	
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