package com.otc.kws.fishsix.lib.domain.service.teacher;

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
import com.otc.kws.fishsix.lib.domain.entity.Teacher;
import com.otc.kws.fishsix.lib.domain.entity.TeacherSubjectMapper;
import com.otc.kws.fishsix.lib.domain.model.TeacherProfile;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaTeacherRepository;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaTeacherSubjectMapperRepository;

import lombok.Data;

@Component
public class FishsixTeacherCreateCommand extends BaseKwsCommand
{
	@Autowired
	protected JpaTeacherRepository teacherRepository;
	
	@Autowired
	protected JpaTeacherSubjectMapperRepository teacherSubjectMapperRepository;
	
	@Autowired
	protected JpaUserAccountRepository userAccountRepository;
	
	@Autowired
	protected JpaUserPersonalInfoRepository userPersonalInfoRepository;
	
	@Autowired
	protected JpaUserRoleMapperRepository userRoleMapperRepository;
	
	
	public Response createTeacher(Request request)
	{
		logger.info("### {}.createTeacher ###", getClass().getSimpleName());
		
		Response response = new Response();
		
		TeacherProfile createdTeacherProfile = new TeacherProfile();
		
		// ****************************** userAccount ****************************** //
		UserAccount userAccount = request.getTeacherProfile().getUserAccount();
		if(userAccount == null) {
			throw new KwsRuntimeException("UserAccount is null");
		}
		if(userAccountRepository.findByUsername(userAccount.getUsername()) != null) {
			throw new KwsRuntimeException("Duplicate username");
		}
		if(userAccountRepository.findByEmail(userAccount.getEmail()) != null) {
			throw new KwsRuntimeException("Duplicate email");
		}
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
		createdTeacherProfile.setUserAccount(userAccount);
		// ****************************** userAccount ****************************** //
		
		// ****************************** teacher ****************************** //
		Teacher teacher = request.getTeacherProfile().getTeacher();
		if(teacher == null) {
			throw new KwsRuntimeException("Teacher is null");
		}
		if(teacherRepository.findByIdcardNo(teacher.getIdcardNo()) != null) {
			throw new KwsRuntimeException("เลขบัตรประชาชนซ้ำกับข้อมูลครูที่มีอยู่ในระบบแล้ว, กรุณาระบุเลขบัตรประชาชนให้ถูกต้อง");
		}
		if(teacher.getId() == null) {
			teacher.setId(UUID.randomUUID().toString());
		}
		if(teacher.getUserId() == null) {
			teacher.setUserId(userAccount.getId());
		}
		if(teacher.getStatus() == null) {
			teacher.setStatus(MasterStatusValue.Active);
		}
		teacher.setCreatedBy(request.getPerformedBy());
		teacher.setCreatedAt(request.getPerformedAt());
		teacher.setLastModifiedBy(request.getPerformedBy());
		teacher.setLastModifiedAt(request.getPerformedAt());
		teacher = teacherRepository.save(teacher);
		logger.info("create teacher => {}", teacher);
		createdTeacherProfile.setTeacher(teacher);
		// ****************************** teacher ****************************** //
		
		// ****************************** teacherSubjectMapper ****************************** //
		List<TeacherSubjectMapper> teacherSubjectMappers = request.getTeacherProfile().getTeacherSubjectMappers();
		if(teacherSubjectMappers != null && !teacherSubjectMappers.isEmpty()) {
			for(TeacherSubjectMapper teacherSubjectMapper : teacherSubjectMappers) {
				if(teacherSubjectMapper.getId() == null) {
					teacherSubjectMapper.setId(UUID.randomUUID().toString());
				}
				if(teacherSubjectMapper.getTeacherId() == null) {
					teacherSubjectMapper.setTeacherId(teacher.getId());
				}
				if(teacherSubjectMapper.getStatus() == null) {
					teacherSubjectMapper.setStatus(MasterStatusValue.Active);
				}
				teacherSubjectMapper.setCreatedBy(request.getPerformedBy());
				teacherSubjectMapper.setCreatedAt(request.getPerformedAt());
				teacherSubjectMapper.setLastModifiedBy(request.getPerformedBy());
				teacherSubjectMapper.setLastModifiedAt(request.getPerformedAt());
				teacherSubjectMapper = teacherSubjectMapperRepository.save(teacherSubjectMapper);
				logger.info("create teacherSubjectMapper => {}", teacherSubjectMapper);
			}
			createdTeacherProfile.setTeacherSubjectMappers(teacherSubjectMappers);
		}
		// ****************************** teacherSubjectMapper ****************************** //
		
		// ****************************** userPersonalInfo ****************************** //
		UserPersonalInfo userPersonalInfo = new UserPersonalInfo();
		userPersonalInfo.setUserId(userAccount.getId());
		userPersonalInfo.setGender(teacher.getGenderId());
		userPersonalInfo.setFirstName(teacher.getFirstName());
		userPersonalInfo.setLastName(teacher.getLastName());
		userPersonalInfo.setNickName(teacher.getNickName());
		userPersonalInfo.setStatus(MasterStatusValue.Active);
		userPersonalInfo.setCreatedBy(request.getPerformedBy());
		userPersonalInfo.setCreatedAt(request.getPerformedAt());
		userPersonalInfo.setLastModifiedBy(request.getPerformedBy());
		userPersonalInfo.setLastModifiedAt(request.getPerformedAt());
		userPersonalInfo = userPersonalInfoRepository.save(userPersonalInfo);
		logger.info("create userPersonalInfo => {}", userPersonalInfo);
		// ****************************** userPersonalInfo ****************************** //
		
		// ****************************** userRoleMapper ****************************** //
		List<UserRoleMapper> userRoleMappers = request.getTeacherProfile().getUserRoleMappers();
		if(userRoleMappers == null || userRoleMappers.isEmpty()) {
			UserRoleMapper userRoleMapper = new UserRoleMapper();
			userRoleMapper.setUserId(userAccount.getId());
			userRoleMapper.setRoleId(RoleConst.ID_FISHSIX_TEACHER);
			
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
		createdTeacherProfile.setUserRoleMappers(userRoleMappers);
		// ****************************** userRoleMapper ****************************** //
		
		response.setCreatedTeacherProfile(createdTeacherProfile);
		
		return response;
	}
	
	
	@Data
	public static class Request extends BaseKwsCommandRequest
	{
		protected TeacherProfile teacherProfile;
	}
	
	
	@Data
	public static class Response extends BaseKwsCommandResponse
	{
		protected TeacherProfile createdTeacherProfile;
	}
}