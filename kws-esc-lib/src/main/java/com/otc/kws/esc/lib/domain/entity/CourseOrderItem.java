package com.otc.kws.esc.lib.domain.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.otc.kws.esc.lib.domain.model.RequestCourseScheduleModel;

import lombok.Data;

@Entity
@Table(name = "id")
@Data
public class CourseOrderItem extends BaseKwsEscEntity
{
	@Id
	@Column(name = "id")
	protected String id;
	
	@Column(name = "order_id")
	protected String orderId;
	
	@Column(name = "order_seq_no")
	protected int orderSeqNo;
	
	@Column(name = "student_id")
	protected String studentId;
	
	@Column(name = "course_id")
	protected String courseId;
	
	@Column(name = "course_package_id")
	protected String coursePackageId;
	
	@Column(name = "request_course_start_date")
	@Temporal(TemporalType.DATE)
	protected Date requestCourseStartDate;
	
	@Column(name = "request_course_location_id")
	protected String requestCourseLocationId;
	
	@Column(name = "request_course_schedule")
	protected String requestCourseSchedule;
	
	@Column(name = "remark")
	protected String remark;
	
	@Column(name = "tags")
	protected String tags;
	
	
	protected transient RequestCourseScheduleModel requestCourseScheduleModel;
}