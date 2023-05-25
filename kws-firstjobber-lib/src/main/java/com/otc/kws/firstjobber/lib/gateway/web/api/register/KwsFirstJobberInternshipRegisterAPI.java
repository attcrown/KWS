package com.otc.kws.firstjobber.lib.gateway.web.api.register;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.otc.kws.core.domain.constant.KwsConst;
import com.otc.kws.core.domain.constant.value.GenderValue;
import com.otc.kws.core.domain.exception.KwsRuntimeException;
import com.otc.kws.core.domain.model.FileData;
import com.otc.kws.core.domain.util.JsonUtil;
import com.otc.kws.core.gateway.web.api.ResponseMessage;
import com.otc.kws.firstjobber.lib.domain.entity.FjbInternshipRegisterExperience;
import com.otc.kws.firstjobber.lib.domain.entity.InternshipRegister;
import com.otc.kws.firstjobber.lib.domain.service.InternshipRegisterService;
import com.otc.kws.firstjobber.lib.domain.service.internship.InternshipRegisterCommand;
import com.otc.kws.firstjobber.lib.gateway.web.api.BaseKwsFirstJobberAPI;

import lombok.Data;

@RestController
@RequestMapping(BaseKwsFirstJobberAPI.PREFIX_PATH + "/registration/internship")
public class KwsFirstJobberInternshipRegisterAPI extends BaseKwsFirstJobberAPI
{
	final protected SimpleDateFormat dateFormat = new SimpleDateFormat(KwsConst.DEFAULT_DATE_FORMAT, Locale.ENGLISH);
	
	
	@Autowired
	protected InternshipRegisterService internshipRegisterService;
	
	
	@PostMapping
	public ResponseEntity<?> register(HttpServletRequest request, @ModelAttribute RequestMessage requestMessage) throws Exception
	{
		logger.info("### KwsFirstJobberInternshipRegisterAPI.register ###");
		logger.info("requestMessage => " + requestMessage);
		
		FileData formalPhotoFileData = buildFileData(requestMessage.getFormalPhotoFile());
		FileData informalPhotoFileData = buildFileData(requestMessage.getInformalPhotoFile());
		FileData resumeFileData = buildFileData(requestMessage.getResumeFile());
		List<FileData> portfolioFileDatas = buildFileDatas(requestMessage.getPortfolioFiles());
		FileData internLetterFileData = buildFileData(requestMessage.getInternLetterFile());
		FileData internCertFileData = buildFileData(requestMessage.getInternCertFile());
		
		InternshipRegisterCommand.Response registerResponse = internshipRegisterService.register(req -> {
			setupServiceRequest(request, req);
			
			InternshipRegister internshipRegister = new InternshipRegister();
			
			internshipRegister.setGender(requestMessage.getGender());
			internshipRegister.setFirstName(requestMessage.getFirstName());
			internshipRegister.setLastName(requestMessage.getLastName());
			internshipRegister.setNickName(requestMessage.getNickName());
			try {
				if(requestMessage.getBirthDate() != null && !requestMessage.getBirthDate().isEmpty()) {
					internshipRegister.setBirthDate(dateFormat.parse(requestMessage.getBirthDate()));
				}
			} catch(Exception ex) {
				String msg = "Invalie BirthDate [" + requestMessage.getBirthDate() + "] format, Expected format is [" + KwsConst.DEFAULT_DATE_FORMAT + "]";
				throw new KwsRuntimeException(msg, ex);
			}
			internshipRegister.setMobileNo(requestMessage.getMobileNo());
			internshipRegister.setEmail(requestMessage.getEmail());
			internshipRegister.setLineId(requestMessage.getLineId());
			
			internshipRegister.setContactAddressDetail(requestMessage.getContactAddressDetail());
			internshipRegister.setContactAddressDistrictId(requestMessage.getContactAddressDistrictId());
			internshipRegister.setContactAddressDistrictName(requestMessage.getContactAddressDistrictName());
			internshipRegister.setContactAddressAmphurId(requestMessage.getContactAddressAmphurId());
			internshipRegister.setContactAddressAmphurName(requestMessage.getContactAddressAmphurName());
			internshipRegister.setContactAddressProvinceId(requestMessage.getContactAddressProvinceId());
			internshipRegister.setContactAddressProvinceName(requestMessage.getContactAddressProvinceName());
			internshipRegister.setContactAddressPostcode(requestMessage.getContactAddressPostcode());
			
			internshipRegister.setContactPersonName(requestMessage.getContactPersonName());
			internshipRegister.setContactPersonRelationshipId(requestMessage.getContactPersonRelationshipId());
			internshipRegister.setContactPersonRelationshipOther(requestMessage.getContactPersonRelationshipOther());
			internshipRegister.setContactPersonMobileNo(requestMessage.getContactPersonMobileNo());
			
			internshipRegister.setEducationStatusId(requestMessage.getEducationStatusId());
			internshipRegister.setEducationLevelId(requestMessage.getEducationLevelId());
			internshipRegister.setEducationLevelOther(requestMessage.getEducationLevelOther());
			internshipRegister.setEducationClass(requestMessage.getEducationClass());
			internshipRegister.setEducationFacultyId(requestMessage.getEducationFacultyId());
			internshipRegister.setEducationFacultyOther(requestMessage.getEducationFacultyOther());
			internshipRegister.setEducationMajorId(requestMessage.getEducationMajorId());
			internshipRegister.setEducationMajorOther(requestMessage.getEducationMajorOther());
			internshipRegister.setSchoolId(requestMessage.getSchoolId());
			internshipRegister.setSchoolOther(requestMessage.getSchoolOther());
			internshipRegister.setGpa(requestMessage.getGpa());
			
			internshipRegister.setInternshipTypeId(requestMessage.getInternshipTypeId());
			try {
				if(requestMessage.getRequestAckDate() != null && !requestMessage.getRequestAckDate().isEmpty()) {
					internshipRegister.setRequestAckDate(dateFormat.parse(requestMessage.getRequestAckDate()));
				}
			} catch(Exception ex) {
				String msg = "Invalid RequestAckDate [" + requestMessage.getRequestAckDate() + "] format, Expected format is [" + KwsConst.DEFAULT_DATE_FORMAT + "]";
				throw new KwsRuntimeException(msg, ex);
			}
			try {
				if(requestMessage.getRequestStartDate() != null && !requestMessage.getRequestStartDate().isEmpty()) {
					internshipRegister.setRequestStartDate(dateFormat.parse(requestMessage.getRequestStartDate()));
				}
			} catch(Exception ex) {
				String msg = "Invalid RequestStartDate [" + requestMessage.getRequestStartDate() + "] format, Expected format is [" + KwsConst.DEFAULT_DATE_FORMAT + "]";
				throw new KwsRuntimeException(msg, ex);
			}
			try {
				if(requestMessage.getRequestEndDate() != null && !requestMessage.getRequestEndDate().isEmpty()) {
					internshipRegister.setRequestEndDate(dateFormat.parse(requestMessage.getRequestEndDate()));
				}
			} catch(Exception ex) {
				String msg = "Invalid RequestEndDate [" + requestMessage.getRequestEndDate() + "] format, Expected format is [" + KwsConst.DEFAULT_DATE_FORMAT + "]";
				throw new KwsRuntimeException(msg, ex);
			}
			internshipRegister.setRequestJob1Id(requestMessage.getRequestJob1Id());
			internshipRegister.setRequestJob1Other(requestMessage.getRequestJob1Other());
			internshipRegister.setRequestJob2Id(requestMessage.getRequestJob2Id());
			internshipRegister.setRequestJob2Other(requestMessage.getRequestJob2Other());
			internshipRegister.setRequestJob3Id(requestMessage.getRequestJob3Id());
			internshipRegister.setRequestJob3Other(requestMessage.getRequestJob3Other());
			internshipRegister.setRequestDayOffNum(requestMessage.getRequestDayOffNum());
			internshipRegister.setSkill(requestMessage.getSkill());
			internshipRegister.setRequestLocation(requestMessage.getRequestLocation());
			internshipRegister.setPortfolioDescription(requestMessage.getPortfolioDescription());
			
			req.setInternshipRegister(internshipRegister);
			
			if(requestMessage.getExperienceInfosJSON() != null && !requestMessage.getExperienceInfosJSON().trim().isEmpty()) {
				try {
					InternshipRegisterExperienceList experienceList = JsonUtil.parse(requestMessage.getExperienceInfosJSON(), InternshipRegisterExperienceList.class);
					if(experienceList != null && !experienceList.isEmpty()) {
						req.setInternshipRegisterExperiences(experienceList);
					}
				} catch(Exception ex) {
					throw new KwsRuntimeException(ex);
				}
			}
			
			req.setFormalPhotoFile(formalPhotoFileData);
			req.setInformalPhotoFile(informalPhotoFileData);
			req.setResumeFile(resumeFileData);
			req.setPortfolioFiles(portfolioFileDatas);
			req.setInternLetterFile(internLetterFileData);
			req.setInternCertFile(internCertFileData);
		});
		
		BodyResponseMessage bodyResponseMessage = new BodyResponseMessage();
		bodyResponseMessage.setCreatedInternshipRegister(registerResponse.getCreatedInternshipRegister());
		bodyResponseMessage.setCreatedInternshipRegisterExperiences(registerResponse.getCreatedInternshipRegisterExperiences());
		
		return replySuccess(request, bodyResponseMessage);
	}
	
	
	public static class InternshipRegisterExperienceList extends ArrayList<FjbInternshipRegisterExperience>
	{
		
	}
	
	
	@Data
	public static class RequestMessage
	{
		protected GenderValue gender;
		protected String firstName;
		protected String lastName;
		protected String nickName;
		protected String birthDate;
		protected String mobileNo;
		protected String email;
		protected String lineId;
		
		protected String contactAddressDetail;
		protected String contactAddressDistrictId;
		protected String contactAddressDistrictName;
		protected String contactAddressAmphurId;
		protected String contactAddressAmphurName;
		protected String contactAddressProvinceId;
		protected String contactAddressProvinceName;
		protected String contactAddressPostcode;
		
		protected String contactPersonName;
		protected String contactPersonRelationshipId;
		protected String contactPersonRelationshipOther;
		protected String contactPersonMobileNo;
		
		protected String educationStatusId;
		protected String educationLevelId;
		protected String educationLevelOther;
		protected String educationClass;
		protected String educationFacultyId;
		protected String educationFacultyOther;
		protected String educationMajorId;
		protected String educationMajorOther;
		protected String schoolId;
		protected String schoolOther;
		protected String gpa;
		
		protected String internshipTypeId;
		protected String requestAckDate;
		protected String requestStartDate;
		protected String requestEndDate;
		protected String requestJob1Id;
		protected String requestJob1Other;
		protected String requestJob2Id;
		protected String requestJob2Other;
		protected String requestJob3Id;
		protected String requestJob3Other;
		protected int requestDayOffNum;
		protected String skill;
		protected String requestLocation;
		protected String portfolioDescription;
		
		protected String experienceInfosJSON;
		
	    protected MultipartFile formalPhotoFile;
	    protected MultipartFile informalPhotoFile;
	    protected MultipartFile resumeFile;
	    protected MultipartFile portfolioFiles[];
		protected MultipartFile internLetterFile;
		protected MultipartFile internCertFile;
	}
	
	
	@Data
	public static class BodyResponseMessage extends ResponseMessage.BaseBody
	{
		protected InternshipRegister createdInternshipRegister;
		protected List<FjbInternshipRegisterExperience> createdInternshipRegisterExperiences;
	}
}