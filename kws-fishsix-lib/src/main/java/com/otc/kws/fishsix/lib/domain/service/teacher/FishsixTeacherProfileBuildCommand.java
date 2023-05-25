package com.otc.kws.fishsix.lib.domain.service.teacher;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.otc.kws.core.domain.entity.UserAccount;
import com.otc.kws.core.domain.entity.UserRoleMapper;
import com.otc.kws.core.domain.repository.jpa.JpaUserAccountRepository;
import com.otc.kws.core.domain.repository.jpa.JpaUserRoleMapperRepository;
import com.otc.kws.core.domain.service.BaseKwsCommand;
import com.otc.kws.core.domain.service.BaseKwsCommandRequest;
import com.otc.kws.core.domain.service.BaseKwsCommandResponse;
import com.otc.kws.core.domain.service.filestore.FileStoreService;
import com.otc.kws.fishsix.lib.domain.entity.Teacher;
import com.otc.kws.fishsix.lib.domain.entity.TeacherSubjectMapper;
import com.otc.kws.fishsix.lib.domain.model.TeacherProfile;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaTeacherRepository;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaTeacherSubjectMapperRepository;

import lombok.Data;

@Component
public class FishsixTeacherProfileBuildCommand extends BaseKwsCommand
{
	@Autowired
	protected JpaTeacherRepository teacherRepository;
	
	@Autowired
	protected JpaTeacherSubjectMapperRepository teacherSubjectMapperRepository;
	
	@Autowired
	protected JpaUserAccountRepository userAccountRepository;
	
	@Autowired
	protected JpaUserRoleMapperRepository userRoleMapperRepository;
	
	@Autowired
	protected FileStoreService fileStoreService;
	
	
	public Response buildTeacherProfile(Request request)
	{
		logger.info("### {}.buildTeacherProfile ###", getClass().getSimpleName());
		
		Response response = new Response();
		
		Teacher teacher = teacherRepository.findById(request.getTeacherId()).orElse(null);
		
		if(teacher != null) {
			List<TeacherSubjectMapper> teacherSubjectMappers = teacherSubjectMapperRepository.findByTeacherId(teacher.getId());
			UserAccount userAccount = userAccountRepository.findById(teacher.getUserId()).orElse(null);
			List<UserRoleMapper> userRoleMappers = userRoleMapperRepository.findByUserId(teacher.getUserId());
			
			TeacherProfile teacherProfile = new TeacherProfile();
			teacherProfile.setTeacher(teacher);
			teacherProfile.setTeacherSubjectMappers(teacherSubjectMappers);
			teacherProfile.setUserAccount(userAccount);
			teacherProfile.setUserRoleMappers(userRoleMappers);
			
			if(teacher.getIdcardFileURI() != null) {
				String fileURL = fileStoreService.getURL(req -> {
					req.copyFrom(request);
					req.setFileURI(teacher.getIdcardFileURI());
				}).getUrl();
				
				if(fileURL != null) {
					TeacherProfile.Attachment attachment = new TeacherProfile.Attachment();
					attachment.setIdCardFileURL(fileURL);
					teacherProfile.setAttachment(attachment);
				}
			}
			
			response.setTeacherProfile(teacherProfile);
		}
		
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
		protected TeacherProfile teacherProfile;
	}
}