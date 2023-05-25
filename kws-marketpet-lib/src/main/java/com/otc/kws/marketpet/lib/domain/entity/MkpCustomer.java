package com.otc.kws.marketpet.lib.domain.entity;

import java.math.BigDecimal;
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
import com.otc.kws.core.domain.constant.value.MasterStatusValue;

import lombok.Data;

@Entity
@Table(name = "mkp_customer")
@Data
public class MkpCustomer extends BaseKwsMarketPetEntity
{
	@Id
	@Column(name = "id")
	protected String id;
	
	@Column(name = "user_id")
	protected String userId;
	
	@Column(name = "line_hierachy_level")
	protected int lineHierachyLevel;
	
	@Column(name = "root_line_id")
	protected String rootLineId;
	
	@Column(name = "parent_line_id")
	protected String parentLineId;
	
	@Column(name = "username")
	protected String username;
	
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
	
	@Column(name = "nick_name")
	protected String nickName;
	
	@Column(name = "birth_date")
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = KwsConst.DEFAULT_DATE_FORMAT, timezone = KwsConst.DEFAULT_TIMEZONE)
	protected Date birthDate;
	
	@Column(name = "mobile_no")
	protected String mobileNo;
	
	@Column(name = "email")
	protected String email;
	
	@Column(name = "line_id")
	protected String line_id;
	
	@Column(name = "line_user_id")
	protected String lineUserId;
	
	@Column(name = "address_id")
	protected String addressId;
	
	@Column(name = "profile_image_uri")
	protected String profileImageURI;
	
	@Column(name = "accum_amount")
	protected BigDecimal accumAmount;
	
	@Column(name = "remain_amount")
	protected BigDecimal remainAmount;
	
	@Column(name = "point")
	protected BigDecimal point;
	
	@Column(name = "score")
	protected int score;
	
	@Column(name = "shipping_address_id_1")
	protected String shippingAddressId1;
	
	@Column(name = "shipping_address_id_2")
	protected String shippingAddressId2;
	
	@Column(name = "remark")
	protected String remark;
	
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