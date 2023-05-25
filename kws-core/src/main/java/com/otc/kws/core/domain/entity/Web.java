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

import com.fasterxml.jackson.annotation.JsonFormat;
import com.otc.kws.core.domain.constant.KwsConst;
import com.otc.kws.core.domain.constant.value.WebStatusValue;

import lombok.Data;

@Entity
@Table(name = "sys_m_web")
@Data
public class Web extends BaseKwsEntity
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
	
	@Column(name = "view_file")
	protected String viewFile;
	
	@Column(name = "controller_class")
	protected String controllerClass;
	
	@Column(name = "page_title")
	protected String pageTitle;
	
	@Column(name = "page_description")
	protected String pageDescription;
	
	/*
	@Column(name = "active_menus")
	protected String activeMenus;
	
	@Column(name = "breadcrumbs")
	protected String breadcrumbs;
	*/
	
	@Column(name = "should_authen")
	protected boolean shouldAuthen;
	
	@Column(name = "should_authorize")
	protected boolean shouldAuthorize;
	
	@Column(name = "is_workspace")
	protected boolean workspace;
	
	@Column(name = "description")
	protected String description;
	
	@Column(name = "remark")
	protected String remark;
	
	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	protected WebStatusValue status;
	
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