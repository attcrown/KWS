package com.otc.kws.fishsix.lib.domain.service.teacher.file;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.otc.kws.core.domain.model.FileData;
import com.otc.kws.core.domain.service.BaseKwsCommand;
import com.otc.kws.core.domain.service.BaseKwsCommandRequest;
import com.otc.kws.core.domain.service.BaseKwsCommandResponse;
import com.otc.kws.core.domain.service.filestore.FileStoreService;
import com.otc.kws.fishsix.lib.domain.constant.KwsFishsixConst;
import com.otc.kws.fishsix.lib.domain.entity.Teacher;
import com.otc.kws.fishsix.lib.domain.model.TeacherProfile;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaTeacherRepository;
import com.otc.kws.fishsix.lib.domain.service.teacher.FishsixTeacherService;

import lombok.Data;

@Component
public class FishsixTeacherResumeFileUploadCommand extends BaseKwsCommand
{
	public static final String MODULE = KwsFishsixConst.MODULE_NAME;
	public static final String FILE_CATEGORY = "teacher";
	public static final String FILE_GROUP = "resume_file";
	
	
	@Autowired
	protected JpaTeacherRepository teacherRepository;
	
	@Autowired
	protected FishsixTeacherService teacherService;
	
	@Autowired
	protected FileStoreService fileStoreService;
	
	
	public Response uploadResumeFile(Request request)
	{
		logger.info("### {}.uploadResumeFile ###", getClass().getSimpleName());
		
		Response response = new Response();
		
		Teacher teacher = teacherRepository.findById(request.getTeacherId()).orElse(null);
		
		if(teacher == null) {
			return response;
		}
		
		logger.debug("resumeFileData.size => {}", request.getResumeFileData().getSize());
		
		String fileURI = fileStoreService.putFile(req -> {
			req.copyFrom(request);
			
			Map<String, String> variables = new HashMap<>();
			variables.put("teacher_id", request.getTeacherId());
			
			req.setModule(MODULE);
			req.setFileCategory(FILE_CATEGORY);
			req.setFileGroup(FILE_GROUP);
			req.setSeqNo(1);
			req.setVariables(variables);
			req.setFileData(request.getResumeFileData());
		}).getUri();
		
		logger.debug("fileURI => {}", fileURI);
		
		if(fileURI != null) {
			teacher.setResumeFileURI(fileURI);
			teacher.setLastModifiedBy(request.getPerformedBy());
			teacher.setLastModifiedAt(request.getPerformedAt());
			teacherRepository.save(teacher);
			logger.info("update teacher => {}", teacher);
		}
		
		TeacherProfile teacherProfile = teacherService.buildTeacherProfile(req -> {
			req.copyFrom(request);
			req.setTeacherId(request.getTeacherId());
		}).getTeacherProfile();
		
		response.setTeacherProfile(teacherProfile);
		
		return response;
	}
	
	
	@Data
	public static class Request extends BaseKwsCommandRequest
	{
		protected String teacherId;
		protected FileData resumeFileData;
	}
	
	
	@Data
	public static class Response extends BaseKwsCommandResponse
	{
		protected TeacherProfile teacherProfile;
	}
}