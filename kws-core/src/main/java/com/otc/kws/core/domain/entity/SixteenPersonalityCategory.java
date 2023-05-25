package com.otc.kws.core.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import com.otc.kws.core.domain.constant.value.MasterStatusValue;

import lombok.Data;

@Entity
@Table(name = "core_m_sixteen_personality_category")
@Data
public class SixteenPersonalityCategory extends BaseKwsEntity
{
	@Id
	@Column(name = "id")
	protected String id;
	
	@Column(name = "personality_type_id")
	protected String personalityTypeId;
	
	@Column(name = "code")
	protected String code;
	
	@Column(name = "name")
	protected String name;
	
	@Column(name = "caption")
	protected String caption;
	
	@Column(name = "avatar_image_url")
	protected String avatarImageURL;
	
	@Column(name = "web_url")
	protected String webURL;
	
	@Column(name = "remark")
	protected String remark;
	
	@Column(name = "seq_no")
	protected int seqNo;
	
	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	protected MasterStatusValue status;
}