package com.otc.kws.fishsix.lib.domain.service.student;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
public class FishsixStudentRemoveCommand extends BaseKwsCommand
{
	@Autowired
	protected JpaStudentRepository studentRepository;
	
	@Autowired
	protected JpaUserAccountRepository userAccountRepository;
	
	@Autowired
	protected JpaUserPersonalInfoRepository userPersonalInfoRepository;
	
	@Autowired
	protected JpaUserRoleMapperRepository userRoleMapperRepository;
	
	
	public Response removeStudent(Request request)
	{
		logger.info("### {}.removeStudent ###", getClass().getSimpleName());
		
		Response response = new Response();
		
		StudentProfile removedStudentProfile = new StudentProfile();
		
		Student student = studentRepository.findById(request.getStudentId()).orElse(null);
		
		if(student == null) {
			throw new KwsRuntimeException("Student not found");
		}
		
		// ****************************** student ****************************** //
		student.setStatus(MasterStatusValue.Closed);
		student.setLastModifiedBy(request.getPerformedBy());
		student.setLastModifiedAt(request.getPerformedAt());
		studentRepository.save(student);
		logger.info("### close student => {}", student);
		removedStudentProfile.setStudent(student);
		// ****************************** student ****************************** //
		
		// ****************************** userAccount ****************************** //
		UserAccount userAccount = userAccountRepository.findById(student.getUserId()).orElse(null);
		if(userAccount != null) {
			userAccount.setStatus(MasterStatusValue.Closed);
			userAccount.setLastModifiedBy(request.getPerformedBy());
			userAccount.setLastModifiedAt(request.getPerformedAt());
			userAccountRepository.save(userAccount);
			logger.info("close userAccount => {}", userAccount);
			removedStudentProfile.setUserAccount(userAccount);
		}
		// ****************************** userAccount ****************************** //
		
		// ****************************** userPersonalInfo ****************************** //
		UserPersonalInfo userPersonalInfo = userPersonalInfoRepository.findById(userAccount.getId()).orElse(null);
		if(userPersonalInfo != null) {
			userPersonalInfo.setStatus(MasterStatusValue.Closed);
			userPersonalInfo.setLastModifiedBy(request.getPerformedBy());
			userPersonalInfo.setLastModifiedAt(request.getPerformedAt());
			userPersonalInfoRepository.save(userPersonalInfo);
			logger.info("close userPersonalInfo => {}", userPersonalInfo);
		}
		// ****************************** userPersonalInfo ****************************** //
		
		// ****************************** userRoleMapper ****************************** //
		List<UserRoleMapper> userRoleMappers = userRoleMapperRepository.removeByUserId(student.getUserId());
		logger.info("remove userRoleMappers => {}", userRoleMappers);
		removedStudentProfile.setUserRoleMappers(userRoleMappers);
		// ****************************** userRoleMapper ****************************** //
		
		response.setRemovedStudentProfile(removedStudentProfile);
		
		return response;
	}
	
	
	@Data
	public static class Request extends BaseKwsCommandRequest
	{
		protected String studentId;
	}
	
	
	@Data
	public static class Response extends BaseKwsCommandResponse
	{
		protected StudentProfile removedStudentProfile;
	}
}