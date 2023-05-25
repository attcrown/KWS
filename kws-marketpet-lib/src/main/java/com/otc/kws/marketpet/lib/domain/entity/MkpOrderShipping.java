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

import com.otc.kws.core.domain.constant.value.MasterStatusValue;

import lombok.Data;

@Entity
@Table(name = "mkp_order_shipping")
@Data
public class MkpOrderShipping extends BaseKwsMarketPetEntity
{
	@Id
	@Column(name = "id")
	protected String id;
	
	@Column(name = "order_id")
	protected String orderId;
	
	@Column(name = "customer_id")
	protected String customerId;
	
	@Column(name = "shipping_provider_id")
	protected String shippingProviderId;
	
	@Column(name = "tracking_no")
	protected String trackingNo;
	
	@Column(name = "recipient_name")
	protected String recipientName;
	
	@Column(name = "recipient_mobile_no")
	protected String recipientMobileNo;
	
	@Column(name = "address_info")
	protected String addressInfo;
	
	@Column(name = "country_id")
	protected String countryId;
	
	@Column(name = "country_name")
	protected String countryName;
	
	@Column(name = "province_id")
	protected String provinceId;
	
	@Column(name = "province_name")
	protected String provinceName;
	
	@Column(name = "amphur_id")
	protected String amphurId;
	
	@Column(name = "amphur_name")
	protected String amphurName;
	
	@Column(name = "district_id")
	protected String districtId;
	
	@Column(name = "district_name")
	protected String districtName;
	
	@Column(name = "post_code")
	protected String postCode;
	
	@Column(name = "location_lat")
	protected Double locationLat;
	
	@Column(name = "location_lon")
	protected Double locationLon;
	
	@Column(name = "remark")
	protected String remark;
	
	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	protected MasterStatusValue status;
	
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