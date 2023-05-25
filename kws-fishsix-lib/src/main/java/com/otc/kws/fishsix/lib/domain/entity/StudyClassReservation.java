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
@Table(name = "fs_t_class_reservation")
@Data
public class StudyClassReservation extends BaseKwsFishsixEntity
{
	@Id
	@Column(name = "id")
	protected String id;
	
	@Column(name = "class_id")
	protected String classId;
	
	@Column(name = "requestor_type")
	@Enumerated(EnumType.STRING)
	protected RequestorType requestorType;
	
	@Column(name = "requestor_ref_id")
	protected String requestorRefId;
	
	@Column(name = "reserve_teacher_id")
	protected String reserveTeacherId;
	
	@Column(name = "request_status")
	@Enumerated(EnumType.STRING)
	protected RequestStatus requestStatus;
	
	@Column(name = "requested_by")
	protected String requestedBy;
	
	@Column(name = "requested_at")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = KwsConst.DEFAULT_DATETIME_FORMAT, timezone = KwsConst.DEFAULT_TIMEZONE)
	protected Date requestedAt;
	
	@Column(name = "requested_remark")
	protected String requestedRemark;
	
	@Column(name = "approved_by")
	protected String approvedBy;
	
	@Column(name = "approved_at")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = KwsConst.DEFAULT_DATETIME_FORMAT, timezone = KwsConst.DEFAULT_TIMEZONE)
	protected Date approvedAt;
	
	@Column(name = "approved_remark")
	protected String approvedRemark;
	
	@Column(name = "rejected_by")
	protected String rejectedBy;
	
	@Column(name = "rejected_at")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = KwsConst.DEFAULT_DATETIME_FORMAT, timezone = KwsConst.DEFAULT_TIMEZONE)
	protected Date rejectedAt;
	
	@Column(name = "rejected_remark")
	protected String rejectedRemark;
	
	
	public static enum RequestorType
	{
		Teacher, Student, Admin
	}
	
	
	public static enum RequestStatus
	{
		Pending, Approved, Rejected
	}
}