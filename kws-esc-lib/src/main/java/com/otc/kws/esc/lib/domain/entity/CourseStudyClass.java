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

import com.otc.kws.esc.lib.domain.constant.value.CourseChannelValue;
import com.otc.kws.esc.lib.domain.constant.value.CourseStudyStatusValue;

import lombok.Data;

@Entity
@Table(name = "course_study_class")
@Data
public class CourseStudyClass extends BaseKwsEscEntity
{
	@Id
	@Column(name = "id")
	protected String id;
	
	@Column(name = "course_id")
	protected String courseId;
	
	@Column(name = "course_channel_id")
	@Enumerated(EnumType.STRING)
	protected CourseChannelValue courseChannelId;
	
	@Column(name = "course_location_id")
	protected String courseLocationId;
	
	@Column(name = "study_room_id")
	protected String studyRoomId;
	
	@Column(name = "study_date")
	@Temporal(TemporalType.DATE)
	protected Date studyDate;
	
	@Column(name = "study_start_time")
	protected Time studyStartTime;
	
	@Column(name = "study_end_time")
	protected Time studyEndTime;
	
	@Column(name = "class_teacher_id")
	protected String classTeacherId;
	
	@Column(name = "course_study_status_id")
	@Enumerated(EnumType.STRING)
	protected CourseStudyStatusValue courseStudyStatusId;
	
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