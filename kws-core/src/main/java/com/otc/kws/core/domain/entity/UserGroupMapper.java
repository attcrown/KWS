package com.otc.kws.core.domain.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "sys_m_user_group_mapper")
@IdClass(UserGroupMapper.CompositeId.class)
@Data
public class UserGroupMapper extends BaseKwsEntity
{
	@Id
	@Column(name = "user_id")
	protected String userId;
	
	@Id
	@Column(name = "user_group_id")
	protected String userGroupId;
	
	@Column(name = "mapped_by")
	protected String mappedBy;
	
	@Column(name = "mapped_at")
	@Temporal(TemporalType.TIMESTAMP)
	protected Date mappedAt;
	
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class CompositeId implements Serializable
	{
		protected String userId;
		protected String userGroupId;
	}
}