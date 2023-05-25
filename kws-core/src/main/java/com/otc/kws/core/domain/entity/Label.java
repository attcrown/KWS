package com.otc.kws.core.domain.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.otc.kws.core.domain.constant.value.MasterStatusValue;
import com.otc.kws.core.domain.constant.value.PlatformValue;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "sys_m_label")
@IdClass(Label.CompositeId.class)
@Data
public class Label extends BaseKwsEntity
{
	@Id
	@Column(name = "module")
	protected String module;
	
	@Id
	@Column(name = "screen")
	protected String screen;
	
	@Id
	@Column(name = "platform")
	@Enumerated(EnumType.STRING)
	protected PlatformValue platform;
	
	@Id
	@Column(name = "code")
	protected String code;
	
	@Id
	@Column(name = "language")
	protected String language;
	
	@Column(name = "type")
	protected String type;
	
	@Column(name = "value")
	protected String value;
	
	@Column(name = "seq_no")
	protected int seqNo;
	
	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	protected MasterStatusValue status;
	
	@Column(name = "description")
	protected String description;
	
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class CompositeId implements Serializable
	{
		protected String module;
		protected String screen;
		protected PlatformValue platform;
		protected String code;
		protected String language;
	}
}