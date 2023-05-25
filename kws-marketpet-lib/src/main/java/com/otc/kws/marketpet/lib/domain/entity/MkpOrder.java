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
import com.otc.kws.marketpet.lib.domain.constant.value.MkpOrderChannelValue;
import com.otc.kws.marketpet.lib.domain.constant.value.MkpOrderStatusValue;
import com.otc.kws.marketpet.lib.domain.constant.value.MkpPaymentStatusValue;
import com.otc.kws.marketpet.lib.domain.constant.value.MkpSaleStatusValue;

import lombok.Data;

@Entity
@Table(name = "mkp_order")
@Data
public class MkpOrder extends BaseKwsMarketPetEntity
{
	@Id
	@Column(name = "id")
	protected String id;
	
	@Column(name = "running_no")
	protected String runningNo;
	
	@Column(name = "customer_id")
	protected String customerId;
	
	@Column(name = "order_channel")
	@Enumerated(EnumType.STRING)
	protected MkpOrderChannelValue orderChannel;
	
	@Column(name = "order_by")
	protected String orderBy;
	
	@Column(name = "order_at")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = KwsConst.DEFAULT_DATETIME_FORMAT, timezone = KwsConst.DEFAULT_TIMEZONE)
	protected Date orderAt;
	
	@Column(name = "order_status_id")
	@Enumerated(EnumType.STRING)
	protected MkpOrderStatusValue orderStatusId;
	
	@Column(name = "order_cancelled_by")
	protected String orderCancelledBy;
	
	@Column(name = "order_cancelled_at")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = KwsConst.DEFAULT_DATETIME_FORMAT, timezone = KwsConst.DEFAULT_TIMEZONE)
	protected Date orderCancelledAt;
	
	@Column(name = "order_cancelled_remark")
	protected String orderCancelledRemark;
	
	@Column(name = "order_verified_by")
	protected String orderVerifiedBy;
	
	@Column(name = "order_verified_at")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = KwsConst.DEFAULT_DATETIME_FORMAT, timezone = KwsConst.DEFAULT_TIMEZONE)
	protected Date orderVerifiedAt;
	
	@Column(name = "order_verified_remark")
	protected String orderVerifiedRemark;
	
	@Column(name = "order_rejected_by")
	protected String orderRejectedBy;
	
	@Column(name = "order_rejected_at")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = KwsConst.DEFAULT_DATETIME_FORMAT, timezone = KwsConst.DEFAULT_TIMEZONE)
	protected Date orderRejectedAt;
	
	@Column(name = "order_rejected_remark")
	protected String orderRejectedRemark;
	
	@Column(name = "order_accepted_by")
	protected String orderAcceptedBy;
	
	@Column(name = "order_accepted_at")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = KwsConst.DEFAULT_DATETIME_FORMAT, timezone = KwsConst.DEFAULT_TIMEZONE)
	protected Date orderAcceptedAt;
	
	@Column(name = "order_accepted_remark")
	protected String orderAcceptedRemark;
	
	@Column(name = "total_quantity")
	protected String totalQuantity;
	
	@Column(name = "total_price")
	protected BigDecimal totalPrice;
	
	@Column(name = "total_product_discount")
	protected BigDecimal totalProductDiscount;
	
	@Column(name = "point_discount_volume")
	protected BigDecimal pointDiscountVolume;
	
	@Column(name = "point_discount_price")
	protected BigDecimal pointDiscountPrice;
	
	@Column(name = "bill_discount")
	protected BigDecimal billDiscount;
	
	@Column(name = "total_discount")
	protected BigDecimal totalDiscount;
	
	@Column(name = "vat")
	protected BigDecimal vat;
	
	@Column(name = "net_price")
	protected BigDecimal netPrice;
	
	@Column(name = "total_point")
	protected BigDecimal totalPoint;
	
	@Column(name = "total_score")
	protected int totalScore;
	
	@Column(name = "payment_status")
	@Enumerated(EnumType.STRING)
	protected MkpPaymentStatusValue paymentStatus;
	
	@Column(name = "payment_type_id")
	protected String paymentTypeId;
	
	@Column(name = "payment_to_bank_account_id")
	protected String paymentToBankAccountId;
	
	@Column(name = "payment_received_amount")
	protected BigDecimal paymentReceivedAmount;
	
	@Column(name = "payment_changed_amount")
	protected BigDecimal paymentChangedAmount;
	
	@Column(name = "payment_at")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = KwsConst.DEFAULT_DATETIME_FORMAT, timezone = KwsConst.DEFAULT_TIMEZONE)
	protected Date paymentAt;
	
	@Column(name = "sale_status")
	@Enumerated(EnumType.STRING)
	protected MkpSaleStatusValue saleStatus;
	
	@Column(name = "shipping_id")
	protected String shippingId;
	
	@Column(name = "sold_by")
	protected String soldBy;
	
	@Column(name = "sold_at")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = KwsConst.DEFAULT_DATETIME_FORMAT, timezone = KwsConst.DEFAULT_TIMEZONE)
	protected Date soldAt;
	
	@Column(name = "sold_remark")
	protected String soldRemark;
	
	@Column(name = "cancelled_by")
	protected String cancelledBy;
	
	@Column(name = "cancelled_at")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = KwsConst.DEFAULT_DATETIME_FORMAT, timezone = KwsConst.DEFAULT_TIMEZONE)
	protected Date cancelledAt;
	
	@Column(name = "cancelled_remark")
	protected String cancelledRemark;
}