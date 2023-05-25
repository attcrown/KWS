package com.otc.kws.fishsix.lib.domain.entity;

import java.sql.Time;
import java.time.DayOfWeek;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.otc.kws.core.domain.constant.KwsConst;
import com.otc.kws.core.domain.constant.value.MasterStatusValue;

import lombok.Data;

@Entity
@Table(name = "fs_m_class_period")
@Data
public class StudyClassPeriod extends BaseKwsFishsixEntity
{
	@Id
	@Column(name = "id")
	protected String id;
	
	@Column(name = "day")
	@Enumerated(EnumType.STRING)
	protected DayOfWeek day;
	
	@Column(name = "start_time")
	@JsonFormat(pattern = KwsConst.DEFAULT_TIME_FORMAT, timezone = KwsConst.DEFAULT_TIMEZONE)
	protected Time startTime;
	
	@Column(name = "end_time")
	@JsonFormat(pattern = KwsConst.DEFAULT_TIME_FORMAT, timezone = KwsConst.DEFAULT_TIMEZONE)
	protected Time endTime;
	
	@Column(name = "remark")
	protected String remark;
	
	@Column(name = "seq_no")
	protected int seqNo;
	
	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	protected MasterStatusValue status;
}