package com.otc.kws.firstjobber.lib.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import com.otc.kws.core.domain.constant.value.MasterStatusValue;

import lombok.Data;

@Entity
@Table(name = "fjb_t_internship_register_experience")
@Data
public class FjbInternshipRegisterExperience extends BaseKwsFirstJobberEntity
{
	@Id
	@Column(name = "id")
	protected String id;
	
	@Column(name = "internship_register_id")
	protected String internshipRegisterId;
	
	@Column(name = "seq_no")
	protected int seqNo;
	
	@Column(name = "experience_info")
	protected String experienceInfo;
	
	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	protected MasterStatusValue status;
}