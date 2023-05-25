package com.otc.kws.marketpet.lib.domain.entity;

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
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.otc.kws.core.domain.constant.KwsConst;
import com.otc.kws.core.domain.constant.value.GenderValue;
import com.otc.kws.marketpet.lib.domain.constant.value.MkpCustomerActivatedChannelValue;
import com.otc.kws.marketpet.lib.domain.constant.value.MkpCustomerRegisterStatusValue;

import lombok.Data;

@Entity
@Table(name = "mkp_customer_register")
@Data
public class MkpCustomerRegister extends BaseKwsMarketPetEntity
{
	@Id
	@Column(name = "id")
	protected String id;
	
	@Column(name = "customer_id")
	protected String customerId;
	
	@Column(name = "user_id")
	protected String userId;
	
	@Column(name = "invited_customer_id")
	protected String invitedCustomerId;
	
	@Column(name = "email")
	protected String email;
	
	@Column(name = "password")
	@JsonIgnore
	protected String password;
	
	@Column(name = "gender")
	@Enumerated(EnumType.STRING)
	protected GenderValue gender;
	
	@Column(name = "first_name")
	protected String firstName;
	
	@Column(name = "last_name")
	protected String lastName;
	
	@Column(name = "birth_date")
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = KwsConst.DEFAULT_DATE_FORMAT, timezone = KwsConst.DEFAULT_TIMEZONE)
	protected Date birthDate;
	
	@Column(name = "mobile_no")
	protected String mobileNo;
	
	@Column(name = "activated_channel")
	@Enumerated(EnumType.STRING)
	protected MkpCustomerActivatedChannelValue activatedChannel;
	
	@Column(name = "register_status")
	@Enumerated(EnumType.STRING)
	protected MkpCustomerRegisterStatusValue registerStatus;
	
	@Column(name = "registered_at")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = KwsConst.DEFAULT_DATETIME_FORMAT, timezone = KwsConst.DEFAULT_TIMEZONE)
	protected Date registeredAt;
	
	@Column(name = "activated_at")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = KwsConst.DEFAULT_DATETIME_FORMAT, timezone = KwsConst.DEFAULT_TIMEZONE)
	protected Date activatedAt;
	
	@Column(name = "completed_at")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = KwsConst.DEFAULT_DATETIME_FORMAT, timezone = KwsConst.DEFAULT_TIMEZONE)
	protected Date completedAt;
}