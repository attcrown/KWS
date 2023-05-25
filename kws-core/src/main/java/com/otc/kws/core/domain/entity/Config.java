package com.otc.kws.core.domain.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.otc.kws.core.domain.constant.value.ConfigTypeValue;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "sys_m_config")
@IdClass(Config.CompositeId.class)
@Data
public class Config extends BaseKwsEntity
{
	@Id
	@Column(name = "module")
	protected String module;
	
	@Id
	@Column(name = "category")
	protected String category;
	
	@Id
	@Column(name = "name")
	protected String name;
	
	@Column(name = "value")
	protected String value;
	
	@Column(name = "type")
	@Enumerated(EnumType.STRING)
	protected ConfigTypeValue type;
	
	@Column(name = "description")
	protected String description;
	
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class CompositeId implements Serializable
	{
		protected String module;
		protected String category;
		protected String name;
	}
}