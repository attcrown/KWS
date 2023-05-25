package com.otc.kws.esc.lib.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.otc.kws.core.domain.constant.value.MasterStatusValue;

import lombok.Data;

@Entity
@Table(name = "course_location_type")
@Data
public class CourseLocationType extends BaseKwsEscEntity
{
	@Id
	@Column(name = "id")
	protected String id;
	
	@Column(name = "name")
	protected String name;
	
	@Column(name = "remark")
	protected String remark;
	
	@Column(name = "seq_no")
	protected String seqNo;
	
	@Column(name = "status")
	protected MasterStatusValue status;
}