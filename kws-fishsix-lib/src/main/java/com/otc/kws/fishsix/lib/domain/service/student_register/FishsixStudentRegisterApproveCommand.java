package com.otc.kws.fishsix.lib.domain.service.student_register;

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
import com.otc.kws.core.domain.repository.jpa.JpaUserAccountRepository;
import com.otc.kws.core.domain.repository.jpa.JpaUserPersonalInfoRepository;
import com.otc.kws.core.domain.repository.jpa.JpaUserRoleMapperRepository;
import com.otc.kws.core.domain.service.BaseKwsCommand;
import com.otc.kws.core.domain.service.BaseKwsCommandRequest;
import com.otc.kws.core.domain.service.BaseKwsCommandResponse;
import com.otc.kws.fishsix.lib.domain.entity.Student;
import com.otc.kws.fishsix.lib.domain.entity.StudentRegister;
import com.otc.kws.fishsix.lib.domain.entity.StudentRegister.RegisterStatus;
import com.otc.kws.fishsix.lib.domain.model.StudentProfile;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaStudentRegisterRepository;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaStudentRepository;

import lombok.Data;

@Component
public class FishsixStudentRegisterApproveCommand extends BaseKwsCommand
{
	@Autowired
	protected JpaStudentRegisterRepository studentRegisterRepository;
	
	@Autowired
	protected JpaUserAccountRepository userAccountRepository;
	
	@Autowired
	protected JpaUserPersonalInfoRepository userPersonalInfoRepository;
	
	@Autowired
	protected JpaUserRoleMapperRepository userRoleMapperRepository;
	
	@Autowired
	protected JpaStudentRepository studentRepository;
	
	
	public Response approve(Request request)
	{
		logger.info("### {}.approve ###", getClass().getSimpleName());
		
		
		StudentRegister studentRegister = request.getStudentRegister();
		
		
		// ****************************** verify ****************************** //
		StudentRegister _studentRegister = studentRegisterRepository.findById(studentRegister.getId()).orElse(null);
		
		if(_studentRegister == null) {
			throw new KwsRuntimeException("Student register not found");
		}
		
		if(_studentRegister.getRegisterStatus() != RegisterStatus.Pending) {
			throw new KwsRuntimeException("Cannot approve student register which register status is not 'Pending'");
		}
		
		if(userAccountRepository.findByUsername(studentRegister.getEmail()) != null) {
			throw new KwsRuntimeException("Duplicate username");
		}
		
		if(userAccountRepository.findByEmail(studentRegister.getEmail()) != null) {
			throw new KwsRuntimeException("Duplicate email");
		}
		// ****************************** verify ****************************** //
		
		
		// ****************************** approve student_register ****************************** //
		studentRegister.setRegisterStatus(RegisterStatus.Approved);
		studentRegister.setApprovedBy(request.getPerformedBy());
		studentRegister.setApprovedAt(request.getPerformedAt());
		
		StudentRegister approvedStudentRegister = studentRegisterRepository.save(studentRegister);
		logger.info("Update studentRegister => {}", approvedStudentRegister);
		// ****************************** approve student_register ****************************** //
		
		
		// ****************************** create user_account ****************************** //
		UserAccount userAccount = new UserAccount();
		userAccount.setId(UUID.randomUUID().toString());
		userAccount.setUsername(studentRegister.getEmail());
		userAccount.setPassword(studentRegister.getPassword());
		userAccount.setEmail(studentRegister.getEmail());
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
		userPersonalInfo.setGender(studentRegister.getGenderId());
		userPersonalInfo.setFirstName(studentRegister.getFirstName());
		userPersonalInfo.setLastName(studentRegister.getLastName());
		userPersonalInfo.setNickName(studentRegister.getNickName());
		userPersonalInfo.setBirthDate(studentRegister.getBirthDate());
		userPersonalInfo.setMobileNo(studentRegister.getMobileNo());
		userPersonalInfo.setContactAddressText(studentRegister.getContactAddress());
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
		userRoleMapper.setRoleId(RoleConst.ID_FISHSIX_STUDENT);
		userRoleMapper.setActivatedAt(request.getPerformedAt());
		userRoleMapper.setExpiredAt(null);
		userRoleMapper.setStatus(MasterStatusValue.Active);
		userRoleMapper.setCreatedBy(request.getPerformedBy());
		userRoleMapper.setCreatedAt(request.getPerformedAt());
		
		List<UserRoleMapper> userRoleMappers = Arrays.asList(userRoleMapper);
		userRoleMappers = userRoleMapperRepository.saveAll(userRoleMappers);
		logger.info("Create userRoleMappers => {}", userRoleMappers);
		// ****************************** create user_role_mapper ****************************** //
		
		
		// ****************************** create student ****************************** //
		Student student = new Student();
		student.setId(UUID.randomUUID().toString());
		student.setUserId(userAccount.getId());
		student.setGenderId(studentRegister.getGenderId());
		student.setFirstName(studentRegister.getFirstName());
		student.setLastName(studentRegister.getLastName());
		student.setNickName(studentRegister.getNickName());
		student.setBirthDate(studentRegister.getBirthDate());
		student.setMobileNo(studentRegister.getMobileNo());
		student.setContactAddress(studentRegister.getContactAddress());
		student.setEducationSchool(studentRegister.getEducationSchool());
		student.setEducationLevel(studentRegister.getEducationLevel());
		student.setParentName(studentRegister.getParentName());
		student.setParentMobileNo(studentRegister.getParentMobileNo());
		student.setParentJob(studentRegister.getParentJob());
		student.setParentExpectation(studentRegister.getParentExpectation());
		student.setFavourTeacherStyle(studentRegister.getFavourTeacherStyle());
		student.setBackground(studentRegister.getBackground());
		student.setRemark(null);
		student.setStatus(MasterStatusValue.Active);
		student.setCreatedBy(request.getPerformedBy());
		student.setCreatedAt(request.getPerformedAt());
		
		student = studentRepository.save(student);
		logger.info("Create student => {}", student);
		// ****************************** create student ****************************** //
		
		
		StudentProfile studentProfile = new StudentProfile();
		studentProfile.setStudent(student);
		studentProfile.setUserAccount(userAccount);
		studentProfile.setUserRoleMappers(userRoleMappers);
		
		
		Response response = new Response();
		response.setApprovedStudentRegister(approvedStudentRegister);
		response.setCreatedStudentProfile(studentProfile);
		
		return response;
	}
	
	
	@Data
	public static class Request extends BaseKwsCommandRequest
	{
		protected StudentRegister studentRegister;
	}
	
	
	@Data
	public static class Response extends BaseKwsCommandResponse
	{
		protected StudentRegister approvedStudentRegister;
		protected StudentProfile createdStudentProfile;
	}
}