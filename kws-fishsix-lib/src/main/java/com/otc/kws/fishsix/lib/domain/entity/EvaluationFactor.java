package com.otc.kws.fishsix.lib.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import com.otc.kws.core.domain.constant.value.MasterStatusValue;

import lombok.Data;

@Entity
@Table(name = "fs_m_evaluation_factor")
@Data
public class EvaluationFactor extends BaseKwsFishsixEntity
{
	@Id
	@Column(name = "id")
	protected String id;
	
	@Column(name = "type")
	@Enumerated(EnumType.STRING)
	protected Type type;
	
	@Column(name = "title")
	protected String title;
	
	@Column(name = "min_score")
	protected Integer minScore;
	
	@Column(name = "max_score")
	protected Integer maxScore;
	
	@Column(name = "remark")
	protected String remark;
	
	@Column(name = "seq_no")
	protected int seqNo;
	
	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	protected MasterStatusValue status;
	
	
	public static enum Type
	{
		Text, Score
	}
}