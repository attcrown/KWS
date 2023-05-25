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
import com.otc.kws.core.domain.constant.value.GenderValue;

import lombok.Data;

@Entity
@Table(name = "fs_t_student_register")
@Data
public class StudentRegister extends BaseKwsFishsixEntity
{
	@Id
	@Column(name = "id")
	protected String id;
	
	@Column(name = "email")
	protected String email;
	
	@Column(name = "password")
	protected String password;
	
	@Column(name = "gender_id")
	@Enumerated(EnumType.STRING)
	protected GenderValue genderId;
	
	@Column(name = "first_name")
	protected String firstName;
	
	@Column(name = "last_name")
	protected String lastName;
	
	@Column(name = "nick_name")
	protected String nickName;
	
	@Column(name = "birth_date")
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = KwsConst.DEFAULT_DATE_FORMAT, timezone = KwsConst.DEFAULT_TIMEZONE)
	protected Date birthDate;
	
	@Column(name = "mobile_no")
	protected String mobileNo;
	
	@Column(name = "contact_address")
	protected String contactAddress;
	
	@Column(name = "education_school")
	protected String educationSchool;
	
	@Column(name = "education_level")
	protected String educationLevel;
	
	@Column(name = "parent_name")
	protected String parentName;
	
	@Column(name = "parent_mobile_no")
	protected String parentMobileNo;
	
	@Column(name = "parent_job")
	protected String parentJob;
	
	@Column(name = "parent_expectation")
	protected String parentExpectation;
	
	@Column(name = "favour_teacher_style")
	protected String favourTeacherStyle;
	
	@Column(name = "background")
	protected String background;
	
	@Column(name = "remark")
	protected String remark;
	
	@Column(name = "register_status")
	@Enumerated(EnumType.STRING)
	protected RegisterStatus registerStatus;
	
	@Column(name = "registered_at")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = KwsConst.DEFAULT_DATETIME_FORMAT, timezone = KwsConst.DEFAULT_TIMEZONE)
	protected Date registeredAt;
	
	@Column(name = "approved_by")
	protected String approvedBy;
	
	@Column(name = "approved_remark")
	protected String approvedRemark;
	
	@Column(name = "approved_at")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = KwsConst.DEFAULT_DATETIME_FORMAT, timezone = KwsConst.DEFAULT_TIMEZONE)
	protected Date approvedAt;
	
	@Column(name = "rejected_by")
	protected String rejectedBy;
	
	@Column(name = "rejected_remark")
	protected String rejectedRemark;
	
	@Column(name = "rejected_at")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = KwsConst.DEFAULT_DATETIME_FORMAT, timezone = KwsConst.DEFAULT_TIMEZONE)
	protected Date rejectedAt;
	
	
	public static enum RegisterStatus
	{
		Pending, Approved, Rejected
	}
}