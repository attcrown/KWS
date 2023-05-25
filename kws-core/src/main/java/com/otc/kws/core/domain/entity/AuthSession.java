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

import com.otc.kws.core.domain.constant.value.AuthSessionStatusValue;
import com.otc.kws.core.domain.constant.value.PlatformValue;

import lombok.Data;

@Entity
@Table(name = "sys_t_auth_session")
@Data
public class AuthSession extends BaseKwsEntity
{
	@Id
	@Column(name = "id")
	protected String id;
	
	@Column(name = "platform")
	@Enumerated(EnumType.STRING)
	protected PlatformValue platform;
	
	@Column(name = "user_id")
	protected String userId;
	
	@Column(name = "access_token")
	protected String accessToken;
	
	@Column(name = "session_id")
	protected String sessionId;
	
	@Column(name = "label_language_id")
	protected String labelLanguageId;
	
	@Column(name = "user_profile_info")
	protected String userProfileInfo;
	
	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	protected AuthSessionStatusValue status;
	
	@Column(name = "created_at")
	@Temporal(TemporalType.TIMESTAMP)
	protected Date createdAt;
	
	@Column(name = "last_verified_at")
	@Temporal(TemporalType.TIMESTAMP)
	protected Date lastVerifiedAt;
	
	@Column(name = "last_accessed_at")
	@Temporal(TemporalType.TIMESTAMP)
	protected Date lastAccessedAt;
	
	@Column(name = "expired_at")
	@Temporal(TemporalType.TIMESTAMP)
	protected Date expiredAt;
}