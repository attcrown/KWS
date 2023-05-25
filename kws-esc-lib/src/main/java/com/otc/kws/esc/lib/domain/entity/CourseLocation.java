package com.otc.kws.esc.lib.domain.entity;

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
import com.otc.kws.esc.lib.domain.constant.value.CourseLocationTypeValue;

import lombok.Data;

@Entity
@Table(name = "course_location")
@Data
public class CourseLocation extends BaseKwsEscEntity
{
	@Id
	@Column(name = "id")
	protected String id;
	
	@Column(name = "location_type_id")
	@Enumerated(EnumType.STRING)
	protected CourseLocationTypeValue locationId;
	
	@Column(name = "location_name")
	protected String locationName;
	
	@Column(name = "remark")
	protected String remark;
	
	@Column(name = "tel_no")
	protected String telNo;
	
	@Column(name = "fax_no")
	protected String faxNo;
	
	@Column(name = "email")
	protected String email;
	
	@Column(name = "address_id")
	protected String addressId;
	
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
	protected Date lastModifieddAt;
}