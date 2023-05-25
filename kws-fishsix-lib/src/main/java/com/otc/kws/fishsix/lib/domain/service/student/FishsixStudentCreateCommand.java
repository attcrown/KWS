package com.otc.kws.fishsix.lib.domain.service.student;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.otc.kws.core.domain.constant.RoleConst;
import com.otc.kws.core.domain.constant.value.MasterStatusValue;
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
import com.otc.kws.fishsix.lib.domain.model.StudentProfile;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaStudentRepository;

import lombok.Data;

@Component
public class FishsixStudentCreateCommand extends BaseKwsCommand
{
	@Autowired
	protected JpaStudentRepository studentRepository;
	
	@Autowired
	protected JpaUserAccountRepository userAccountRepository;
	
	@Autowired
	protected JpaUserPersonalInfoRepository userPersonalInfoRepository;
	
	@Autowired
	protected JpaUserRoleMapperRepository userRoleMapperRepository;
	
	
	public Response createStudent(Request request)
	{
		logger.info("### {}.createStudent ###", getClass().getSimpleName());
		
		Response response = new Response();
		
		StudentProfile createdStudentProfile = new StudentProfile();
		
		UserAccount userAccount = request.getStudentProfile().getUserAccount();
		Student student = request.getStudentProfile().getStudent();
		
		if(userAccount == null) {
			throw new KwsRuntimeException("UserAccount is null");
		}
		if(userAccountRepository.findByUsername(userAccount.getUsername()) != null) {
			throw new KwsRuntimeException("Duplicate username");
		}
		if(userAccountRepository.findByEmail(userAccount.getEmail()) != null) {
			throw new KwsRuntimeException("Duplicate email");
		}
		
		if(student == null) {
			throw new KwsRuntimeException("Student is null");
		}
		
		// ****************************** userAccount ****************************** //
		if(userAccount.getId() == null) {
			userAccount.setId(UUID.randomUUID().toString());
		}
		if(userAccount.getAccountActivatedAt() == null) {
			userAccount.setAccountActivatedAt(request.getPerformedAt());
		}
		if(userAccount.getStatus() == null) {
			userAccount.setStatus(MasterStatusValue.Active);
		}
		userAccount.setCreatedBy(request.getPerformedBy());
		userAccount.setCreatedAt(request.getPerformedAt());
		userAccount.setLastModifiedBy(request.getPerformedBy());
		userAccount.setLastModifiedAt(request.getPerformedAt());
		userAccount = userAccountRepository.save(userAccount);
		logger.info("create userAccount => {}", userAccount);
		createdStudentProfile.setUserAccount(userAccount);
		// ****************************** userAccount ****************************** //
		
		// ****************************** userPersonalInfo ****************************** //
		UserPersonalInfo userPersonalInfo = new UserPersonalInfo();
		userPersonalInfo.setUserId(userAccount.getId());
		userPersonalInfo.setGender(student.getGenderId());
		userPersonalInfo.setFirstName(student.getFirstName());
		userPersonalInfo.setLastName(student.getLastName());
		userPersonalInfo.setNickName(student.getNickName());
		userPersonalInfo.setStatus(MasterStatusValue.Active);
		userPersonalInfo.setCreatedBy(request.getPerformedBy());
		userPersonalInfo.setCreatedAt(request.getPerformedAt());
		userPersonalInfo.setLastModifiedBy(request.getPerformedBy());
		userPersonalInfo.setLastModifiedAt(request.getPerformedAt());
		userPersonalInfo = userPersonalInfoRepository.save(userPersonalInfo);
		logger.info("create userPersonalInfo => {}", userPersonalInfo);
		// ****************************** userPersonalInfo ****************************** //
		
		// ****************************** userRoleMapper ****************************** //
		List<UserRoleMapper> userRoleMappers = request.getStudentProfile().getUserRoleMappers();
		if(userRoleMappers == null || userRoleMappers.isEmpty()) {
			UserRoleMapper userRoleMapper = new UserRoleMapper();
			userRoleMapper.setUserId(userAccount.getId());
			userRoleMapper.setRoleId(RoleConst.ID_FISHSIX_STUDENT);
			
			userRoleMappers = new ArrayList<>();
			userRoleMappers.add(userRoleMapper);
		}
		logger.debug("userRoleMappers => {}", userRoleMappers);
		for(UserRoleMapper userRoleMapper : userRoleMappers) {
			if(userRoleMapper.getId() == null) {
				userRoleMapper.setId(UUID.randomUUID().toString());
			}
			if(userRoleMapper.getUserId() == null) {
				userRoleMapper.setUserId(userAccount.getId());
			}
			if(userRoleMapper.getActivatedAt() == null) {
				userRoleMapper.setActivatedAt(request.getPerformedAt());
			}
			if(userRoleMapper.getStatus() == null) {
				userRoleMapper.setStatus(MasterStatusValue.Active);
			}
			userRoleMapper.setCreatedBy(request.getPerformedBy());
			userRoleMapper.setCreatedAt(request.getPerformedAt());
			userRoleMapper.setLastModifiedBy(request.getPerformedBy());
			userRoleMapper.setLastModifiedAt(request.getPerformedAt());
			userRoleMapperRepository.save(userRoleMapper);
			logger.info("create userRoleMapper => {}", userRoleMapper);
		}
		createdStudentProfile.setUserRoleMappers(userRoleMappers);
		// ****************************** userRoleMapper ****************************** //
		
		// ****************************** student ****************************** //
		if(student.getId() == null) {
			student.setId(UUID.randomUUID().toString());
		}
		if(student.getUserId() == null) {
			student.setUserId(userAccount.getId());
		}
		if(student.getStatus() == null) {
			student.setStatus(MasterStatusValue.Active);
		}
		student.setCreatedBy(request.getPerformedBy());
		student.setCreatedAt(request.getPerformedAt());
		student.setLastModifiedBy(request.getPerformedBy());
		student.setLastModifiedAt(request.getPerformedAt());
		student = studentRepository.save(student);
		logger.info("create student => {}", student);
		createdStudentProfile.setStudent(student);
		// ****************************** student ****************************** //
		
		response.setCreatedStudentProfile(createdStudentProfile);
		
		return response;
	}
	
	
	@Data
	public static class Request extends BaseKwsCommandRequest
	{
		protected StudentProfile studentProfile;
	}
	
	
	@Data
	public static class Response extends BaseKwsCommandResponse
	{
		protected StudentProfile createdStudentProfile;
	}
}