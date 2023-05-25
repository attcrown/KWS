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
import com.otc.kws.core.domain.constant.value.GenderValue;
import com.otc.kws.core.domain.constant.value.MasterStatusValue;

import lombok.Data;

@Entity
@Table(name = "sys_m_user_personal_info")
@Data
public class UserPersonalInfo extends BaseKwsEntity
{
	@Id
	@Column(name = "user_id")
	protected String userId;
	
	@Column(name = "gender")
	@Enumerated(EnumType.STRING)
	protected GenderValue gender;
	
	@Column(name = "prefix_name_id")
	protected String prefixNameId;
	
	@Column(name = "first_name")
	protected String firstName;
	
	@Column(name = "last_name")
	protected String lastName;
	
	@Column(name = "nick_name")
	protected String nickName;
	
	@Column(name = "birth_date")
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = KwsConst.DEFAULT_DATE_FORMAT, timezone = KwsConst.DEFAULT_TIMEZONE)
	protected Date birthDate;
	
	@Column(name = "id_card_no")
	protected String idCardNo;
	
	@Column(name = "id_card_file_uri")
	protected String idCardFileURI;
	
	@Column(name = "nation_id")
	protected String nationId;
	
	@Column(name = "race_id")
	protected String raceId;
	
	@Column(name = "religion_id")
	protected String religionId;
	
	@Column(name = "mobile_no")
	protected String mobileNo;
	
	@Column(name = "line_id")
	protected String lineId;
	
	@Column(name = "regist_address_id")
	protected String registAddressId;
	
	@Column(name = "regist_address_text")
	protected String registAddressText;
	
	@Column(name = "contact_address_id")
	protected String contactAddressId;
	
	@Column(name = "contact_address_text")
	protected String contactAddressText;
	
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
}