package com.otc.kws.core.domain.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.otc.kws.core.domain.constant.value.EducationStatusValue;

import lombok.Data;

@Entity
@Table(name = "core_m_user_education_info")
@Data
public class UserEducationInfo extends BaseKwsEntity
{
	@Id
	@Column(name = "id")
	protected String id;
	
	@Column(name = "user_id")
	protected String userId;
	
	@Column(name = "school_id")
	protected String schoolId;
	
	@Column(name = "school_name")
	protected String schoolName;
	
	@Column(name = "education_status_id")
	@Enumerated(EnumType.STRING)
	protected EducationStatusValue educationStatusId;
	
	@Column(name = "education_level_id")
	protected String educationLevelId;
	
	@Column(name = "education_degree_id")
	protected String educationDegreeId;
	
	@Column(name = "education_major_id")
	protected String educationMajorId;
	
	@Column(name = "start_year")
	protected String startYear;
	
	@Column(name = "graduate_year")
	protected String graduateYear;
	
	@Column(name = "avg_grade")
	protected Double averageGrade;
	
	@Column(name = "remark")
	protected String remark;
	
	@Column(name = "cert_image")
	protected String certImage;
	
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