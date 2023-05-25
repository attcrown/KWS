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

import lombok.Data;

@Entity
@Table(name = "fs_t_class_evaluation")
@Data
public class StudyClassEvaluation extends BaseKwsFishsixEntity
{
	@Id
	@Column(name = "id")
	protected String id;
	
	@Column(name = "class_schedule_id")
	protected String classScheduleId;
	
	@Column(name = "evaluate_from_type")
	@Enumerated(EnumType.STRING)
	protected EvaluatorType evaluateFromType;
	
	@Column(name = "evaluate_from_ref_id")
	protected String evaluateFromRefId;
	
	@Column(name = "evaluate_to_type")
	@Enumerated(EnumType.STRING)
	protected EvaluatorType evaluateToType;
	
	@Column(name = "evaluate_to_ref_id")
	protected String evaluateToRefId;
	
	@Column(name = "comment")
	protected String comment;
	
	@Column(name = "evaluate_status")
	@Enumerated(EnumType.STRING)
	protected EvaluateStatus evaluateStatus;
	
	@Column(name = "evaluate_rejected_by")
	protected String evaluateRejectedBy;
	
	@Column(name = "evaluate_rejected_at")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = KwsConst.DEFAULT_DATETIME_FORMAT, timezone = KwsConst.DEFAULT_TIMEZONE)
	protected Date evaluateRejectedAt;
	
	@Column(name = "evaluate_rejected_remark")
	protected String evaluateRejectedRemark;
	
	@Column(name = "evaluate_approved_by")
	protected String evaluateApprovedBy;
	
	@Column(name = "evaluate_approved_at")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = KwsConst.DEFAULT_DATETIME_FORMAT, timezone = KwsConst.DEFAULT_TIMEZONE)
	protected Date evaluateApprovedAt;
	
	@Column(name = "evaluate_approved_remark")
	protected String evaluateApprovedRemark;
	
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
	
	
	public static enum EvaluatorType
	{
		Teacher, Student
	}
	
	
	public static enum EvaluateStatus
	{
		Pending, Rejected, Approved
	}
}