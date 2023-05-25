package com.otc.kws.firstjobber.lib.domain.entity;

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
import com.otc.kws.firstjobber.lib.domain.constant.value.InternshipRegisterStatusValue;

import lombok.Data;

@Entity
@Table(name = "fjb_t_internship_register")
@Data
public class InternshipRegister extends BaseKwsFirstJobberEntity
{
	@Id
	@Column(name = "id")
	protected String id;
	
	@Column(name = "applicant_id")
	protected String applicantId;
	
	@Column(name = "user_id")
	protected String userId;
	
	@Column(name = "gender")
	@Enumerated(EnumType.STRING)
	protected GenderValue gender;
	
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
	
	@Column(name = "email")
	protected String email;
	
	@Column(name = "line_id")
	protected String lineId;
	
	@Column(name = "contact_address_id")
	protected String contactAddressId;
	
	@Column(name = "contact_address_detail")
	protected String contactAddressDetail;
	
	@Column(name = "contact_address_district_id")
	protected String contactAddressDistrictId;
	
	@Column(name = "contact_address_district_name")
	protected String contactAddressDistrictName;
	
	@Column(name = "contact_address_amphur_id")
	protected String contactAddressAmphurId;
	
	@Column(name = "contact_address_amphur_name")
	protected String contactAddressAmphurName;
	
	@Column(name = "contact_address_province_id")
	protected String contactAddressProvinceId;
	
	@Column(name = "contact_address_province_name")
	protected String contactAddressProvinceName;
	
	@Column(name = "contact_address_postcode")
	protected String contactAddressPostcode;
	
	@Column(name = "contact_person_name")
	protected String contactPersonName;
	
	@Column(name = "contact_person_relationship_id")
	protected String contactPersonRelationshipId;
	
	@Column(name = "contact_person_relationship_other")
	protected String contactPersonRelationshipOther;
	
	@Column(name = "contact_person_mobile_no")
	protected String contactPersonMobileNo;
	
	@Column(name = "education_status_id")
	protected String educationStatusId;
	
	@Column(name = "education_level_id")
	protected String educationLevelId;
	
	@Column(name = "education_level_other")
	protected String educationLevelOther;
	
	@Column(name = "education_class")
	protected String educationClass;
	
	@Column(name = "education_faculty_id")
	protected String educationFacultyId;
	
	@Column(name = "education_faculty_other")
	protected String educationFacultyOther;
	
	@Column(name = "education_major_id")
	protected String educationMajorId;
	
	@Column(name = "education_major_other")
	protected String educationMajorOther;
	
	@Column(name = "school_id")
	protected String schoolId;
	
	@Column(name = "school_other")
	protected String schoolOther;
	
	@Column(name = "gpa")
	protected String gpa;
	
	@Column(name = "internship_type_id")
	protected String internshipTypeId;
	
	@Column(name = "request_ack_date")
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = KwsConst.DEFAULT_DATE_FORMAT, timezone = KwsConst.DEFAULT_TIMEZONE)
	protected Date requestAckDate;
	
	@Column(name = "request_start_date")
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = KwsConst.DEFAULT_DATE_FORMAT, timezone = KwsConst.DEFAULT_TIMEZONE)
	protected Date requestStartDate;
	
	@Column(name = "request_end_date")
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = KwsConst.DEFAULT_DATE_FORMAT, timezone = KwsConst.DEFAULT_TIMEZONE)
	protected Date requestEndDate;
	
	@Column(name = "request_job1_id")
	protected String requestJob1Id;
	
	@Column(name = "request_job1_other")
	protected String requestJob1Other;
	
	@Column(name = "request_job2_id")
	protected String requestJob2Id;
	
	@Column(name = "request_job2_other")
	protected String requestJob2Other;
	
	@Column(name = "request_job3_id")
	protected String requestJob3Id;
	
	@Column(name = "request_job3_other")
	protected String requestJob3Other;
	
	@Column(name = "request_dayoff_num")
	protected int requestDayOffNum;
	
	@Column(name = "skill")
	protected String skill;
	
	@Column(name = "request_location")
	protected String requestLocation;
	
	@Column(name = "formal_photo_uri")
	protected String formalPhotoURI;
	
	@Column(name = "informal_photo_uri")
	protected String informalPhotoURI;
	
	@Column(name = "idcard_file_uri")
	protected String idcardFileURI;
	
	@Column(name = "house_particular_file_uri")
	protected String houseParticularFileURI;
	
	@Column(name = "educational_qualification_file_uri")
	protected String educationalQualificationFileURI;
	
	@Column(name = "bookbank_file_uri")
	protected String bookbankFileURI;
	
	@Column(name = "resume_file_uri")
	protected String resumeFileURI;
	
	@Column(name = "intern_letter_file_uri")
	protected String internLetterFileURI;
	
	@Column(name = "intern_cert_file_uri")
	protected String internCertFileURI;
	
	@Column(name = "portfolio1_file_uri")
	protected String portfolio1FileURI;
	
	@Column(name = "portfolio2_file_uri")
	protected String portfolio2FileURI;
	
	@Column(name = "portfolio3_file_uri")
	protected String portfolio3FileURI;
	
	@Column(name = "portfolio4_file_uri")
	protected String portfolio4FileURI;
	
	@Column(name = "portfolio5_file_uri")
	protected String portfolio5FileURI;
	
	@Column(name = "portfolio_description")
	protected String portfolioDescription;
	
	@Column(name = "register_status_id")
	@Enumerated(EnumType.STRING)
	protected InternshipRegisterStatusValue registerStatusId;
	
	@Column(name = "registered_by")
	protected String registeredBy;
	
	@Column(name = "registered_at")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = KwsConst.DEFAULT_DATETIME_FORMAT, timezone = KwsConst.DEFAULT_TIMEZONE)
	protected Date registeredAt;
	
	@Column(name = "registered_remark")
	protected String registeredRemark;
	
	@Column(name = "upload_test_by")
	protected String uploadTestBy;
	
	@Column(name = "upload_test_at")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = KwsConst.DEFAULT_DATETIME_FORMAT, timezone = KwsConst.DEFAULT_TIMEZONE)
	protected Date uploadTestAt;
	
	@Column(name = "upload_test_remark")
	protected String uploadTestRemark;
	
	@Column(name = "reviewed_by")
	protected String reviewedBy;
	
	@Column(name = "reviewed_at")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = KwsConst.DEFAULT_DATETIME_FORMAT, timezone = KwsConst.DEFAULT_TIMEZONE)
	protected Date reviewedAt;
	
	@Column(name = "reviewed_remark")
	protected String reviewedRemark;
	
	@Column(name = "rejected_by")
	protected String rejectedBy;
	
	@Column(name = "rejected_at")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = KwsConst.DEFAULT_DATETIME_FORMAT, timezone = KwsConst.DEFAULT_TIMEZONE)
	protected Date rejectedAt;
	
	@Column(name = "rejected_remark")
	protected String rejectedRemark;
	
	@Column(name = "accepted_by")
	protected String acceptedBy;
	
	@Column(name = "accepted_at")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = KwsConst.DEFAULT_DATETIME_FORMAT, timezone = KwsConst.DEFAULT_TIMEZONE)
	protected Date acceptedAt;
	
	@Column(name = "accepted_remark")
	protected String acceptedRemark;
}