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
import com.otc.kws.core.domain.constant.value.FileStorageTypeValue;

import lombok.Data;

@Entity
@Table(name = "sys_t_filestore_log")
@Data
public class FileStoreLog extends BaseKwsEntity
{
	@Id
	@Column(name = "id")
	protected String id;
	
	@Column(name = "module")
	protected String module;
	
	@Column(name = "file_category")
	protected String fileCategory;
	
	@Column(name = "file_group")
	protected String fileGroup;
	
	@Column(name = "seq_no")
	protected int seqNo;
	
	@Column(name = "storage_type")
	@Enumerated(EnumType.STRING)
	protected FileStorageTypeValue storageType;
	
	@Column(name = "scheme")
	protected String scheme;
	
	@Column(name = "file_uri")
	protected String fileURI;
	
	@Column(name = "original_file_name")
	protected String originalFileName;
	
	@Column(name = "file_content_type")
	protected String fileContentType;
	
	@Column(name = "file_size")
	protected int fileSize;
	
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