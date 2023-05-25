package com.otc.kws.esc.lib.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import com.otc.kws.core.domain.constant.value.MasterStatusValue;
import com.otc.kws.esc.lib.domain.constant.value.CourseChannelValue;
import com.otc.kws.esc.lib.domain.constant.value.DayOfWeekValue;

import lombok.Data;

@Entity
@Table(name = "course_schedule_day")
@Data
public class CourseScheduleDay extends BaseKwsEscEntity
{
	@Id
	@Column(name = "id")
	protected String id;
	
	@Column(name = "course_channel_id")
	@Enumerated(EnumType.STRING)
	protected CourseChannelValue courseChannelId;
	
	@Column(name = "day")
	@Enumerated(EnumType.STRING)
	protected DayOfWeekValue day;
	
	@Column(name = "seq_no")
	protected int seqNo;
	
	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	protected MasterStatusValue status;
}