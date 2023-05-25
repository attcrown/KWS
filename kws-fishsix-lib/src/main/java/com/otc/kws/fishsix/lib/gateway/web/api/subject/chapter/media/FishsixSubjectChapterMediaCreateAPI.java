package com.otc.kws.fishsix.lib.gateway.web.api.subject.chapter.media;

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
import com.otc.kws.fishsix.lib.domain.entity.SubjectChapterMedia;
import com.otc.kws.fishsix.lib.domain.model.SubjectProfile;
import com.otc.kws.fishsix.lib.domain.service.subject.FishsixSubjectService;
import com.otc.kws.fishsix.lib.gateway.web.api.BaseKwsFishsixAPI;

import lombok.Data;

@RestController
@RequestMapping(BaseKwsFishsixAPI.PREFIX_PATH + "/subject/chapter/media/create")
public class FishsixSubjectChapterMediaCreateAPI extends BaseKwsFishsixAPI
{
	@Autowired
	protected FishsixSubjectService subjectService;
	
	
	@PostMapping
	public ResponseEntity<?> createSubjectChapterMedia(HttpServletRequest request, @ModelAttribute RequestMessage requestMessage) throws Exception
	{
		logger.info("### {}.createSubjectChapterMedia ###", getClass().getSimpleName());
		logger.info("requestMessage => {}", requestMessage);
		
		SubjectChapterMedia subjectChapterMedia = new SubjectChapterMedia();
		subjectChapterMedia.setId(requestMessage.getId());
		subjectChapterMedia.setSubjectId(requestMessage.getSubjectId());
		subjectChapterMedia.setChapterId(requestMessage.getChapterId());
		subjectChapterMedia.setMediaType(requestMessage.getMediaType());
		subjectChapterMedia.setMediaSourceURI(requestMessage.getMediaSourceURI());
		subjectChapterMedia.setTitle(requestMessage.getTitle());
		subjectChapterMedia.setDescription(requestMessage.getDescription());
		subjectChapterMedia.setSeqNo(requestMessage.getSeqNo());
		subjectChapterMedia.setRemark(requestMessage.getRemark());
		subjectChapterMedia.setStatus(requestMessage.getStatus());
		
		SubjectProfile.SubjectChapterMedia createdSubjectChapterMedia = subjectService.createSubjectChapterMedia(req -> {
			setupServiceRequest(request, req);
			req.setSubjectChapterMedia(subjectChapterMedia);
			if(requestMessage.getMediaSourceFile() != null) {
				try {
					req.setMediaSourceFileData(buildFileData(requestMessage.getMediaSourceFile()));
				} catch(Exception ex) {
					throw new KwsRuntimeException(ex);
				}
			}
		}).getCreatedSubjectChapterMedia();
		
		BodyResponseMessage bodyResponseMessage = new BodyResponseMessage();
		bodyResponseMessage.setCreatedSubjectChapterMedia(createdSubjectChapterMedia);
		
		return replySuccess(request, bodyResponseMessage);
	}
	
	
	@Data
	public static class RequestMessage 
	{
		protected String id;
		protected String subjectId;
		protected String chapterId;
		protected SubjectChapterMedia.MediaType mediaType;
		protected String mediaSourceURI;
		protected String title;
		protected String description;
		protected int seqNo;
		protected String remark;
		protected MasterStatusValue status;
		protected MultipartFile mediaSourceFile;
	}
	
	
	@Data
	public static class BodyResponseMessage extends ResponseMessage.BaseBody
	{
		protected SubjectProfile.SubjectChapterMedia createdSubjectChapterMedia;
	}
}