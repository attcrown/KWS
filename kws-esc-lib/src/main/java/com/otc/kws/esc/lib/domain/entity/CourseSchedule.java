package com.otc.kws.esc.lib.domain.entity;

import java.sql.Time;
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
import com.otc.kws.esc.lib.domain.constant.value.CourseChannelValue;
import com.otc.kws.esc.lib.domain.constant.value.DayOfWeekValue;

import lombok.Data;

@Entity
@Table(name = "course_schedule")
@Data
public class CourseSchedule extends BaseKwsEscEntity
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
	
	@Column(name = "start_time")
	protected Time startTime;
	
	@Column(name = "end_time")
	protected Time endTime;
	
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