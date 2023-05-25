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
@Table(name = "core_m_high5test_category")
@Data
public class High5TestCategory extends BaseKwsEntity
{
	@Id
	@Column(name = "id")
	protected String id;
	
	@Column(name = "title")
	protected String title;
	
	@Column(name = "description")
	protected String description;
	
	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	protected MasterStatusValue status;
}