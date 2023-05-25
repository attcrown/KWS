package com.otc.kws.fishsix.lib.domain.service.teacher;

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
import com.otc.kws.fishsix.lib.domain.entity.Teacher;
import com.otc.kws.fishsix.lib.domain.entity.TeacherSubjectMapper;
import com.otc.kws.fishsix.lib.domain.model.TeacherProfile;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaTeacherRepository;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaTeacherSubjectMapperRepository;

import lombok.Data;

@Component
public class FishsixTeacherRemoveCommand extends BaseKwsCommand
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
	
	@Autowired
	protected FishsixTeacherService teacherService;
	
	
	public Response removeTeacher(Request request)
	{
		logger.info("### {}.removeTeacher ###", getClass().getSimpleName());
		
		Response response = new Response();
		
		TeacherProfile removedTeacherProfile = new TeacherProfile();
		
		Teacher teacher = teacherRepository.findById(request.getTeacherId()).orElse(null);
		
		if(teacher == null) {
			throw new KwsRuntimeException("Teacher not found");
		}
		
		if(teacher.getIdcardFileURI() != null) {
			teacherService.removeIdCardFile(req -> {
				req.copyFrom(request);
				req.setTeacher(teacher);
			});
		}
		
		// ****************************** teacher ****************************** //
		teacher.setIdcardFileURI(null);
		teacher.setStatus(MasterStatusValue.Closed);
		teacher.setLastModifiedBy(request.getPerformedBy());
		teacher.setLastModifiedAt(request.getPerformedAt());
		teacherRepository.save(teacher);
		logger.info("close teacher => {}", teacher);
		removedTeacherProfile.setTeacher(teacher);
		// ****************************** teacher ****************************** //
		
		// ****************************** teacherSubjectMapper ****************************** //
		List<TeacherSubjectMapper> teacherSubjectMappers = teacherSubjectMapperRepository.removeByTeacherId(teacher.getId());
		logger.info("remove teacherSubjectMappers => {}", teacherSubjectMappers);
		removedTeacherProfile.setTeacherSubjectMappers(teacherSubjectMappers);
		// ****************************** teacherSubjectMapper ****************************** //
		
		// ****************************** userAccount ****************************** //
		UserAccount userAccount = userAccountRepository.findById(teacher.getUserId()).orElse(null);
		if(userAccount != null) {
			userAccount.setStatus(MasterStatusValue.Closed);
			userAccount.setLastModifiedBy(request.getPerformedBy());
			userAccount.setLastModifiedAt(request.getPerformedAt());
			userAccountRepository.save(userAccount);
			logger.info("close userAccount => {}", userAccount);
			removedTeacherProfile.setUserAccount(userAccount);
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
		List<UserRoleMapper> userRoleMappers = userRoleMapperRepository.removeByUserId(teacher.getUserId());
		logger.info("remove userRoleMappers => {}", userRoleMappers);
		removedTeacherProfile.setUserRoleMappers(userRoleMappers);
		// ****************************** userRoleMapper ****************************** //
		
		response.setRemovedTeacherProfile(removedTeacherProfile);
		
		return response;
	}
	
	
	@Data
	public static class Request extends BaseKwsCommandRequest
	{
		protected String teacherId;
	}
	
	
	@Data
	public static class Response extends BaseKwsCommandResponse
	{
		protected TeacherProfile removedTeacherProfile;
	}
}