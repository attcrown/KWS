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
@Table(name = "fs_t_teacher_hour_acquired")
@Data
public class TeacherHourAcquired extends BaseKwsFishsixEntity
{
	@Id
	@Column(name = "id")
	protected String id;
	
	@Column(name = "teacher_id")
	protected String teacherId;
	
	@Column(name = "class_schedule_id")
	protected String classScheduleId;
	
	@Column(name = "acquired_method")
	@Enumerated(EnumType.STRING)
	protected AcquiredMethod acquiredMethod;
	
	@Column(name = "acquired_from_course_quota_id")
	protected String acquiredFromCourseQuotaId;
	
	@Column(name = "acquired_from_student_id")
	protected String acquiredFromStudentId;
	
	@Column(name = "acquired_hour")
	protected int acquiredHour;
	
	@Column(name = "acquired_amount")
	protected BigDecimal acquiredAmount;
	
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
	
	
	public static enum AcquiredMethod
	{
		Educate, Cancel
	}
}