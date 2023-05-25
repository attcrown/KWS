package com.otc.kws.core.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "core_m_province")
@Data
public class Province extends BaseKwsEntity
{
	@Id
	@Column(name = "id")
	protected String id;
	
	@Column(name = "country_id")
	protected String countryId;
	
	@Column(name = "code")
	protected String code;
	
	@Column(name = "name_th")
	protected String nameTH;
	
	@Column(name = "name_en")
	protected String nameEN;
}