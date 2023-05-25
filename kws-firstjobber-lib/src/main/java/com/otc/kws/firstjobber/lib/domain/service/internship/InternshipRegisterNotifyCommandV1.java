package com.otc.kws.firstjobber.lib.domain.service.internship;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.otc.kws.core.domain.entity.EducationFaculty;
import com.otc.kws.core.domain.entity.EducationLevel;
import com.otc.kws.core.domain.entity.EducationMajor;
import com.otc.kws.core.domain.entity.School;
import com.otc.kws.core.domain.service.notification.NotificationSendNotifyCommand;
import com.otc.kws.firstjobber.lib.domain.entity.InternshipRegister;

@Component
public class InternshipRegisterNotifyCommandV1 extends InternshipRegisterNotifyCommand
{
	@Override
	public Response notifyInternshipRegister(Request request) 
	{
		InternshipRegister internshipRegister = request.getInternshipRegister();
		
		String username = internshipRegister.getFirstName() + " " + internshipRegister.getLastName();
		String schoolName = null;
		String educationLevelName = null;
		String educationFacultyName = null;
		String educationMajorName = null;
		
		if(internshipRegister.getEducationLevelOther() != null) {
			educationLevelName = internshipRegister.getEducationLevelOther();
		} else if(internshipRegister.getEducationLevelId() != null) {
			EducationLevel educationLevel = educationLevelService.getById(internshipRegister.getEducationLevelId());
			if(educationLevel != null) {
				educationLevelName = educationLevel.getName();
			}
		}
		
		if(internshipRegister.getSchoolOther() != null) {
			schoolName = internshipRegister.getSchoolOther();
		} else if(internshipRegister.getSchoolId() != null) {
			School school = schoolService.findById(internshipRegister.getSchoolId());
			if(school != null) {
				schoolName = school.getName();
			}
		}
		
		if(internshipRegister.getEducationFacultyOther() != null) {
			educationFacultyName = internshipRegister.getEducationFacultyOther();
		} else if(internshipRegister.getEducationFacultyId() != null) {
			EducationFaculty educationFaculty = educationFacultyService.findById(internshipRegister.getEducationFacultyId());
			if(educationFaculty != null) {
				educationFacultyName = educationFaculty.getName();
			}
		}
		
		if(internshipRegister.getEducationMajorOther() != null) {
			educationMajorName = internshipRegister.getEducationMajorOther();
		} else if(internshipRegister.getEducationMajorId() != null) {
			EducationMajor educationMajor = educationMajorService.findById(internshipRegister.getEducationMajorId());
			if(educationMajor != null) {
				educationMajorName = educationMajor.getName();
			}
		}
		
		Map<String, String> variables = new HashMap<>();
		variables.put("${username}", username);
		if(educationLevelName != null) {
			variables.put("${education_level}", educationLevelName);
		} else {
			variables.put("${education_level}", "-");
		}
		if(schoolName != null) {
			variables.put("${school_name}", schoolName);
		} else {
			variables.put("${school_name}", "-");
		}
		if(educationFacultyName != null) {
			variables.put("${education_faculty}", educationFacultyName);
		} else {
			variables.put("${education_faculty}", "-");
		}
		if(educationMajorName != null) {
			variables.put("${education_major}", educationMajorName);
		} else {
			variables.put("${education_major}", "-");
		}
		variables.put("${registered_at}", sdf.format(internshipRegister.getRegisteredAt()));
		variables.put("${link}", serverURLProvider.getServerURL() + "/views/workspace/firstjobber/internship/new-registration");
		
		Response response = new Response();
		
		NotificationSendNotifyCommand.Response sendNotifyResponse = notificationService.sendNotify(req -> {
			req.copyFrom(request);
			req.setEvent(request.getEvent());
			req.setVariables(variables);
		});
		
		response.setSendNotifyResponse(sendNotifyResponse);
		
		return response;
	}
}