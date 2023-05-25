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
import com.otc.kws.core.domain.constant.value.MasterStatusValue;
import com.otc.kws.core.domain.constant.value.PlatformValue;

import lombok.Data;

@Entity
@Table(name = "sys_m_menu")
@Data
public class Menu extends BaseKwsEntity
{
	@Id
	@Column(name = "id")
	protected String id;
	
	@Column(name = "module")
	protected String module;
	
	@Column(name = "platform")
	@Enumerated(EnumType.STRING)
	protected PlatformValue platform;
	
	@Column(name = "item_type")
	@Enumerated(EnumType.STRING)
	protected ItemType itemType;
	
	@Column(name = "hierachy_type")
	@Enumerated(EnumType.STRING)
	protected HierachyType hierachyType;
	
	@Column(name = "hierachy_level")
	protected int hierachyLevel;
	
	@Column(name = "root_item_id")
	protected String rootItemId;
	
	@Column(name = "parent_item_id")
	protected String parentItemId;
	
	@Column(name = "seq_no")
	protected int seqNo;
	
	@Column(name = "title")
	protected String title;
	
	@Column(name = "description")
	protected String description;
	
	@Column(name = "program_id")
	protected String programId;
	
	@Column(name = "default_program_id")
	protected String defaultProgramId;
	
	@Column(name = "is_authorize")
	protected boolean authorize;
	
	@Column(name = "is_default_open_menu_dropdown")
	protected boolean defaultOpenMenuDropdown;
	
	@Column(name = "icon_type")
	@Enumerated(EnumType.STRING)
	protected IconType iconType;
	
	@Column(name = "icon_image_uri")
	protected String iconImageURI;
	
	@Column(name = "icon_font_class")
	protected String iconFontClass;
	
	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	protected MasterStatusValue status;
	
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
	
	
	public static enum ItemType
	{
		Section, MenuDropdown, MenuItem
	}
	
	
	public static enum HierachyType
	{
		Root, Middle, Leaf
	}
	
	
	public static enum IconType
	{
		Image, AwesomeFont
	}
}