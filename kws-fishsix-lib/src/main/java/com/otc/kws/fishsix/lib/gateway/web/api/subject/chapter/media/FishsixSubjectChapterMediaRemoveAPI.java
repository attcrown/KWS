package com.otc.kws.fishsix.lib.gateway.web.api.subject.chapter.media;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.otc.kws.core.gateway.web.api.ResponseMessage;
import com.otc.kws.fishsix.lib.domain.entity.SubjectChapterMedia;
import com.otc.kws.fishsix.lib.domain.service.subject.FishsixSubjectService;
import com.otc.kws.fishsix.lib.gateway.web.api.BaseKwsFishsixAPI;

import lombok.Data;

@RestController
@RequestMapping(BaseKwsFishsixAPI.PREFIX_PATH + "/subject/chapter/media/remove")
public class FishsixSubjectChapterMediaRemoveAPI extends BaseKwsFishsixAPI
{
	@Autowired
	protected FishsixSubjectService subjectService;
	
	
	@PostMapping
	public ResponseEntity<?> removeSubjectChapterMedia(HttpServletRequest request, @RequestBody RequestMessage requestMessage) throws Exception
	{
		logger.info("### {}.removeSubjectChapterMedia ###", getClass().getSimpleName());
		logger.info("requestMessage => {}", requestMessage);
		
		SubjectChapterMedia removedSubjectChapterMedia = subjectService.removeSubjectChapterMedia(req -> {
			setupServiceRequest(request, req);
			req.setSubjectChapterMediaId(requestMessage.getSubjectChapterMediaId());
		}).getRemovedSubjectChapterMedia();
		
		BodyResponseMessage bodyResponseMessage = new BodyResponseMessage();
		bodyResponseMessage.setRemovedSubjectChapterMedia(removedSubjectChapterMedia);
		
		return replySuccess(request, bodyResponseMessage);
	}
	
	
	@Data
	public static class RequestMessage 
	{
		protected String subjectChapterMediaId;
	}
	
	
	@Data
	public static class BodyResponseMessage extends ResponseMessage.BaseBody
	{
		protected SubjectChapterMedia removedSubjectChapterMedia;
	}
}