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

import com.otc.kws.core.domain.constant.value.AuthPermissionValue;
import com.otc.kws.core.domain.constant.value.PrincipalTypeValue;
import com.otc.kws.core.domain.constant.value.ResourceTypeValue;

import lombok.Data;

@Entity
@Table(name = "sys_m_auth_privilege")
@Data
public class AuthPrivilege extends BaseKwsEntity
{
	@Id
	@Column(name = "id")
	protected String id;
	
	@Column(name = "auth_policy_id")
	protected String authPolicyId;
	
	@Column(name = "resource_type")
	@Enumerated(EnumType.STRING)
	protected ResourceTypeValue resourceType;
	
	@Column(name = "resource_ref_id")
	protected String resourceRefId;
	
	@Column(name = "resource_action")
	protected String resourceAction;
	
	@Column(name = "principal_type")
	@Enumerated(EnumType.STRING)
	protected PrincipalTypeValue principalType;
	
	@Column(name = "principal_ref_id")
	protected String principalRefId;
	
	@Column(name = "permission")
	@Enumerated(EnumType.STRING)
	protected AuthPermissionValue permission;
	
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
}