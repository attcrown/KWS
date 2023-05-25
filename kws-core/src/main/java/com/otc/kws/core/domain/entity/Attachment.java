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

import lombok.Data;

@Entity
@Table(name = "core_m_attachment")
@Data
public class Attachment extends BaseKwsEntity
{
	@Id
	@Column(name = "id")
	protected String id;
	
	@Column(name = "owner_type")
	protected String ownerType;
	
	@Column(name = "owner_ref_id")
	protected String ownerRefId;
	
	@Column(name = "resource_category")
	protected String resourceCategory;
	
	@Column(name = "resource_type")
	@Enumerated(EnumType.STRING)
	protected ResourceType resourceType;
	
	@Column(name = "resource_extension")
	protected String resourceExtension;
	
	@Column(name = "resource_uri")
	protected String resourceURI;
	
	@Column(name = "seq_no")
	protected int seqNo;
	
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
	
	
	public static enum ResourceType
	{
		Image, File
	}
}