package com.otc.kws.fishsix.lib.gateway.web.api.teacher.attachment;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.otc.kws.core.domain.exception.KwsRuntimeException;
import com.otc.kws.core.gateway.web.api.ResponseMessage;
import com.otc.kws.fishsix.lib.domain.model.TeacherProfile;
import com.otc.kws.fishsix.lib.domain.service.teacher.FishsixTeacherService;
import com.otc.kws.fishsix.lib.gateway.web.api.BaseKwsFishsixAPI;

import lombok.Data;

@RestController
@RequestMapping(BaseKwsFishsixAPI.PREFIX_PATH + "/teacher/idcard/upload")
public class FishsixTeacherIdCardFileUploadAPI extends BaseKwsFishsixAPI
{
	@Autowired
	protected FishsixTeacherService teacherService;
	
	
	@PostMapping
	public ResponseEntity<?> uploadIdCardFile(HttpServletRequest request, @ModelAttribute RequestMessage requestMessage) throws Exception
	{
		logger.info("### {}.uploadIdCardFile ###", getClass().getSimpleName());
		logger.info("requestMessage => " + requestMessage);
		
		TeacherProfile teacherProfile = teacherService.uploadIdCardFile(req -> {
			setupServiceRequest(request, req);
			req.setTeacherId(requestMessage.getTeacherId());
			try {
				req.setIdCardFileData(buildFileData(requestMessage.getIdcardFile()));
			} catch(Exception ex) {
				throw new KwsRuntimeException(ex);
			}
		}).getTeacherProfile();
		
		BodyResponseMessage bodyResponseMessage = new BodyResponseMessage();
		bodyResponseMessage.setTeacherProfile(teacherProfile);
		
		return replySuccess(request, bodyResponseMessage);
	}
	
	
	@Data
	public static class RequestMessage
	{
		protected String teacherId;
		protected MultipartFile idcardFile;
	}
	
	
	@Data
	public static class BodyResponseMessage extends ResponseMessage.BaseBody
	{
		protected TeacherProfile teacherProfile;
	}
}