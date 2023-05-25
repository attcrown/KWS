package com.otc.kws.firstjobber.lib.domain.service.internship;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.otc.kws.core.domain.entity.EducationFaculty;
import com.otc.kws.core.domain.entity.EducationLevel;
import com.otc.kws.core.domain.entity.EducationMajor;
import com.otc.kws.core.domain.entity.High5TestCategory;
import com.otc.kws.core.domain.entity.School;
import com.otc.kws.core.domain.entity.SixteenPersonalityCategory;
import com.otc.kws.core.domain.service.notification.NotificationSendNotifyCommand;
import com.otc.kws.firstjobber.lib.domain.entity.FjbInternshipRegisterTest;
import com.otc.kws.firstjobber.lib.domain.entity.InternshipRegister;

@Component
public class InternshipUploadTestNotifyCommandV1 extends InternshipUploadTestNotifyCommand
{
	@Override
	public Response notifyInternshipUploadTest(Request request) 
	{
		InternshipRegister internshipRegister = request.getInternshipRegister();
		FjbInternshipRegisterTest internshipRegisterTest = request.getInternshipRegisterTest();
		
		String username = internshipRegister.getFirstName() + " " + internshipRegister.getLastName();
		String schoolName = null;
		String educationLevelName = null;
		String educationFacultyName = null;
		String educationMajorName = null;
		String personalityName = null;
		String high5CategoryName1 = null;
		String high5CategoryName2 = null;
		String high5CategoryName3 = null;
		String high5CategoryName4 = null;
		String high5CategoryName5 = null;
		int iqScore = internshipRegisterTest.getIqScore();
		
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
		
		if(internshipRegisterTest.getSixteenPersonalityCategoryId() != null) {
			SixteenPersonalityCategory personalityCategory = sixteenPersonalityCategoryService.getById(internshipRegisterTest.getSixteenPersonalityCategoryId());
			if(personalityCategory != null) {
				personalityName = personalityCategory.getName();
			}
		}
		
		if(internshipRegisterTest.getHigh5CategoryId1() != null) {
			High5TestCategory high5Category1 = high5TestCategoryService.getById(internshipRegisterTest.getHigh5CategoryId1());
			if(high5Category1 != null) {
				high5CategoryName1 = high5Category1.getTitle();
			}
		}
		
		if(internshipRegisterTest.getHigh5CategoryId2() != null) {
			High5TestCategory high5Category2 = high5TestCategoryService.getById(internshipRegisterTest.getHigh5CategoryId2());
			if(high5Category2 != null) {
				high5CategoryName2 = high5Category2.getTitle();
			}
		}
		
		if(internshipRegisterTest.getHigh5CategoryId3() != null) {
			High5TestCategory high5Category3 = high5TestCategoryService.getById(internshipRegisterTest.getHigh5CategoryId3());
			if(high5Category3 != null) {
				high5CategoryName3 = high5Category3.getTitle();
			}
		}
		
		if(internshipRegisterTest.getHigh5CategoryId4() != null) {
			High5TestCategory high5Category4 = high5TestCategoryService.getById(internshipRegisterTest.getHigh5CategoryId4());
			if(high5Category4 != null) {
				high5CategoryName4 = high5Category4.getTitle();
			}
		}
		
		if(internshipRegisterTest.getHigh5CategoryId5() != null) {
			High5TestCategory high5Category5 = high5TestCategoryService.getById(internshipRegisterTest.getHigh5CategoryId5());
			if(high5Category5 != null) {
				high5CategoryName5 = high5Category5.getTitle();
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
		
		if(personalityName != null) {
			variables.put("${personality_name}", personalityName);
		} else {
			variables.put("${personality_name}", "-");
		}
		
		if(high5CategoryName1 != null) {
			variables.put("${high5_category_name_1}", high5CategoryName1);
		} else {
			variables.put("${high5_category_name_1}", "-");
		}
		
		if(high5CategoryName2 != null) {
			variables.put("${high5_category_name_2}", high5CategoryName2);
		} else {
			variables.put("${high5_category_name_2}", "-");
		}
		
		if(high5CategoryName3 != null) {
			variables.put("${high5_category_name_3}", high5CategoryName3);
		} else {
			variables.put("${high5_category_name_3}", "-");
		}
		
		if(high5CategoryName4 != null) {
			variables.put("${high5_category_name_4}", high5CategoryName4);
		} else {
			variables.put("${high5_category_name_4}", "-");
		}
		
		if(high5CategoryName5 != null) {
			variables.put("${high5_category_name_5}", high5CategoryName5);
		} else {
			variables.put("${high5_category_name_5}", "-");
		}
		
		variables.put("${iq_score}", String.valueOf(iqScore));
		
		if(internshipRegisterTest.getLastModifiedAt() != null) {
			variables.put("${upload_test_at}", sdf.format(internshipRegisterTest.getLastModifiedAt()));
		} else if(internshipRegisterTest.getCreatedAt() != null) {
			variables.put("${upload_test_at}", sdf.format(internshipRegisterTest.getCreatedAt()));
		} else {
			variables.put("${upload_test_at}", sdf.format(new Date()));
		}
		
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