package com.otc.kws.fishsix.lib.domain.entity;

import java.sql.Time;
import java.time.DayOfWeek;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.otc.kws.core.domain.constant.KwsConst;
import com.otc.kws.core.domain.constant.value.MasterStatusValue;

import lombok.Data;

@Entity
@Table(name = "fs_m_class_schedule_template")
@Data
public class StudyClassScheduleTemplate extends BaseKwsFishsixEntity
{
	@Id
	@Column(name = "id")
	protected String id;
	
	@Column(name = "class_name")
	protected String className;
	
	@Column(name = "day")
	@Enumerated(EnumType.STRING)
	protected DayOfWeek day;
	
	@Column(name = "start_date")
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = KwsConst.DEFAULT_DATE_FORMAT, timezone = KwsConst.DEFAULT_TIMEZONE)
	protected Date startDate;
	
	@Column(name = "start_time")
	@JsonFormat(pattern = KwsConst.DEFAULT_TIME_FORMAT, timezone = KwsConst.DEFAULT_TIMEZONE)
	protected Time startTime;
	
	@Column(name = "end_time")
	@JsonFormat(pattern = KwsConst.DEFAULT_TIME_FORMAT, timezone = KwsConst.DEFAULT_TIMEZONE)
	protected Time endTime;
	
	@Column(name = "repeatable")
	protected boolean repeatable;
	
	@Column(name = "hour")
	protected int hour;
	
	@Column(name = "max_student")
	protected int maxStudent;
	
	@Column(name = "class_channel_id")
	protected String classChannelId;
	
	@Column(name = "class_type_id")
	protected String classTypeId;
	
	@Column(name = "class_location_id")
	protected String classLocationId;
	
	@Column(name = "description")
	protected String description;
	
	@Column(name = "remark")
	protected String remark;
	
	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	protected MasterStatusValue status;
	
	@Column(name = "created_by")
	protected String createdBy;
	
	@Column(name = "created_at")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = KwsConst.DEFAULT_DATETIME_FORMAT, timezone = KwsConst.DEFAULT_TIMEZONE)
	protected Date createdAt;
	
	@Column(name = "last_modified_by")
	protected String lastModifiedBy;
	
	@Column(name = "last_modified_at")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = KwsConst.DEFAULT_DATETIME_FORMAT, timezone = KwsConst.DEFAULT_TIMEZONE)
	protected Date lastModifiedAt;
}