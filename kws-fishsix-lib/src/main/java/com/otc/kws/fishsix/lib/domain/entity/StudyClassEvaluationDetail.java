package com.otc.kws.fishsix.lib.domain.entity;

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
@Table(name = "fs_t_class_evaluation_detail")
@Data
public class StudyClassEvaluationDetail extends BaseKwsFishsixEntity
{
	@Id
	@Column(name = "id")
	protected String id;
	
	@Column(name = "class_evaluation_id")
	protected String classEvaluationId;
	
	@Column(name = "evaluation_factor_id")
	protected String evaluationFactorId;
	
	@Column(name = "evaluation_value")
	protected String evaluationValue;
	
	@Column(name = "remark")
	protected String remark;
	
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