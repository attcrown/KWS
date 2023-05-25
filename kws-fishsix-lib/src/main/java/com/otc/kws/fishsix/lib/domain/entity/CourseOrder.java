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
import com.otc.kws.fishsix.lib.domain.constant.value.CourseOrderStatusValue;

import lombok.Data;

@Entity
@Table(name = "fs_t_course_order")
@Data
public class CourseOrder extends BaseKwsFishsixEntity
{
	@Id
	@Column(name = "id")
	protected String id;
	
	@Column(name = "course_id")
	protected String courseId;
	
	@Column(name = "student_id")
	protected String studentId;
	
	@Column(name = "salesman_id")
	protected String salesmanId;
	
	@Column(name = "selected_subject_ids")
	protected String selectedSubjectIds;
	
	@Column(name = "reserve_teacher_id")
	protected String reserveTeacherId;
	
	@Column(name = "order_status_id")
	@Enumerated(EnumType.STRING)
	protected CourseOrderStatusValue orderStatusId;
	
	@Column(name = "ordered_by")
	protected String orderedBy;
	
	@Column(name = "ordered_at")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = KwsConst.DEFAULT_DATETIME_FORMAT, timezone = KwsConst.DEFAULT_TIMEZONE)
	protected Date orderedAt;
	
	@Column(name = "ordered_remark")
	protected String orderedRemark;
	
	@Column(name = "cancelled_by")
	protected String cancelledBy;
	
	@Column(name = "cancelled_at")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = KwsConst.DEFAULT_DATETIME_FORMAT, timezone = KwsConst.DEFAULT_TIMEZONE)
	protected Date cancelledAt;
	
	@Column(name = "cancelled_remark")
	protected String cancelledRemark;
	
	@Column(name = "rejected_by")
	protected String rejectedBy;
	
	@Column(name = "rejected_at")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = KwsConst.DEFAULT_DATETIME_FORMAT, timezone = KwsConst.DEFAULT_TIMEZONE)
	protected Date rejectedAt;
	
	@Column(name = "rejected_remark")
	protected String rejectedRemark;
	
	@Column(name = "approved_by")
	protected String approvedBy;
	
	@Column(name = "approved_at")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = KwsConst.DEFAULT_DATETIME_FORMAT, timezone = KwsConst.DEFAULT_TIMEZONE)
	protected Date approvedAt;
	
	@Column(name = "approved_remark")
	protected String approvedRemark;

	@Column(name = "payment_amount")
	protected BigDecimal paymentAmount;

	@Column(name = "payment_to_bank_account")
	protected String paymentToBankAccount;

	@Column(name = "payment_slip_file_uri")
	protected String paymentSlipFileURI;

	@Column(name = "payment_slip_base64")
	protected String paymentSlipBase64;
	
	@Column(name = "receipt_file_uri")
	protected String receiptFileURI;

	@Column(name = "receipt_base64")
	protected String receiptBase64;

	@Column(name = "payment_at")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = KwsConst.DEFAULT_DATETIME_FORMAT, timezone = KwsConst.DEFAULT_TIMEZONE)
	protected Date paymentAt;

	@Column(name = "payment_remark")
	protected String paymentRemark;
}