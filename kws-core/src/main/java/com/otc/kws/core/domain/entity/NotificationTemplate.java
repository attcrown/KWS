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

import com.otc.kws.core.domain.constant.value.MasterStatusValue;

import lombok.Data;

@Entity
@Table(name = "sys_m_notification_template")
@Data
public class NotificationTemplate extends BaseKwsEntity
{
	@Id
	@Column(name = "id")
	protected String id;
	
	@Column(name = "notification_id")
	protected String notificationId;
	
	@Column(name = "channel_id")
	protected String channelId;
	
	@Column(name = "channel_ref_id")
	protected String channelRefId;
	
	@Column(name = "name")
	protected String name;
	
	@Column(name = "event")
	protected String event;

	@Enumerated(EnumType.STRING)
	protected ContentType contentType;
	
	@Column(name = "title")
	protected String title;
	
	@Column(name = "content")
	protected String content;
	
	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	protected MasterStatusValue status;
	
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
	
	
	public static enum ContentType
	{
		Text, Image
	}
}