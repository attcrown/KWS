package com.otc.kws.marketpet.lib.domain.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import com.otc.kws.marketpet.lib.domain.constant.value.MkpCostTypeValue;
import com.otc.kws.marketpet.lib.domain.constant.value.MkpSaleTypeValue;

import lombok.Data;

@Entity
@Table(name = "mkp_order_item")
@Data
public class MkpOrderItem extends BaseKwsMarketPetEntity
{
	@Id
	@Column(name = "id")
	protected String id;
	
	@Column(name = "order_id")
	protected String orderId;
	
	@Column(name = "seq_no")
	protected int seqNo;
	
	@Column(name = "product_id")
	protected String productId;
	
	@Column(name = "product_code")
	protected String productCode;
	
	@Column(name = "product_stock_id")
	protected String productStockId;
	
	@Column(name = "cost_type")
	@Enumerated(EnumType.STRING)
	protected MkpCostTypeValue costType;
	
	@Column(name = "sale_type")
	@Enumerated(EnumType.STRING)
	protected MkpSaleTypeValue saleType;
	
	@Column(name = "unit_cost_price")
	protected BigDecimal unitCostPrice;
	
	@Column(name = "unit_sale_price")
	protected BigDecimal unitSalePrice;
	
	@Column(name = "unit_discount_price")
	protected BigDecimal unitDiscountPrice;
	
	@Column(name = "unit_score")
	protected int unitScore;
	
	@Column(name = "volume")
	protected int volume;
	
	@Column(name = "remark")
	protected String remark;
}