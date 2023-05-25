package com.otc.kws.fishsix.lib.domain.service.teacher.file;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.otc.kws.core.domain.service.BaseKwsCommand;
import com.otc.kws.core.domain.service.BaseKwsCommandRequest;
import com.otc.kws.core.domain.service.BaseKwsCommandResponse;
import com.otc.kws.core.domain.service.filestore.FileStoreService;
import com.otc.kws.fishsix.lib.domain.entity.Teacher;
import com.otc.kws.fishsix.lib.domain.model.TeacherProfile;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaTeacherRepository;
import com.otc.kws.fishsix.lib.domain.service.teacher.FishsixTeacherService;

import lombok.Data;

@Component
public class FishsixTeacherResumeFileRemoveCommand extends BaseKwsCommand
{
	@Autowired
	protected JpaTeacherRepository teacherRepository;
	
	@Autowired
	protected FishsixTeacherService teacherService;
	
	@Autowired
	protected FileStoreService fileStoreService;
	
	
	public Response removeResumeFile(Request request)
	{
		logger.info("### {}.removeResumeFile ###", getClass().getSimpleName());
		
		Response response = new Response();
		
		Teacher teacher = request.getTeacher();
		
		if(teacher == null) {
			teacher = teacherRepository.findById(request.getTeacherId()).orElse(null);
		}
		
		if(teacher != null && teacher.getResumeFileURI() != null) {
			String teacherId = teacher.getId();
			String fileURI = teacher.getResumeFileURI();
			
			fileStoreService.removeFile(req -> {
				req.copyFrom(request);
				req.setFileURI(fileURI);
			});
			
			teacher.setResumeFileURI(null);
			teacher.setLastModifiedBy(request.getPerformedBy());
			teacher.setLastModifiedAt(request.getPerformedAt());
			teacherRepository.save(teacher);
			logger.info("update teacher => {}", teacher);
			
			TeacherProfile teacherProfile = teacherService.buildTeacherProfile(req -> {
				req.copyFrom(request);
				req.setTeacherId(teacherId);
			}).getTeacherProfile();
			
			response.setTeacherProfile(teacherProfile);
		}
		
		return response;
	}
	
	
	@Data
	public static class Request extends BaseKwsCommandRequest
	{
		protected String teacherId;
		protected Teacher teacher;
	}
	
	
	@Data
	public static class Response extends BaseKwsCommandResponse
	{
		protected TeacherProfile teacherProfile;
	}
}