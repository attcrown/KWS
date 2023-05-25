package com.otc.kws.fishsix.lib.gateway.web.api.subject;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.otc.kws.core.domain.constant.value.MasterStatusValue;
import com.otc.kws.core.domain.exception.KwsRuntimeException;
import com.otc.kws.core.gateway.web.api.ResponseMessage;
import com.otc.kws.fishsix.lib.domain.entity.Subject;
import com.otc.kws.fishsix.lib.domain.model.SubjectProfile;
import com.otc.kws.fishsix.lib.domain.service.subject.FishsixSubjectService;
import com.otc.kws.fishsix.lib.gateway.web.api.BaseKwsFishsixAPI;

import lombok.Data;

@RestController
@RequestMapping(BaseKwsFishsixAPI.PREFIX_PATH + "/subject/create")
public class FishsixSubjectCreateAPI extends BaseKwsFishsixAPI
{
	@Autowired
	protected FishsixSubjectService subjectService;
	
	
	@PostMapping
	public ResponseEntity<?> createSubject(HttpServletRequest request, @ModelAttribute RequestMessage requestMessage) throws Exception
	{
		logger.info("### {}.createSubject ###", getClass().getSimpleName());
		logger.info("requestMessage => {}", requestMessage);
		
		Subject subject = new Subject();
		subject.setId(requestMessage.getId());
		subject.setSubjectCategoryId(requestMessage.getSubjectCategoryId());
		subject.setName(requestMessage.getName());
		subject.setTitle(requestMessage.getTitle());
		subject.setDescription(requestMessage.getDescription());
		subject.setEducationClassId(requestMessage.getEducationClassId());
		subject.setEducationLevelId(requestMessage.getEducationLevelId());
		subject.setClassLevel(requestMessage.getClassLevel());
		subject.setRemark(requestMessage.getRemark());
		subject.setStatus(requestMessage.getStatus());
		
		SubjectProfile createdSubjectProfile = subjectService.createSubject(req -> {
			setupServiceRequest(request, req);
			req.setSubject(subject);
			
			if(requestMessage.getThumbnailImage() != null) {
				try {
					req.setThumbnailImageFileData(buildFileData(requestMessage.getThumbnailImage()));
				} catch(Exception ex) {
					throw new KwsRuntimeException(ex);
				}
			}
		}).getCreatedSubjectProfile();
		
		BodyResponseMessage bodyResponseMessage = new BodyResponseMessage();
		bodyResponseMessage.setCreatedSubjectProfile(createdSubjectProfile);
		
		return replySuccess(request, bodyResponseMessage);
	}
	
	
	@Data
	public static class RequestMessage 
	{
		protected String id;
		protected String subjectCategoryId;
		protected String name;
		protected String title;
		protected String description;
		protected String educationClassId;
		protected String educationLevelId;
		protected int classLevel;
		protected String remark;
		protected MasterStatusValue status;
		protected MultipartFile thumbnailImage;
	}
	
	
	@Data
	public static class BodyResponseMessage extends ResponseMessage.BaseBody
	{
		protected SubjectProfile createdSubjectProfile;
	}
}