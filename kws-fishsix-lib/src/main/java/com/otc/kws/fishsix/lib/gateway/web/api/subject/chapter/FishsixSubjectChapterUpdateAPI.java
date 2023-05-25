package com.otc.kws.fishsix.lib.gateway.web.api.subject.chapter;

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
import com.otc.kws.fishsix.lib.domain.entity.SubjectChapter;
import com.otc.kws.fishsix.lib.domain.model.SubjectProfile;
import com.otc.kws.fishsix.lib.domain.service.subject.FishsixSubjectService;
import com.otc.kws.fishsix.lib.gateway.web.api.BaseKwsFishsixAPI;

import lombok.Data;

@RestController
@RequestMapping(BaseKwsFishsixAPI.PREFIX_PATH + "/subject/chapter/update")
public class FishsixSubjectChapterUpdateAPI extends BaseKwsFishsixAPI
{
	@Autowired
	protected FishsixSubjectService subjectService;
	
	
	@PostMapping
	public ResponseEntity<ResponseMessage<FishsixSubjectChapterUpdateAPI.BodyResponseMessage>> updateSubjectChapter(HttpServletRequest request, @ModelAttribute RequestMessage requestMessage) throws Exception
	{
		logger.info("### {}.createSubjectChapter ###", getClass().getSimpleName());
		logger.info("requestMessage => {}", requestMessage);
		
		SubjectChapter subjectChapter = new SubjectChapter();
		subjectChapter.setId(requestMessage.getId());
		subjectChapter.setSubjectId(requestMessage.getSubjectId());
		subjectChapter.setSeqNo(requestMessage.getSeqNo());
		subjectChapter.setName(requestMessage.getName());
		subjectChapter.setDescription(requestMessage.getDescription());
		subjectChapter.setSemaster(requestMessage.getSemaster());
		subjectChapter.setRemark(requestMessage.getRemark());
		subjectChapter.setStatus(requestMessage.getStatus());
		
		SubjectProfile.SubjectChapter updatedSubjectChapter = subjectService.updateSubjectChapter(req -> {
			setupServiceRequest(request, req);
			req.setSubjectChapter(subjectChapter);
			if(requestMessage.getThumbnailImage() != null) {
				try {
					req.setThumbnailImageFileData(buildFileData(requestMessage.getThumbnailImage()));
				} catch(Exception ex) {
					throw new KwsRuntimeException(ex);
				}
			}
		}).getUpdatedSubjectChapter();
		
		BodyResponseMessage bodyResponseMessage = new BodyResponseMessage();
		bodyResponseMessage.setUpdatedSubjectChapter(updatedSubjectChapter);
		
		return replySuccess(request, bodyResponseMessage);
	}
	
	
	@Data
	public static class RequestMessage 
	{
		protected String id;
		protected String subjectId;
		protected int seqNo;
		protected String name;
		protected String description;
		protected int semaster;
		protected String remark;
		protected MasterStatusValue status;
		protected MultipartFile thumbnailImage;
	}
	
	
	@Data
	public static class BodyResponseMessage extends ResponseMessage.BaseBody
	{
		protected SubjectProfile.SubjectChapter updatedSubjectChapter;
	}
}