package com.otc.kws.firstjobber.lib.domain.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.otc.kws.core.domain.constant.KwsConst;

import lombok.Data;

@Entity
@Table(name = "fjb_t_applicant_job_register")
@Data
public class ApplicantJobRegister extends BaseKwsFirstJobberEntity
{
	@Id
	@Column(name = "id")
	protected String id;
	
	@Column(name = "applicant_id")
	protected String applicantId;
	
	@Column(name = "job_id")
	protected String jobId;
	
	@Column(name = "seq_no")
	protected int seqNo;
	
	@Column(name = "skill_description")
	protected String skillDescription;
	
	@Column(name = "experience_description")
	protected String experienceDescription;
	
	@Column(name = "portfolio_file_uri")
	protected String portfolioFileURI;
	
	@Column(name = "skill_level_id")
	protected String skillLevelId;
	
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
}