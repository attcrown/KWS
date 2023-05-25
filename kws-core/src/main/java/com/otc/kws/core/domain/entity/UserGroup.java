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
@Table(name = "sys_m_user_group")
@Data
public class UserGroup extends BaseKwsEntity
{
	@Id
	@Column(name = "id")
	protected String id;
	
	@Column(name = "code")
	protected String code;
	
	@Column(name = "name")
	protected String name;
	
	@Column(name = "seq_no")
	protected int seqNo;
	
	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	protected MasterStatusValue status;
}