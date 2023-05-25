package com.otc.kws.core.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import com.otc.kws.core.domain.constant.value.FileStorageTypeValue;

import lombok.Data;

@Entity
@Table(name = "sys_m_filestore_config")
@Data
public class FileStoreConfig extends BaseKwsEntity
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
	
	@Column(name = "storage_type")
	@Enumerated(EnumType.STRING)
	protected FileStorageTypeValue storageType;
	
	@Column(name = "scheme")
	protected String scheme;
	
	@Column(name = "file_uri")
	protected String fileURI;
	
	@Column(name = "allow_file_type")
	protected String allowFileType;
	
	@Column(name = "allow_file_extension")
	protected String allowFileExtension;
	
	@Column(name = "max_length")
	protected int maxLength;
	
	@Column(name = "max_size_per_file")
	protected int maxSizePerFile;
	
	@Column(name = "description")
	protected String description;
	
	@Column(name = "remark")
	protected String remark;
}