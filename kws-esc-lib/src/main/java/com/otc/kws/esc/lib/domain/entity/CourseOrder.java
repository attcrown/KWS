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

import com.otc.kws.esc.lib.domain.constant.value.CourseOrderStatusValue;

import lombok.Data;

@Entity
@Table(name = "course_order")
@Data
public class CourseOrder extends BaseKwsEscEntity
{
	@Id
	@Column(name = "id")
	protected String id;
	
	@Column(name = "student_id")
	protected String studentId;
	
	@Column(name = "ordered_by")
	protected String orderedBy;
	
	@Column(name = "ordered_at")
	@Temporal(TemporalType.TIMESTAMP)
	protected Date orderedAt;
	
	@Column(name = "order_status_id")
	@Enumerated(EnumType.STRING)
	protected CourseOrderStatusValue orderStatusId;
	
	@Column(name = "payment_type")
	protected String payment_type;
	
	@Column(name = "payment_to_bank_account")
	protected String paymentToBankAccount;
	
	@Column(name = "payment_amount")
	protected Double paymentAmount;
	
	@Column(name = "payment_at")
	@Temporal(TemporalType.TIMESTAMP)
	protected Date paymentAt;
	
	@Column(name = "canceled_by")
	protected String canceledBy;
	
	@Column(name = "canceled_at")
	@Temporal(TemporalType.TIMESTAMP)
	protected Date canceledAt;
	
	@Column(name = "verified_by")
	protected String verifiedBy;
	
	@Column(name = "verified_at")
	@Temporal(TemporalType.TIMESTAMP)
	protected Date verifiedAt;
	
	@Column(name = "rejected_by")
	protected String rejectedBy;
	
	@Column(name = "rejected_at")
	@Temporal(TemporalType.TIMESTAMP)
	protected Date rejectedAt;
	
	@Column(name = "accepted_by")
	protected String acceptedBy;
	
	@Column(name = "accepted_at")
	@Temporal(TemporalType.TIMESTAMP)
	protected Date acceptedAt;
	
	@Column(name = "remark")
	protected String remark;
	
	@Column(name = "tags")
	protected String tags;
}