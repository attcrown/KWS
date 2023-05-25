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
import com.otc.kws.core.domain.constant.KwsConst;
import com.otc.kws.core.domain.constant.value.MasterStatusValue;

import lombok.Data;

@Entity
@Table(name = "mkp_customer_point_acquire")
@Data
public class MkpCustomerPointAcquire extends BaseKwsMarketPetEntity
{
	@Id
	@Column(name = "id")
	protected String id;
	
	@Column(name = "acquire_channel")
	@Enumerated(EnumType.STRING)
	protected AcquireChannel acquireChannel;
	
	@Column(name = "register_id")
	protected String registerId;
	
	@Column(name = "order_id")
	protected String orderId;
	
	@Column(name = "customer_id")
	protected String customerId;
	
	@Column(name = "acquire_from_customer_id")
	protected String acquireFromCustomerId;
	
	@Column(name = "acquire_hierachy_level")
	protected int acquireHierachyLevel;
	
	@Column(name = "price")
	protected BigDecimal price;
	
	@Column(name = "remain_amount")
	protected BigDecimal remainAmount;
	
	@Column(name = "point")
	protected BigDecimal point;
	
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
	
	
	public static enum AcquireChannel
	{
		Register, Purchase
	}
}