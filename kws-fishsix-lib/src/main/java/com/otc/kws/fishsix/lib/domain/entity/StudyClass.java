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

import lombok.Data;

@Entity
@Table(name = "fs_t_class")
@Data
public class StudyClass extends BaseKwsFishsixEntity
{
	@Id
	@Column(name = "id")
	protected String id;
	
	@Column(name = "class_name")
	protected String className;
	
	@Column(name = "class_channel_id")
	protected String classChannelId;
	
	@Column(name = "class_type_id")
	protected String classTypeId;
	
	@Column(name = "class_location_id")
	protected String classLocationId;
	
	@Column(name = "study_day")
	@Enumerated(EnumType.STRING)
	protected DayOfWeek studyDay;
	
	@Column(name = "study_date")
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = KwsConst.DEFAULT_DATE_FORMAT, timezone = KwsConst.DEFAULT_TIMEZONE)
	protected Date studyDate;
	
	@Column(name = "study_start_time")
	@JsonFormat(pattern = KwsConst.DEFAULT_TIME_FORMAT, timezone = KwsConst.DEFAULT_TIMEZONE)
	protected Time studyStartTime;
	
	@Column(name = "study_end_time")
	@JsonFormat(pattern = KwsConst.DEFAULT_TIME_FORMAT, timezone = KwsConst.DEFAULT_TIMEZONE)
	protected Time studyEndTime;
	
	@Column(name = "class_hour")
	protected Integer classHour;
	
	@Column(name = "max_student")
	protected Integer maxStudent;

	@Column(name = "reserved_student")
	protected Integer reservedStudent;
	
	@Column(name = "description")
	protected String description;
	
	@Column(name = "remark")
	protected String remark;
	
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