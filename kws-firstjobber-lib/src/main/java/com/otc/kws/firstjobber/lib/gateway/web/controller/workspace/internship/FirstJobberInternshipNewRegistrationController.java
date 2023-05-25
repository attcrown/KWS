package com.otc.kws.firstjobber.lib.gateway.web.controller.workspace.internship;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.otc.kws.core.domain.constant.KwsConst;
import com.otc.kws.core.domain.constant.value.GenderValue;
import com.otc.kws.core.domain.entity.EducationFaculty;
import com.otc.kws.core.domain.entity.EducationLevel;
import com.otc.kws.core.domain.entity.EducationMajor;
import com.otc.kws.core.domain.entity.High5TestCategory;
import com.otc.kws.core.domain.entity.Relationship;
import com.otc.kws.core.domain.entity.School;
import com.otc.kws.core.domain.entity.SixteenPersonalityCategory;
import com.otc.kws.core.domain.exception.KwsRuntimeException;
import com.otc.kws.core.domain.service.GenderService;
import com.otc.kws.core.domain.service.High5TestCategoryService;
import com.otc.kws.core.domain.service.RelationshipService;
import com.otc.kws.core.domain.service.SixteenPersonalityCategoryService;
import com.otc.kws.core.domain.service.education.EducationDegreeService;
import com.otc.kws.core.domain.service.education.EducationFacultyService;
import com.otc.kws.core.domain.service.education.EducationLevelService;
import com.otc.kws.core.domain.service.education.EducationMajorService;
import com.otc.kws.core.domain.service.education.EducationStatusService;
import com.otc.kws.core.domain.service.education.SchoolService;
import com.otc.kws.core.domain.service.education.SchoolTypeService;
import com.otc.kws.core.domain.service.filestore.FileStoreService;
import com.otc.kws.firstjobber.lib.domain.constant.value.InternshipRegisterStatusValue;
import com.otc.kws.firstjobber.lib.domain.entity.FjbInternshipRegisterExperience;
import com.otc.kws.firstjobber.lib.domain.entity.FjbInternshipRegisterTest;
import com.otc.kws.firstjobber.lib.domain.entity.InternshipRegister;
import com.otc.kws.firstjobber.lib.domain.entity.Job;
import com.otc.kws.firstjobber.lib.domain.service.InternshipRegisterService;
import com.otc.kws.firstjobber.lib.domain.service.InternshipRegisterStatusService;
import com.otc.kws.firstjobber.lib.domain.service.InternshipTypeService;
import com.otc.kws.firstjobber.lib.domain.service.JobService;
import com.otc.kws.firstjobber.lib.domain.service.internship.FjbInternshipRegisterExperienceService;
import com.otc.kws.firstjobber.lib.domain.service.internship.FjbInternshipRegisterTestService;
import com.otc.kws.firstjobber.lib.gateway.web.controller.workspace.KwsFirstJobberWorkspaceController;

import lombok.Data;

@Controller
@RequestMapping(KwsFirstJobberWorkspaceController.PREFIX_PATH + "/internship/new-registration")
public class FirstJobberInternshipNewRegistrationController extends KwsFirstJobberWorkspaceController
{
	protected final SimpleDateFormat dateFormat = new SimpleDateFormat(KwsConst.DEFAULT_DATE_FORMAT, Locale.ENGLISH);
	protected final SimpleDateFormat datetimeFormat = new SimpleDateFormat(KwsConst.DEFAULT_DATETIME_FORMAT, Locale.ENGLISH);
	
	
	@Autowired
	protected InternshipRegisterService internshipRegisterService;
	
	@Autowired
	protected FjbInternshipRegisterExperienceService internshipRegisterExperienceService;
	
	@Autowired
	protected GenderService genderService;
	
	@Autowired
	protected SchoolTypeService schoolTypeService;
	
	@Autowired
	protected SchoolService schoolService;
	
	@Autowired
	protected EducationStatusService educationStatusService;
	
	@Autowired
	protected EducationLevelService educationLevelService;
	
	@Autowired
	protected EducationDegreeService educationDegreeService;
	
	@Autowired
	protected EducationFacultyService educationFacultyService;
	
	@Autowired
	protected EducationMajorService educationMajorService;
	
	@Autowired
	protected InternshipTypeService internshipTypeService;
	
	@Autowired
	protected JobService jobService;
	
	@Autowired
	protected FileStoreService fileStoreService;
	
	@Autowired
	protected InternshipRegisterStatusService internshipRegisterStatusService;
	
	@Autowired
	protected RelationshipService relationshipService;
	
	@Autowired
	protected FjbInternshipRegisterTestService internshipRegisterTestService;
	
	@Autowired
	protected High5TestCategoryService high5TestCategoryService;
	
	@Autowired
	protected SixteenPersonalityCategoryService sixteenPersonalityCategoryService;
	
	
	@Override
	protected void setupModel(HttpServletRequest request, HttpServletResponse response, Model model) 
	{
		Map<String, Object> data = new HashMap<>();
		data.put("internshipRegisterStatuses", internshipRegisterStatusService.getAllActive());
		data.put("internshipRegisters", buildInternshipRegisterModels());
		
		model.addAttribute("kws_data", data);
	}
	
	
	protected List<InternshipRegisterModel> buildInternshipRegisterModels()
	{
		List<InternshipRegisterModel> internshipRegisterModels = new ArrayList<>();
		
		//List<InternshipRegister> internshipRegisters = internshipRegisterService.findAllPendingOrReviewingRegisterStatus();
		List<InternshipRegister> internshipRegisters = internshipRegisterService.findByRegisterStatusIds(
				Arrays.asList(
						InternshipRegisterStatusValue.Registered, 
						InternshipRegisterStatusValue.TestUploaded, 
						InternshipRegisterStatusValue.Reviewing
					)
			);
		
		if(internshipRegisters != null && !internshipRegisters.isEmpty()) {
			List<String> schoolIds = internshipRegisters.
					stream().
					filter(e -> e.getSchoolId() != null).
					map(InternshipRegister::getSchoolId).
					distinct().
					collect(Collectors.toList());
			
			List<String> educationFacultyIds = internshipRegisters.
					stream().
					filter(e -> e.getEducationFacultyId() != null).
					map(InternshipRegister::getEducationFacultyId).
					distinct().
					collect(Collectors.toList());
			
			List<String> educationMajorIds = internshipRegisters.
					stream().
					filter(e -> e.getEducationMajorId() != null).
					map(InternshipRegister::getEducationMajorId).
					distinct().
					collect(Collectors.toList());
			
			List<String> jobIds = getJobIds(internshipRegisters);
			
			List<String> internshipRegisterIds = internshipRegisters.
					stream().
					map(InternshipRegister::getId).
					distinct().
					collect(Collectors.toList());
			
			CompletableFuture<List<School>> schoolsFuture = CompletableFuture.completedFuture(new ArrayList<>());
			CompletableFuture<List<EducationFaculty>> educationFacultiesFuture = CompletableFuture.completedFuture(new ArrayList<>());
			CompletableFuture<List<EducationMajor>> educationMajorsFuture = CompletableFuture.completedFuture(new ArrayList<>());
			CompletableFuture<List<Job>> jobsFuture = CompletableFuture.completedFuture(new ArrayList<>());
			CompletableFuture<List<FjbInternshipRegisterExperience>> experiencesFuture = CompletableFuture.completedFuture(new ArrayList<>());
			
			if(schoolIds != null && !schoolIds.isEmpty()) {
				schoolsFuture = schoolService.fetchFlyweightByIdsAsync(schoolIds);
			}
			
			if(educationFacultyIds != null && !educationFacultyIds.isEmpty()) {
				educationFacultiesFuture = educationFacultyService.fetchFlyweightByIdsAsync(educationFacultyIds);
			}
			
			if(educationMajorIds != null && !educationMajorIds.isEmpty()) {
				educationMajorsFuture = educationMajorService.fetchFlyweightByIdsAsync(educationMajorIds);
			}
			
			if(jobIds != null && !jobIds.isEmpty()) {
				jobsFuture = jobService.findByIdsAsync(jobIds);
			}
			
			if(internshipRegisterIds != null && !internshipRegisterIds.isEmpty()) {
				experiencesFuture = internshipRegisterExperienceService.findByInternshipRegisterIdsAsync(internshipRegisterIds);
			}
			
			CompletableFuture.allOf(
		    		schoolsFuture, 
		    		educationFacultiesFuture, 
		    		educationMajorsFuture, 
		    		jobsFuture, 
		    		experiencesFuture
	    		).join();
			
			List<School> schools = null;
			try {
				schools = schoolsFuture.get();
			} catch(Exception ex) {
				throw new KwsRuntimeException(ex);
			}
			
			List<EducationFaculty> educationFaculties = null;
			try {
				educationFaculties = educationFacultiesFuture.get();
			} catch(Exception ex) {
				throw new KwsRuntimeException(ex);
			}
			
			List<EducationMajor> educationMajors = null;
			try {
				educationMajors = educationMajorsFuture.get();
			} catch(Exception ex) {
				throw new KwsRuntimeException(ex);
			}
			
			List<Job> jobs = null;
			try {
				jobs = jobsFuture.get();
			} catch(Exception ex) {
				throw new KwsRuntimeException(ex);
			}
			
			List<FjbInternshipRegisterExperience> experiences = null;
			try {
				experiences = experiencesFuture.get();
			} catch(Exception ex) {
				throw new KwsRuntimeException(ex);
			}
			
			for(InternshipRegister internshipRegister : internshipRegisters) {
				InternshipRegisterModel internshipRegisterModel = new InternshipRegisterModel();
				
				internshipRegisterModel.setId(internshipRegister.getId());
				internshipRegisterModel.setRegisterStatusId(internshipRegister.getRegisterStatusId());
				internshipRegisterModel.setRegisterStatusName(internshipRegisterStatusService.getById(internshipRegister.getRegisterStatusId().name()).getName());
				internshipRegisterModel.setRegisteredAt(datetimeFormat.format(internshipRegister.getRegisteredAt()));
				
				internshipRegisterModel.setPersonalInfo(buildPersonalInfoModel(internshipRegister));
				internshipRegisterModel.setContactPersonInfo(buildContactPersonInfoModel(internshipRegister));
				internshipRegisterModel.setEducationInfo(buildEducationInfoModel(internshipRegister, schools, educationFaculties, educationMajors));
				internshipRegisterModel.setInternInfo(buildInternInfoModel(internshipRegister, experiences, jobs));
				internshipRegisterModel.setAttachmentInfo(buildAttachmentInfoModel(internshipRegister));
				internshipRegisterModel.setTestInfo(buildTestInfoModel(internshipRegister));
				
				internshipRegisterModels.add(internshipRegisterModel);
			}
		}
		
		return internshipRegisterModels;
	}
	
	
	protected List<String> getJobIds(List<InternshipRegister> internshipRegisters)
	{
		List<String> job1Ids = internshipRegisters.
				stream().
				filter(e -> e.getRequestJob1Id() != null).
				map(InternshipRegister::getRequestJob1Id).
				distinct().
				collect(Collectors.toList());
		
		List<String> job2Ids = internshipRegisters.
				stream().
				filter(e -> e.getRequestJob2Id() != null).
				map(InternshipRegister::getRequestJob2Id).
				distinct().
				collect(Collectors.toList());
		
		List<String> job3Ids = internshipRegisters.
				stream().
				filter(e -> e.getRequestJob3Id() != null).
				map(InternshipRegister::getRequestJob3Id).
				distinct().
				collect(Collectors.toList());
		
		List<String> jobIds = new ArrayList<>();
		
		if(job1Ids != null && !job1Ids.isEmpty()) {
			jobIds.addAll(job1Ids);
		}
		
		if(job2Ids != null && !job2Ids.isEmpty()) {
			jobIds.addAll(job2Ids);
		}
		
		if(job3Ids != null && !job3Ids.isEmpty()) {
			jobIds.addAll(job3Ids);
		}
		
		if(jobIds != null && !jobIds.isEmpty()) {
			jobIds = jobIds.stream().distinct().collect(Collectors.toList());
		}
		
		return jobIds;
	}
	
	
	protected InternshipRegisterModel.PersonalInfoModel buildPersonalInfoModel(InternshipRegister internshipRegister)
	{
		InternshipRegisterModel.PersonalInfoModel personalInfoModel = new InternshipRegisterModel.PersonalInfoModel();
		
		Calendar birthDateCalendar = Calendar.getInstance();
		birthDateCalendar.setTime(internshipRegister.getBirthDate());
		
		Calendar nowCalendar = Calendar.getInstance();
		nowCalendar.setTime(new Date());
		
		personalInfoModel.setGender(internshipRegister.getGender());
		personalInfoModel.setGenderName(genderService.getById(internshipRegister.getGender().name()).getName());
		personalInfoModel.setFirstName(internshipRegister.getFirstName());
		personalInfoModel.setLastName(internshipRegister.getLastName());
		personalInfoModel.setNickName(internshipRegister.getNickName());
		personalInfoModel.setAge(getAge(internshipRegister));
		personalInfoModel.setMobileNo(internshipRegister.getMobileNo());
		personalInfoModel.setLineId(internshipRegister.getLineId());
		personalInfoModel.setEmail(internshipRegister.getEmail());
		personalInfoModel.setAddress(internshipRegister.getContactAddressDetail());
		
		if(internshipRegister.getFormalPhotoURI() != null) {
			String formalPhotoURL = fileStoreService.getURL(req -> {
				req.setFileURI(internshipRegister.getFormalPhotoURI());
			}).getUrl();
			personalInfoModel.setFormalPhotoURL(formalPhotoURL);
		}
		
		if(internshipRegister.getInformalPhotoURI() != null) {
			String informalPhotoURL = fileStoreService.getURL(req -> {
				req.setFileURI(internshipRegister.getInformalPhotoURI());
			}).getUrl();
			personalInfoModel.setInformalPhotoURL(informalPhotoURL);
		}
		
		return personalInfoModel;
	}
	
	
	protected int getAge(InternshipRegister internshipRegister)
	{
		Calendar birthDate = Calendar.getInstance();
		birthDate.setTime(internshipRegister.getBirthDate());
		
		Calendar now = Calendar.getInstance();
		now.setTime(new Date());
		
		return now.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR);
	}
	
	
	protected InternshipRegisterModel.ContactPersonInfoModel buildContactPersonInfoModel(InternshipRegister internshipRegister)
	{
		InternshipRegisterModel.ContactPersonInfoModel contactPersonInfoModel = new InternshipRegisterModel.ContactPersonInfoModel();
		
		contactPersonInfoModel.setFullName(internshipRegister.getContactPersonName());
		if(internshipRegister.getContactPersonRelationshipId() != null) {
			Relationship relationship = relationshipService.getById(internshipRegister.getContactPersonRelationshipId());
			if(relationship != null) {
				contactPersonInfoModel.setRelationshipName(relationship.getName());
			} else {
				if(internshipRegister.getContactPersonRelationshipOther() != null) {
					contactPersonInfoModel.setRelationshipName(internshipRegister.getContactPersonRelationshipOther());
				}
			}
		} else {
			contactPersonInfoModel.setRelationshipName(internshipRegister.getContactPersonRelationshipOther());
		}
		contactPersonInfoModel.setMobileNo(internshipRegister.getContactPersonMobileNo());
		
		return contactPersonInfoModel;
	}
	
	
	protected InternshipRegisterModel.EducationInfoModel buildEducationInfoModel(InternshipRegister internshipRegister, List<School> schools, List<EducationFaculty> educationFaculties, List<EducationMajor> educationMajors)
	{
		InternshipRegisterModel.EducationInfoModel educationInfoModel = new InternshipRegisterModel.EducationInfoModel();
		
		if(internshipRegister.getEducationLevelId() != null) {
			educationInfoModel.setLevelId(internshipRegister.getEducationLevelId());
			EducationLevel educationLevel = educationLevelService.getById(internshipRegister.getEducationLevelId());
			if(educationLevel != null) {
				educationInfoModel.setLevelName(educationLevel.getName());
				educationInfoModel.setClassNo(educationLevel.getClassTitleAbbr() + internshipRegister.getEducationClass());
			}
		} else {
			educationInfoModel.setLevelName(internshipRegister.getEducationLevelOther());
			educationInfoModel.setClassNo(internshipRegister.getEducationClass());
		}
		
		if(internshipRegister.getSchoolId() != null) {
			educationInfoModel.setSchoolId(internshipRegister.getSchoolId());
			if(schools != null && !schools.isEmpty()) {
				School school = schools.
						stream().
						filter(e -> e.getId().equals(internshipRegister.getSchoolId())).
						findFirst().
						orElse(null);
				if(school != null) {
					educationInfoModel.setSchoolName(school.getName());
				}
			}
		} else {
			educationInfoModel.setSchoolName(internshipRegister.getSchoolOther());
		}
		
		if(internshipRegister.getEducationFacultyId() != null) {
			educationInfoModel.setFacultyId(internshipRegister.getEducationFacultyId());
			if(educationFaculties != null && !educationFaculties.isEmpty()) {
				EducationFaculty educationFaculty = educationFaculties.
						stream().
						filter(e -> e.getId().equals(internshipRegister.getEducationFacultyId())).
						findFirst().
						orElse(null);
				if(educationFaculty != null) {
					educationInfoModel.setFacultyName(educationFaculty.getName());
				}
			}
		} else {
			educationInfoModel.setFacultyName(internshipRegister.getEducationFacultyOther());
		}
		
		if(internshipRegister.getEducationMajorId() != null) {
			educationInfoModel.setMajorId(internshipRegister.getEducationMajorId());
			if(educationMajors != null && !educationMajors.isEmpty()) {
				EducationMajor educationMajor = educationMajors.
						stream().
						filter(e -> e.getId().equals(internshipRegister.getEducationMajorId())).
						findFirst().
						orElse(null);
				if(educationMajor != null) {
					educationInfoModel.setMajorName(educationMajor.getName());
				}
			}
		} else {
			educationInfoModel.setMajorName(internshipRegister.getEducationMajorOther());
		}
		
		educationInfoModel.setGpa(internshipRegister.getGpa());
		
		return educationInfoModel;
	}
	
	
	protected InternshipRegisterModel.InternInfoModel buildInternInfoModel(InternshipRegister internshipRegister, List<FjbInternshipRegisterExperience> experiences, List<Job> jobs)
	{
		InternshipRegisterModel.InternInfoModel internInfoModel = new InternshipRegisterModel.InternInfoModel();
		
		internInfoModel.setInternshipTypeId(internshipRegister.getInternshipTypeId());
		internInfoModel.setInternshipTypeName(internshipTypeService.getById(internshipRegister.getInternshipTypeId()).getName());
		if(internshipRegister.getRequestAckDate() != null) {
			internInfoModel.setRequestAckDate(dateFormat.format(internshipRegister.getRequestAckDate()));
		}
		internInfoModel.setRequestStartDate(dateFormat.format(internshipRegister.getRequestStartDate()));
		internInfoModel.setRequestEndDate(dateFormat.format(internshipRegister.getRequestEndDate()));
		
		InternshipRegisterModel.InternInfoModel.JobModel job1Model = new InternshipRegisterModel.InternInfoModel.JobModel();
		if(internshipRegister.getRequestJob1Id() != null) {
			job1Model.setId(internshipRegister.getRequestJob1Id());
			if(jobs != null && !jobs.isEmpty()) {
				Job job = jobs.
						stream().
						filter(e -> e.getId().equals(internshipRegister.getRequestJob1Id())).
						findFirst().
						orElse(null);
				if(job != null) {
					job1Model.setName(job.getName());
				}
			}
		} else {
			job1Model.setName(internshipRegister.getRequestJob1Other());
		}
		
		InternshipRegisterModel.InternInfoModel.JobModel job2Model = new InternshipRegisterModel.InternInfoModel.JobModel();
		if(internshipRegister.getRequestJob2Id() != null) {
			job2Model.setId(internshipRegister.getRequestJob2Id());
			if(jobs != null && !jobs.isEmpty()) {
				Job job = jobs.
						stream().
						filter(e -> e.getId().equals(internshipRegister.getRequestJob2Id())).
						findFirst().
						orElse(null);
				if(job != null) {
					job2Model.setName(job.getName());
				}
			}
		} else {
			job2Model.setName(internshipRegister.getRequestJob2Other());
		}
		
		InternshipRegisterModel.InternInfoModel.JobModel job3Model = new InternshipRegisterModel.InternInfoModel.JobModel();
		if(internshipRegister.getRequestJob3Id() != null) {
			job3Model.setId(internshipRegister.getRequestJob3Id());
			if(jobs != null && !jobs.isEmpty()) {
				Job job = jobs.
						stream().
						filter(e -> e.getId().equals(internshipRegister.getRequestJob3Id())).
						findFirst().
						orElse(null);
				if(job != null) {
					job3Model.setName(job.getName());
				}
			}
		} else {
			job3Model.setName(internshipRegister.getRequestJob3Other());
		}
		
		internInfoModel.setRequestJobs(Arrays.asList(job1Model, job2Model, job3Model));
		
		internInfoModel.setRequestDayOffNum(internshipRegister.getRequestDayOffNum());
		internInfoModel.setSkill(internshipRegister.getSkill());
		internInfoModel.setPortfolioDescription(internshipRegister.getPortfolioDescription());
		internInfoModel.setRequestLocation(internshipRegister.getRequestLocation());
		
		List<String> experienceInfos = new ArrayList<>();
		if(experiences != null && !experiences.isEmpty()) {
			List<FjbInternshipRegisterExperience> filteredExperiences = experiences.
					stream().
					filter(e -> e.getInternshipRegisterId().equals(internshipRegister.getId())).
					collect(Collectors.toList());
			
			if(filteredExperiences != null && !filteredExperiences.isEmpty()) {
				for(FjbInternshipRegisterExperience experience : filteredExperiences) {
					experienceInfos.add(experience.getExperienceInfo());
				}
			}
		}
		
		internInfoModel.setExperienceInfos(experienceInfos);
		
		return internInfoModel;
	}
	
	
	protected InternshipRegisterModel.AttachmentInfoModel buildAttachmentInfoModel(InternshipRegister internshipRegister)
	{
		InternshipRegisterModel.AttachmentInfoModel attachmentInfoModel = new InternshipRegisterModel.AttachmentInfoModel();
		
		if(internshipRegister.getResumeFileURI() != null) {
			String resumeFileURL = fileStoreService.getURL(req -> {
				req.setFileURI(internshipRegister.getResumeFileURI());
			}).getUrl();
			attachmentInfoModel.setResumeFileURL(resumeFileURL);
		}
		
		List<String> portfolioFileURLs = new ArrayList<>();
		
		if(internshipRegister.getPortfolio1FileURI() != null) {
			String portfolioFileURL = fileStoreService.getURL(req -> {
				req.setFileURI(internshipRegister.getPortfolio1FileURI());
			}).getUrl();
			portfolioFileURLs.add(portfolioFileURL);
		}
		
		if(internshipRegister.getPortfolio2FileURI() != null) {
			String portfolioFileURL = fileStoreService.getURL(req -> {
				req.setFileURI(internshipRegister.getPortfolio2FileURI());
			}).getUrl();
			portfolioFileURLs.add(portfolioFileURL);
		}
		
		if(internshipRegister.getPortfolio3FileURI() != null) {
			String portfolioFileURL = fileStoreService.getURL(req -> {
				req.setFileURI(internshipRegister.getPortfolio3FileURI());
			}).getUrl();
			portfolioFileURLs.add(portfolioFileURL);
		}
		
		if(internshipRegister.getPortfolio4FileURI() != null) {
			String portfolioFileURL = fileStoreService.getURL(req -> {
				req.setFileURI(internshipRegister.getPortfolio4FileURI());
			}).getUrl();
			portfolioFileURLs.add(portfolioFileURL);
		}
		
		if(internshipRegister.getPortfolio5FileURI() != null) {
			String portfolioFileURL = fileStoreService.getURL(req -> {
				req.setFileURI(internshipRegister.getPortfolio5FileURI());
			}).getUrl();
			portfolioFileURLs.add(portfolioFileURL);
		}
		
		attachmentInfoModel.setPortfolioFileURLs(portfolioFileURLs);
		
		return attachmentInfoModel;
	}
	
	
	protected InternshipRegisterModel.TestInfoModel buildTestInfoModel(InternshipRegister internshipRegister)
	{
		if(internshipRegister.getRegisterStatusId() != InternshipRegisterStatusValue.TestUploaded) {
			return null;
		}
		
		FjbInternshipRegisterTest test = internshipRegisterTestService.findLatestTestByInternshipRegisterId(internshipRegister.getId());
		
		if(test == null) {
			return null;
		}
		
		InternshipRegisterModel.TestInfoModel testInfoModel = new InternshipRegisterModel.TestInfoModel();
		
		testInfoModel.setPersonalityCategoryId(test.getSixteenPersonalityCategoryId());
		SixteenPersonalityCategory sixteenPersonalityCategory = sixteenPersonalityCategoryService.getById(test.getSixteenPersonalityCategoryId());
		if(sixteenPersonalityCategory != null) {
			testInfoModel.setPersonalityCategoryName(sixteenPersonalityCategory.getName());
		}
		
		testInfoModel.setHigh5CategoryId1(test.getHigh5CategoryId1());
		High5TestCategory high5TestCategory1 = high5TestCategoryService.getById(test.getHigh5CategoryId1());
		if(high5TestCategory1 != null) {
			testInfoModel.setHigh5CategoryTitle1(high5TestCategory1.getTitle());
		}
		
		testInfoModel.setHigh5CategoryId2(test.getHigh5CategoryId2());
		High5TestCategory high5TestCategory2 = high5TestCategoryService.getById(test.getHigh5CategoryId2());
		if(high5TestCategory2 != null) {
			testInfoModel.setHigh5CategoryTitle2(high5TestCategory2.getTitle());
		}
		
		testInfoModel.setHigh5CategoryId3(test.getHigh5CategoryId3());
		High5TestCategory high5TestCategory3 = high5TestCategoryService.getById(test.getHigh5CategoryId3());
		if(high5TestCategory3 != null) {
			testInfoModel.setHigh5CategoryTitle3(high5TestCategory3.getTitle());
		}
		
		testInfoModel.setHigh5CategoryId4(test.getHigh5CategoryId4());
		High5TestCategory high5TestCategory4 = high5TestCategoryService.getById(test.getHigh5CategoryId4());
		if(high5TestCategory4 != null) {
			testInfoModel.setHigh5CategoryTitle4(high5TestCategory4.getTitle());
		}
		
		testInfoModel.setHigh5CategoryId5(test.getHigh5CategoryId5());
		High5TestCategory high5TestCategory5 = high5TestCategoryService.getById(test.getHigh5CategoryId5());
		if(high5TestCategory5 != null) {
			testInfoModel.setHigh5CategoryTitle5(high5TestCategory5.getTitle());
		}
		
		testInfoModel.setIqScore(test.getIqScore());
		
		if(test.getSixteenPersonalityImageURI() != null) {
			String personalityImageURL = fileStoreService.getURL(req -> {
				req.setFileURI(test.getSixteenPersonalityImageURI());
			}).getUrl();
			testInfoModel.setPersonalityImageURL(personalityImageURL);
		}
		
		if(test.getHigh5ImageURI() != null) {
			String high5ImageURL = fileStoreService.getURL(req -> {
				req.setFileURI(test.getHigh5ImageURI());
			}).getUrl();
			testInfoModel.setHigh5ImageURL(high5ImageURL);
		}
		
		if(test.getIqImageURI() != null) {
			String iqImageURL = fileStoreService.getURL(req -> {
				req.setFileURI(test.getIqImageURI());
			}).getUrl();
			testInfoModel.setIqImageURL(iqImageURL);
		}
		
		testInfoModel.setCreatedAt(datetimeFormat.format(test.getCreatedAt()));
		
		return testInfoModel;
	}
	
	
	@Data
	@JsonInclude(JsonInclude.Include.NON_NULL)
	public static class InternshipRegisterModel
	{
		protected String id;
		protected InternshipRegisterStatusValue registerStatusId;
		protected String registerStatusName;
		protected String registeredAt;
		
		protected PersonalInfoModel personalInfo;
		protected ContactPersonInfoModel contactPersonInfo;
		protected EducationInfoModel educationInfo;
		protected InternInfoModel internInfo;
		protected AttachmentInfoModel attachmentInfo;
		protected TestInfoModel testInfo;
		
		
		@Data
		@JsonInclude(JsonInclude.Include.NON_NULL)
		public static class PersonalInfoModel
		{
			protected GenderValue gender;
			protected String genderName;
			protected String firstName;
			protected String lastName;
			protected String nickName;
			protected int age;
			protected String mobileNo;
			protected String lineId;
			protected String email;
			protected String address;
			protected String formalPhotoURL;
			protected String informalPhotoURL;
		}
		
		
		@Data
		@JsonInclude(JsonInclude.Include.NON_NULL)
		public static class ContactPersonInfoModel
		{
			protected String fullName;
			protected String relationshipId;
			protected String relationshipName;
			protected String mobileNo;
		}
		
		
		@Data
		@JsonInclude(JsonInclude.Include.NON_NULL)
		public static class EducationInfoModel
		{
			protected String levelId;
			protected String levelName;
			protected String classNo;
			protected String schoolId;
			protected String schoolName;
			protected String facultyId;
			protected String facultyName;
			protected String majorId;
			protected String majorName;
			protected String gpa;
		}
		
		
		@Data
		@JsonInclude(JsonInclude.Include.NON_NULL)
		public static class InternInfoModel
		{
			protected String internshipTypeId;
			protected String internshipTypeName;
			protected String requestAckDate;
			protected String requestStartDate;
			protected String requestEndDate;
			protected List<JobModel> requestJobs;
			protected int requestDayOffNum;
			protected String skill;
			protected List<String> experienceInfos;
			protected String portfolioDescription;
			protected String requestLocation;
			
			@Data
			@JsonInclude(JsonInclude.Include.NON_NULL)
			public static class JobModel
			{
				protected String id;
				protected String name;
			}
		}
		
		
		@Data
		@JsonInclude(JsonInclude.Include.NON_NULL)
		public static class AttachmentInfoModel
		{
			protected String resumeFileURL;
			protected List<String> portfolioFileURLs;
		}
		
		
		@Data
		@JsonInclude(JsonInclude.Include.NON_NULL)
		public static class TestInfoModel
		{
			protected String personalityCategoryId;
			protected String personalityCategoryName;
			protected String personalityImageURL;
			
			protected String high5CategoryId1;
			protected String high5CategoryTitle1;
			protected String high5CategoryId2;
			protected String high5CategoryTitle2;
			protected String high5CategoryId3;
			protected String high5CategoryTitle3;
			protected String high5CategoryId4;
			protected String high5CategoryTitle4;
			protected String high5CategoryId5;
			protected String high5CategoryTitle5;
			protected String high5ImageURL;
			
			protected int iqScore;
			protected String iqImageURL;
			
			protected String createdAt;
		}
	}
}