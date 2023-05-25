package com.otc.kws.fishsix.lib.domain.service.student;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.otc.kws.core.domain.constant.value.MasterStatusValue;
import com.otc.kws.core.domain.entity.UserAccount;
import com.otc.kws.core.domain.entity.UserPersonalInfo;
import com.otc.kws.core.domain.entity.UserRoleMapper;
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
public class FishsixStudentUpdateCommand extends BaseKwsCommand
{
	@Autowired
	protected JpaStudentRepository studentRepository;
	
	@Autowired
	protected JpaUserAccountRepository userAccountRepository;
	
	@Autowired
	protected JpaUserPersonalInfoRepository userPersonalInfoRepository;
	
	@Autowired
	protected JpaUserRoleMapperRepository userRoleMapperRepository;
	
	
	public Response updateStudent(Request request)
	{
		logger.info("### {}.updateStudent ###", getClass().getSimpleName());
		
		Response response = new Response();
		
		StudentProfile updatedStudentProfile = new StudentProfile();
		
		// ****************************** student ****************************** //
		Student student = request.getStudentProfile().getStudent();
		if(student != null) {
			student.setLastModifiedBy(request.getPerformedBy());
			student.setLastModifiedAt(request.getPerformedAt());
			student = studentRepository.save(student);
			logger.info("update student => {}", student);
			updatedStudentProfile.setStudent(student);
		}
		// ****************************** student ****************************** //
		
		// ****************************** userAccount ****************************** //
		UserAccount userAccount = request.getStudentProfile().getUserAccount();
		if(userAccount != null) {
			userAccount.setLastModifiedBy(request.getPerformedBy());
			userAccount.setLastModifiedAt(request.getPerformedAt());
			userAccount = userAccountRepository.save(userAccount);
			logger.info("update userAccount => {}", userAccount);
			updatedStudentProfile.setUserAccount(userAccount);
		}
		// ****************************** userAccount ****************************** //
		
		// ****************************** userPersonalInfo ****************************** //
		UserPersonalInfo userPersonalInfo = userPersonalInfoRepository.findById(userAccount.getId()).orElse(null);
		if(userPersonalInfo != null) {
			userPersonalInfo.setGender(student.getGenderId());
			userPersonalInfo.setFirstName(student.getFirstName());
			userPersonalInfo.setLastName(student.getLastName());
			userPersonalInfo.setNickName(student.getNickName());
			userPersonalInfo.setLastModifiedBy(request.getPerformedBy());
			userPersonalInfo.setLastModifiedAt(request.getPerformedAt());
			userPersonalInfo = userPersonalInfoRepository.save(userPersonalInfo);
			logger.info("update userPersonalInfo => {}", userPersonalInfo);
		} else {
			userPersonalInfo = new UserPersonalInfo();
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
		}
		// ****************************** userPersonalInfo ****************************** //
		
		// ****************************** userRoleMapper ****************************** //
		List<UserRoleMapper> userRoleMappers = request.getStudentProfile().getUserRoleMappers();
		if(userRoleMappers != null && !userRoleMappers.isEmpty()) {
			userRoleMapperRepository.removeByUserId(userAccount.getId());
			
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
			updatedStudentProfile.setUserRoleMappers(userRoleMappers);
		}
		// ****************************** userRoleMapper ****************************** //
		
		response.setUpdatedStudentProfile(updatedStudentProfile);
		
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
		protected StudentProfile updatedStudentProfile;
	}
}