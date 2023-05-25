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
@Table(name = "mkp_product")
@Data
public class MkpProduct extends BaseKwsMarketPetEntity
{
	@Id
	@Column(name = "id")
	protected String id;
	
	@Column(name = "code")
	protected String code;
	
	@Column(name = "supplier_id")
	protected String supplierId;
	
	@Column(name = "product_brand_id")
	protected String productBrandId;
	
	@Column(name = "product_category_id")
	protected String productCategoryId;
	
	@Column(name = "name")
	protected String name;
	
	@Column(name = "caption")
	protected String caption;
	
	@Column(name = "description")
	protected String description;
	
	@Column(name = "remark")
	protected String remark;
	
	@Column(name = "cost_price")
	protected BigDecimal costPrice;
	
	@Column(name = "dropship_cost_price")
	protected BigDecimal dropshipCostPrice;
	
	@Column(name = "wholesale_price")
	protected BigDecimal wholesalePrice;
	
	@Column(name = "retail_price")
	protected BigDecimal retailPrice;
	
	@Column(name = "discount_price")
	protected BigDecimal discountPrice;
	
	@Column(name = "discount_rate")
	protected BigDecimal discountRate;
	
	@Column(name = "score")
	protected int score;
	
	@Column(name = "balance_stock_volume")
	protected int balanceStockVolume;
	
	@Column(name = "thumbnail_image_uri")
	protected String thumbnailImageURI;
	
	@Column(name = "full_image_uri")
	protected String fullImageURI;
	
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