package com.otc.kws.fishsix.lib.gateway.web.api.study_class.schedule;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.otc.kws.core.gateway.web.api.ResponseMessage;
import com.otc.kws.fishsix.lib.domain.entity.StudentCourseQuota;
import com.otc.kws.fishsix.lib.domain.entity.StudyClass;
import com.otc.kws.fishsix.lib.domain.entity.StudyClassSchedule;
import com.otc.kws.fishsix.lib.domain.entity.StudyClassSchedule.StudentCheckinStatus;
import com.otc.kws.fishsix.lib.domain.entity.StudyClassSchedule.StudentQuotaStatus;
import com.otc.kws.fishsix.lib.domain.entity.StudyClassSchedule.TeacherQuotaStatus;
import com.otc.kws.fishsix.lib.domain.service.study_class.StudyClassService;
import com.otc.kws.fishsix.lib.domain.service.study_class.schedule.StudyClassScheduleCheckinCommand;
import com.otc.kws.fishsix.lib.gateway.web.api.BaseKwsFishsixAPI;

import lombok.Data;

@RestController
@RequestMapping(BaseKwsFishsixAPI.PREFIX_PATH + "/study-class/schedule/checkin")
public class StudyClassScheduleCheckinAPI extends BaseKwsFishsixAPI
{
	@Autowired
	protected StudyClassService studyClassService;
	
	
	@PostMapping
	public ResponseEntity<?> checkinStudyClassSchedule(HttpServletRequest request, @RequestBody RequestMessage requestMessage)
	{
		logger.info("### {}.checkinStudyClassSchedule ###", getClass().getSimpleName());
		
		StudyClassScheduleCheckinCommand.Response createStudyClassScheduleResponse = studyClassService.checkinStudyClassSchedule(req -> {
			setupServiceRequest(request, req);
			req.setClassScheduleId(requestMessage.getClassScheduleId());
			req.setChangedTeacherId(requestMessage.getChangedTeacherId());
			req.setCheckinStatus(requestMessage.getCheckinStatus());
			req.setCheckinRemark(requestMessage.getCheckinRemark());
			req.setStudentQuotaStatus(requestMessage.getStudentQuotaStatus());
			req.setTeacherQuotaStatus(requestMessage.getTeacherQuotaStatus());
			req.setTeacherWageHourRate(requestMessage.getTeacherWageHourRate());
		});
		
		BodyResponseMessage bodyResponseMessage = new BodyResponseMessage();
		bodyResponseMessage.setStudyClass(createStudyClassScheduleResponse.getStudyClass());
		bodyResponseMessage.setUpdatedStudyClassSchedule(createStudyClassScheduleResponse.getUpdatedStudyClassSchedule());
		bodyResponseMessage.setUpdatedStudentCourseQuota(createStudyClassScheduleResponse.getUpdatedStudentCourseQuota());
		
		return replySuccess(request, bodyResponseMessage);
	}
	
	
	@Data
    public static class RequestMessage
    {
		protected String classScheduleId;
		protected String changedTeacherId; // กรณีเปลี่ยนครูผู้สอน
		protected StudentCheckinStatus checkinStatus;
		protected String checkinRemark;
		protected StudentQuotaStatus studentQuotaStatus = StudentQuotaStatus.Used;
		protected TeacherQuotaStatus teacherQuotaStatus = TeacherQuotaStatus.Pending;
		protected BigDecimal teacherWageHourRate;
    }


    @Data
    public static class BodyResponseMessage extends ResponseMessage.BaseBody
    {
    	protected StudyClass studyClass;
    	protected StudyClassSchedule updatedStudyClassSchedule;
		protected StudentCourseQuota updatedStudentCourseQuota;
    }
}