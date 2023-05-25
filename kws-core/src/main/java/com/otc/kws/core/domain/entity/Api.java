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

import com.otc.kws.core.domain.constant.value.ApiStatusValue;

import lombok.Data;

@Entity
@Table(name = "sys_m_api")
@Data
public class Api extends BaseKwsEntity
{
	@Id
	@Column(name = "id")
	protected String id;
	
	@Column(name = "module")
	protected String module;
	
	@Column(name = "method")
	protected String method;
	
	@Column(name = "path")
	protected String path;
	
	@Column(name = "class_name")
	protected String className;
	
	@Column(name = "should_authen")
	protected boolean shouldAuthen;
	
	@Column(name = "should_authorize")
	protected boolean shouldAuthorize;
	
	@Column(name = "description")
	protected String description;
	
	@Column(name = "remark")
	protected String remark;
	
	@Column(name = "success_status")
	protected String successStatus;
	
	@Column(name = "error_status")
	protected String errorStatus;
	
	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	protected ApiStatusValue status;
	
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