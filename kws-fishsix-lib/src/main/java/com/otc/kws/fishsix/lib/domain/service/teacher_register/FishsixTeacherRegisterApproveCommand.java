package com.otc.kws.fishsix.lib.domain.service.teacher_register;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.otc.kws.core.domain.constant.RoleConst;
import com.otc.kws.core.domain.constant.value.MasterStatusValue;
import com.otc.kws.core.domain.constant.value.UserAccountGenusValue;
import com.otc.kws.core.domain.entity.UserAccount;
import com.otc.kws.core.domain.entity.UserPersonalInfo;
import com.otc.kws.core.domain.entity.UserRoleMapper;
import com.otc.kws.core.domain.exception.KwsRuntimeException;
import com.otc.kws.core.domain.model.FileData;
import com.otc.kws.core.domain.repository.jpa.JpaUserAccountRepository;
import com.otc.kws.core.domain.repository.jpa.JpaUserPersonalInfoRepository;
import com.otc.kws.core.domain.repository.jpa.JpaUserRoleMapperRepository;
import com.otc.kws.core.domain.service.BaseKwsCommand;
import com.otc.kws.core.domain.service.BaseKwsCommandRequest;
import com.otc.kws.core.domain.service.BaseKwsCommandResponse;
import com.otc.kws.core.domain.util.FileDataUtil;
import com.otc.kws.fishsix.lib.domain.entity.Teacher;
import com.otc.kws.fishsix.lib.domain.entity.TeacherRegister;
import com.otc.kws.fishsix.lib.domain.entity.TeacherRegister.RegisterStatus;
import com.otc.kws.fishsix.lib.domain.entity.TeacherSubjectMapper;
import com.otc.kws.fishsix.lib.domain.model.TeacherProfile;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaTeacherRegisterRepository;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaTeacherRepository;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaTeacherSubjectMapperRepository;
import com.otc.kws.fishsix.lib.domain.service.teacher.FishsixTeacherService;

import lombok.Data;

@Component
public class FishsixTeacherRegisterApproveCommand extends BaseKwsCommand
{
	@Autowired
	protected JpaTeacherRegisterRepository teacherRegisterRepository;
	
	@Autowired
	protected JpaUserAccountRepository userAccountRepository;
	
	@Autowired
	protected JpaUserPersonalInfoRepository userPersonalInfoRepository;
	
	@Autowired
	protected JpaUserRoleMapperRepository userRoleMapperRepository;
	
	@Autowired
	protected JpaTeacherRepository teacherRepository;
	
	@Autowired
	protected JpaTeacherSubjectMapperRepository teacherSubjectMapperRepository;
	
	@Autowired
	protected FishsixTeacherService teacherService;
	
	
	public Response approve(Request request)
	{
		logger.info("### {}.approve ###", getClass().getSimpleName());
		
		
		TeacherRegister teacherRegister = request.getTeacherRegister();
		
		
		// ****************************** verify ****************************** //
		TeacherRegister _teacherRegister = teacherRegisterRepository.findById(request.getTeacherRegister().getId()).orElse(null);
		
		if(_teacherRegister == null) {
			throw new KwsRuntimeException("Teacher register not found");
		}
		
		if(_teacherRegister.getRegisterStatus() != RegisterStatus.Pending) {
			throw new KwsRuntimeException("Cannot approve teacher register which register status is not 'Pending'");
		}
		
		if(userAccountRepository.findByUsername(teacherRegister.getEmail()) != null) {
			throw new KwsRuntimeException("Duplicate username");
		}
		
		if(userAccountRepository.findByEmail(teacherRegister.getEmail()) != null) {
			throw new KwsRuntimeException("Duplicate email");
		}
		// ****************************** verify ****************************** //
		
		
		// ****************************** approve teacher_register ****************************** //
		request.getTeacherRegister().setRegisterStatus(RegisterStatus.Approved);
		request.getTeacherRegister().setApprovedBy(request.getPerformedBy());
		request.getTeacherRegister().setApprovedAt(request.getPerformedAt());
		
		TeacherRegister approvedTeacherRegister = teacherRegisterRepository.save(request.getTeacherRegister());
		logger.info("Approve teacherRegister => {}", approvedTeacherRegister);
		// ****************************** approve teacher_register ****************************** //
		
		
		// ****************************** create user_account ****************************** //
		UserAccount userAccount = new UserAccount();
		userAccount.setId(UUID.randomUUID().toString());
		userAccount.setUsername(teacherRegister.getEmail());
		userAccount.setPassword(teacherRegister.getPassword());
		userAccount.setEmail(teacherRegister.getEmail());
		userAccount.setGenus(UserAccountGenusValue.Human);
		userAccount.setProfileImageURI(null);
		userAccount.setAccountLocked(false);
		userAccount.setPasswordLocked(false);
		userAccount.setAccountActivatedAt(request.getPerformedAt());
		userAccount.setAccountExpiredAt(null);
		userAccount.setPasswordExpiredAt(null);
		userAccount.setStatus(MasterStatusValue.Active);
		userAccount.setCreatedBy(request.getPerformedBy());
		userAccount.setCreatedAt(request.getPerformedAt());
		
		userAccount = userAccountRepository.save(userAccount);
		logger.info("Create userAccount => {}", userAccount);
		// ****************************** create user_account ****************************** //
		
		
		// ****************************** create user_personal_info ****************************** //
		UserPersonalInfo userPersonalInfo = new UserPersonalInfo();
		userPersonalInfo.setUserId(userAccount.getId());
		userPersonalInfo.setGender(teacherRegister.getGenderId());
		userPersonalInfo.setPrefixNameId(teacherRegister.getNameTitleId());
		userPersonalInfo.setFirstName(teacherRegister.getFirstName());
		userPersonalInfo.setLastName(teacherRegister.getLastName());
		userPersonalInfo.setNickName(teacherRegister.getNickName());
		userPersonalInfo.setIdCardNo(teacherRegister.getIdcardNo());
		userPersonalInfo.setBirthDate(teacherRegister.getBirthDate());
		userPersonalInfo.setMobileNo(teacherRegister.getMobileNo());
		userPersonalInfo.setRegistAddressText(teacherRegister.getRegistAddress());
		userPersonalInfo.setContactAddressText(teacherRegister.getContactAddress());
		userPersonalInfo.setStatus(MasterStatusValue.Active);
		userPersonalInfo.setCreatedBy(request.getPerformedBy());
		userPersonalInfo.setCreatedAt(request.getPerformedAt());
		
		userPersonalInfo = userPersonalInfoRepository.save(userPersonalInfo);
		logger.info("Create userPersonalInfo => {}", userPersonalInfo);
		// ****************************** create user_personal_info ****************************** //
		
		
		// ****************************** create user_role_mapper ****************************** //
		UserRoleMapper userRoleMapper = new UserRoleMapper();
		userRoleMapper.setId(UUID.randomUUID().toString());
		userRoleMapper.setUserId(userAccount.getId());
		userRoleMapper.setRoleId(RoleConst.ID_FISHSIX_TEACHER);
		userRoleMapper.setActivatedAt(request.getPerformedAt());
		userRoleMapper.setExpiredAt(null);
		userRoleMapper.setStatus(MasterStatusValue.Active);
		userRoleMapper.setCreatedBy(request.getPerformedBy());
		userRoleMapper.setCreatedAt(request.getPerformedAt());
		
		List<UserRoleMapper> userRoleMappers = Arrays.asList(userRoleMapper);
		userRoleMappers = userRoleMapperRepository.saveAll(userRoleMappers);
		logger.info("Create userRoleMappers => {}", userRoleMappers);
		// ****************************** create user_role_mapper ****************************** //
		
		
		// ****************************** create teacher ****************************** //
		Teacher teacher = new Teacher();
		teacher.setId(UUID.randomUUID().toString());
		teacher.setUserId(userAccount.getId());
		teacher.setGenderId(teacherRegister.getGenderId());
		teacher.setNameTitleId(teacherRegister.getNameTitleId());
		teacher.setFirstName(teacherRegister.getFirstName());
		teacher.setLastName(teacherRegister.getLastName());
		teacher.setNickName(teacherRegister.getNickName());
		teacher.setBirthDate(teacherRegister.getBirthDate());
		teacher.setIdcardNo(teacherRegister.getIdcardNo());
		teacher.setIdcardFileURI(null);
		teacher.setResumeFileURI(null);
		teacher.setMobileNo(teacherRegister.getMobileNo());
		teacher.setRegistAddress(teacherRegister.getRegistAddress());
		teacher.setContactAddress(teacherRegister.getContactAddress());
		teacher.setEducationSchool(teacherRegister.getEducationSchool());
		teacher.setEducationFaculty(teacherRegister.getEducationFaculty());
		teacher.setEducationMajor(teacherRegister.getEducationMajor());
		teacher.setOccupation(teacherRegister.getOccupation());
		teacher.setBankAccountNo(teacherRegister.getBankAccountNo());
		teacher.setBankAccountName(teacherRegister.getBankAccountName());
		teacher.setBankAccountBranch(teacherRegister.getBankAccountBranch());
		teacher.setBankAccountImageURI(null);
		teacher.setStartWorkDate(teacherRegister.getStartWorkDate());
		teacher.setHireTypeId(teacherRegister.getHireTypeId());
		teacher.setWorktimeTypeId(teacherRegister.getWorktimeTypeId());
		teacher.setContractFileURI(null);
		teacher.setSalary(teacherRegister.getSalary());
		teacher.setWageHourRate(teacherRegister.getWageHourRate());
		teacher.setWorkHourMonth(teacherRegister.getWorkHourMonth());
		teacher.setRemark(null);
		teacher.setStatus(MasterStatusValue.Active);
		teacher.setCreatedBy(request.getPerformedBy());
		teacher.setCreatedAt(request.getPerformedAt());
		
		teacherRepository.save(teacher);
		logger.info("Create teacher => {}", teacher);
		// ****************************** create teacher ****************************** //
		
		
		// ****************************** create teacher_subject_mapper ****************************** //
		List<TeacherSubjectMapper> teacherSubjectMappers = new ArrayList<>();
		if(teacherRegister.getSubjectIds() != null && !teacherRegister.getSubjectIds().trim().isEmpty()) {
			String[] subjectIds = teacherRegister.getSubjectIds().trim().split(",");
			for(String subjectId : subjectIds) {
				TeacherSubjectMapper teacherSubjectMapper = new TeacherSubjectMapper();
				teacherSubjectMapper.setId(UUID.randomUUID().toString());
				teacherSubjectMapper.setTeacherId(teacher.getId());
				teacherSubjectMapper.setSubjectId(subjectId);
				teacherSubjectMapper.setStatus(MasterStatusValue.Active);
				teacherSubjectMapper.setCreatedBy(request.getPerformedBy());
				teacherSubjectMapper.setCreatedAt(request.getPerformedAt());
				
				teacherSubjectMappers.add(teacherSubjectMapper);
			}
			
			teacherSubjectMappers = teacherSubjectMapperRepository.saveAll(teacherSubjectMappers);
			logger.info("Create teacherSubjectMappers => {}", teacherSubjectMappers);
		}
		// ****************************** create teacher_subject_mapper ****************************** //
		
		
		// ****************************** upload idcard_file ****************************** //
		if(teacherRegister.getIdcardFileBase64() != null && !teacherRegister.getIdcardFileBase64().trim().isEmpty()) {
			try {
				FileData idcardFileData = FileDataUtil.buildFileData(teacherRegister.getIdcardFileBase64().trim());
				teacherService.uploadIdCardFile(req -> {
					req.copyFrom(request);
					req.setTeacherId(teacher.getId());
					req.setIdCardFileData(idcardFileData);
				});
			} catch(Exception ex) {
				throw new KwsRuntimeException(ex);
			}
		}
		// ****************************** upload idcard_file ****************************** //
		
		
		/*
		TeacherProfile teacherProfile = new TeacherProfile();
		teacherProfile.setTeacher(teacher);
		teacherProfile.setTeacherSubjectMappers(teacherSubjectMappers);
		teacherProfile.setUserAccount(userAccount);
		teacherProfile.setUserRoleMappers(userRoleMappers);
		*/
		
		TeacherProfile teacherProfile = teacherService.buildTeacherProfile(req -> {
			req.copyFrom(request);
			req.setTeacherId(teacher.getId());
		}).getTeacherProfile();
		
		
		Response response = new Response();
		response.setApprovedTeacherRegister(approvedTeacherRegister);
		response.setCreatedTeacherProfile(teacherProfile);
		
		return response;
	}
	
	
	@Data
	public static class Request extends BaseKwsCommandRequest
	{
		protected TeacherRegister teacherRegister;
	}
	
	
	@Data
	public static class Response extends BaseKwsCommandResponse
	{
		protected TeacherRegister approvedTeacherRegister;
		protected TeacherProfile createdTeacherProfile;
	}
}