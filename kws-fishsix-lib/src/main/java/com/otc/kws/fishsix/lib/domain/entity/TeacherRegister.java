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
import com.otc.kws.core.domain.constant.value.GenderValue;

import lombok.Data;

@Entity
@Table(name = "fs_t_teacher_register")
@Data
public class TeacherRegister extends BaseKwsFishsixEntity
{
	@Id
	@Column(name = "id")
	protected String id;
	
	@Column(name = "gender_id")
	@Enumerated(EnumType.STRING)
	protected GenderValue genderId;
	
	@Column(name = "name_title_id")
	protected String nameTitleId;
	
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
	
	@Column(name = "email")
	protected String email;
	
	@Column(name = "password")
	protected String password;
	
	@Column(name = "idcard_no")
	protected String idcardNo;
	
	@Column(name = "idcard_file_base64")
	protected String idcardFileBase64;
	
	@Column(name = "resume_file_base64")
	protected String resumeFileBase64;
	
	@Column(name = "mobile_no")
	protected String mobileNo;
	
	@Column(name = "regist_address")
	protected String registAddress;
	
	@Column(name = "contact_address")
	protected String contactAddress;
	
	@Column(name = "education_school")
	protected String educationSchool;
	
	@Column(name = "education_faculty")
	protected String educationFaculty;
	
	@Column(name = "education_major")
	protected String educationMajor;
	
	@Column(name = "subject_ids")
	protected String subjectIds;
	
	@Column(name = "occupation")
	protected String occupation;
	
	@Column(name = "bank_account_no")
	protected String bankAccountNo;
	
	@Column(name = "bank_account_name")
	protected String bankAccountName;
	
	@Column(name = "bank_account_branch")
	protected String bankAccountBranch;
	
	@Column(name = "bank_account_image_base64")
	protected String bankAccountImageBase64;
	
	@Column(name = "start_work_date")
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = KwsConst.DEFAULT_DATE_FORMAT, timezone = KwsConst.DEFAULT_TIMEZONE)
	protected Date startWorkDate;
	
	@Column(name = "hire_type_id")
	protected String hireTypeId;
	
	@Column(name = "worktime_type_id")
	protected String worktimeTypeId;
	
	@Column(name = "contract_file_base64")
	protected String contractFileBase64;
	
	@Column(name = "salary")
	protected BigDecimal salary;
	
	@Column(name = "wage_hour_rate")
	protected BigDecimal wageHourRate;
	
	@Column(name = "work_hour_month")
	protected Integer workHourMonth;
	
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