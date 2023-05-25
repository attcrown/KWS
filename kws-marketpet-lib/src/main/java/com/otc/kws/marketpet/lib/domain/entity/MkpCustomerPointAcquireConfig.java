package com.otc.kws.marketpet.lib.domain.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import com.otc.kws.core.domain.constant.value.MasterStatusValue;

import lombok.Data;

@Entity
@Table(name = "mkp_customer_point_acquire_config")
@Data
public class MkpCustomerPointAcquireConfig extends BaseKwsMarketPetEntity
{
	@Id
	@Column(name = "id")
	protected String id;
	
	@Column(name = "hierachy_level")
	protected int hierachyLevel;
	
	@Column(name = "acquired_percentage")
	protected BigDecimal acquiredPercentage;
	
	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	protected MasterStatusValue status;
}