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

import lombok.Data;

@Entity
@Table(name = "fs_t_class_schedule")
@Data
public class StudyClassSchedule extends BaseKwsFishsixEntity
{
	@Id
	@Column(name = "id")
	protected String id;
	
	@Column(name = "class_id")
	protected String classId;
	
	@Column(name = "teacher_id")
	protected String teacherId;
	
	@Column(name = "student_id")
	protected String studentId;
	
	@Column(name = "subject_id")
	protected String subjectId;
	
	@Column(name = "subject_chapter_id")
	protected String subjectChapterId;
	
	@Column(name = "class_status")
	@Enumerated(EnumType.STRING)
	protected ClassStatus classStatus;
	
	@Column(name = "class_reserved_by")
	protected String classReservedBy;
	
	@Column(name = "class_reserved_at")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = KwsConst.DEFAULT_DATETIME_FORMAT, timezone = KwsConst.DEFAULT_TIMEZONE)
	protected Date classReservedAt;
	
	@Column(name = "class_reserved_remark")
	protected String classReservedRemark;
	
	@Column(name = "class_cancelled_by")
	protected String classCancelledBy;
	
	@Column(name = "class_cancelled_at")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = KwsConst.DEFAULT_DATETIME_FORMAT, timezone = KwsConst.DEFAULT_TIMEZONE)
	protected Date classCancelledAt;
	
	@Column(name = "class_cancelled_remark")
	protected String classCancelledRemark;
	
	@Column(name = "class_confirmed_by")
	protected String classConfirmedBy;
	
	@Column(name = "class_confirmed_at")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = KwsConst.DEFAULT_DATETIME_FORMAT, timezone = KwsConst.DEFAULT_TIMEZONE)
	protected Date classConfirmedAt;
	
	@Column(name = "class_confirmed_remark")
	protected String classConfirmedRemark;
	
	@Column(name = "class_completed_by")
	protected String classCompletedBy;
	
	@Column(name = "class_completed_at")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = KwsConst.DEFAULT_DATETIME_FORMAT, timezone = KwsConst.DEFAULT_TIMEZONE)
	protected Date classCompletedAt;
	
	@Column(name = "class_completed_remark")
	protected String classCompletedRemark;
	
	@Column(name = "student_checkin_status")
	@Enumerated(EnumType.STRING)
	protected StudentCheckinStatus studentCheckinStatus;
	
	@Column(name = "student_checkin_by")
	protected String studentCheckinBy;
	
	@Column(name = "student_checkin_at")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = KwsConst.DEFAULT_DATETIME_FORMAT, timezone = KwsConst.DEFAULT_TIMEZONE)
	protected Date studentCheckinAt;
	
	@Column(name = "student_checkin_remark")
	protected String studentCheckinRemark;
	
	@Column(name = "student_course_quota_id")
	protected String studentCourseQuotaId;
	
	@Column(name = "student_quota_status")
	@Enumerated(EnumType.STRING)
	protected StudentQuotaStatus studentQuotaStatus;
	
	@Column(name = "teacher_quota_status")
	@Enumerated(EnumType.STRING)
	protected TeacherQuotaStatus teacherQuotaStatus;
	
	@Column(name = "teacher_wage_hour_rate")
	protected BigDecimal teacherWageHourRate;
	
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
	
	
	public static enum ClassStatus
	{
		Reserved, Cancelled, Confirmed, Completed
	}
	
	
	public static enum StudentCheckinStatus
	{
		Absent, Present, Leave
	}
	
	
	public static enum StudentQuotaStatus
	{
		Reserved, Used, Returned
	}
	
	
	public static enum TeacherQuotaStatus
	{
		Pending, Cancelled, Acquired
	}
}