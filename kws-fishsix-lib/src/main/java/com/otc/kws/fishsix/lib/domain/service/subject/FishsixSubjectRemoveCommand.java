package com.otc.kws.fishsix.lib.domain.service.subject;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.otc.kws.core.domain.constant.value.MasterStatusValue;
import com.otc.kws.core.domain.exception.KwsRuntimeException;
import com.otc.kws.core.domain.service.BaseKwsCommand;
import com.otc.kws.core.domain.service.BaseKwsCommandRequest;
import com.otc.kws.core.domain.service.BaseKwsCommandResponse;
import com.otc.kws.fishsix.lib.domain.entity.Subject;
import com.otc.kws.fishsix.lib.domain.entity.SubjectChapter;
import com.otc.kws.fishsix.lib.domain.entity.SubjectChapterMedia;
import com.otc.kws.fishsix.lib.domain.model.SubjectProfile;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaSubjectChapterMediaRepository;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaSubjectChapterRepository;
import com.otc.kws.fishsix.lib.domain.repository.jpa.JpaSubjectRepository;

import lombok.Data;

@Component
public class FishsixSubjectRemoveCommand extends BaseKwsCommand
{
	@Autowired
	protected JpaSubjectRepository subjectRepository;
	
	@Autowired
	protected JpaSubjectChapterRepository subjectChapterRepository;
	
	@Autowired
	protected JpaSubjectChapterMediaRepository subjectChapterMediaRepository;
	
	@Autowired
	protected FishsixSubjectService subjectService;
	
	
	public Response removeSubject(Request request)
	{
		logger.info("### {}.removeSubject ###", getClass().getSimpleName());
		
		Response response = new Response();
		
		Subject subject = subjectRepository.findById(request.getSubjectId()).orElse(null);
		
		if(subject == null) {
			throw new KwsRuntimeException("Subject not found");
		}
		
		subject.setStatus(MasterStatusValue.Closed);
		subject.setLastModifiedBy(request.getPerformedBy());
		subject.setLastModifiedAt(request.getPerformedAt());
		subjectRepository.save(subject);
		logger.info("update subject => {}", subject);
		
		List<SubjectChapter> subjectChapters = subjectChapterRepository.findBySubjectId(subject.getId());
		if(subjectChapters != null && !subjectChapters.isEmpty()) {
			for(SubjectChapter subjectChapter : subjectChapters) {
				subjectChapter.setStatus(MasterStatusValue.Closed);
				subjectChapter.setLastModifiedBy(request.getPerformedBy());
				subjectChapter.setLastModifiedAt(request.getPerformedAt());
				subjectChapterRepository.save(subjectChapter);
				logger.info("update subjectChapter => {}", subjectChapter);
			}
			
			List<String> chapterIds = subjectChapters.stream().map(SubjectChapter::getId).distinct().collect(Collectors.toList());
			List<SubjectChapterMedia> medias = subjectChapterMediaRepository.findByChapterIds(chapterIds);
			if(medias != null && !medias.isEmpty()) {
				for(SubjectChapterMedia media : medias) {
					media.setStatus(MasterStatusValue.Closed);
					media.setLastModifiedBy(request.getPerformedBy());
					media.setLastModifiedAt(request.getPerformedAt());
					subjectChapterMediaRepository.save(media);
					logger.info("update subjectChapterMedia => {}", media);
				}
			}
		}
		
		SubjectProfile removedSubjectProfile = subjectService.buildSubjectProfile(req -> {
			req.copyFrom(request);
			req.setSubjectId(request.getSubjectId());
		}).getSubjectProfile();
		
		response.setRemovedSubjectProfile(removedSubjectProfile);
		
		return response;
	}
	
	
	@Data
	public static class Request extends BaseKwsCommandRequest
	{
		protected String subjectId;
	}
	
	
	@Data
	public static class Response extends BaseKwsCommandResponse
	{
		protected SubjectProfile removedSubjectProfile;
	}
}