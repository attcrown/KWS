package com.otc.kws.esc.lib.domain.entity;

import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import com.otc.kws.core.domain.constant.value.MasterStatusValue;
import com.otc.kws.esc.lib.domain.constant.value.CourseChannelValue;

import lombok.Data;

@Entity
@Table(name = "course_schedule_time")
@Data
public class CourseScheduleTime extends BaseKwsEscEntity
{
	@Id
	@Column(name = "id")
	protected String id;
	
	@Column(name = "course_channel_id")
	@Enumerated(EnumType.STRING)
	protected CourseChannelValue courseChannelId;
	
	@Column(name = "start_time")
	protected Time startTime;
	
	@Column(name = "end_time")
	protected Time endTime;
	
	@Column(name = "seq_no")
	protected int seqNo;
	
	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	protected MasterStatusValue status;
}