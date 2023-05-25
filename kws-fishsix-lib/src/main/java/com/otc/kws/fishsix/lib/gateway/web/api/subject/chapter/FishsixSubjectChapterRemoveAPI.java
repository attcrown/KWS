package com.otc.kws.fishsix.lib.gateway.web.api.subject.chapter;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.otc.kws.core.gateway.web.api.ResponseMessage;
import com.otc.kws.fishsix.lib.domain.entity.SubjectChapter;
import com.otc.kws.fishsix.lib.domain.service.subject.FishsixSubjectService;
import com.otc.kws.fishsix.lib.gateway.web.api.BaseKwsFishsixAPI;

import lombok.Data;

@RestController
@RequestMapping(BaseKwsFishsixAPI.PREFIX_PATH + "/subject/chapter/remove")
public class FishsixSubjectChapterRemoveAPI extends BaseKwsFishsixAPI
{
	@Autowired
	protected FishsixSubjectService subjectService;
	
	
	@PostMapping
	public ResponseEntity<?> removeSubjectChapter(HttpServletRequest request, @RequestBody RequestMessage requestMessage) throws Exception
	{
		logger.info("### {}.removeSubjectChapter ###", getClass().getSimpleName());
		logger.info("requestMessage => {}", requestMessage);
		
		SubjectChapter removedSubjectChapter = subjectService.removeSubjectChapter(req -> {
			setupServiceRequest(request, req);
			req.setSubjectChapterId(requestMessage.getSubjectChapterId());
		}).getRemovedSubjectChapter();
		
		BodyResponseMessage bodyResponseMessage = new BodyResponseMessage();
		bodyResponseMessage.setRemovedSubjectChapter(removedSubjectChapter);
		
		return replySuccess(request, bodyResponseMessage);
	}
	
	
	@Data
	public static class RequestMessage 
	{
		protected String subjectChapterId;
	}
	
	
	@Data
	public static class BodyResponseMessage extends ResponseMessage.BaseBody
	{
		protected SubjectChapter removedSubjectChapter;
	}
}