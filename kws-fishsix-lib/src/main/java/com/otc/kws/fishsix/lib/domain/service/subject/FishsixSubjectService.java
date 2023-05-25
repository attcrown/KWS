package com.otc.kws.fishsix.lib.domain.service.subject;

import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.otc.kws.core.domain.service.BaseKwsService;
import com.otc.kws.fishsix.lib.domain.service.subject.chapter.FishsixSubjectChapterCreateCommand;
import com.otc.kws.fishsix.lib.domain.service.subject.chapter.FishsixSubjectChapterRemoveCommand;
import com.otc.kws.fishsix.lib.domain.service.subject.chapter.FishsixSubjectChapterUpdateCommand;
import com.otc.kws.fishsix.lib.domain.service.subject.chapter.media.FishsixSubjectChapterMediaCreateCommand;
import com.otc.kws.fishsix.lib.domain.service.subject.chapter.media.FishsixSubjectChapterMediaRemoveCommand;
import com.otc.kws.fishsix.lib.domain.service.subject.chapter.media.FishsixSubjectChapterMediaUpdateCommand;

@Service
public class FishsixSubjectService extends BaseKwsService
{
	@Autowired
	protected FishsixSubjectBuildProfileCommand subjectBuildProfileCommand;
	
	@Autowired
	protected FishsixSubjectFetchCommand subjectFetchCommand;
	
	@Autowired
	protected FishsixSubjectCreateCommand subjectCreateCommand;
	
	@Autowired
	protected FishsixSubjectUpdateCommand subjectUpdateCommand;
	
	@Autowired
	protected FishsixSubjectRemoveCommand subjectRemoveCommand;
	
	@Autowired
	protected FishsixSubjectChapterCreateCommand subjectChapterCreateCommand;
	
	@Autowired
	protected FishsixSubjectChapterUpdateCommand subjectChapterUpdateCommand;
	
	@Autowired
	protected FishsixSubjectChapterRemoveCommand subjectChapterRemoveCommand;
	
	@Autowired
	protected FishsixSubjectChapterMediaCreateCommand subjectChapterMediaCreateCommand;
	
	@Autowired
	protected FishsixSubjectChapterMediaUpdateCommand subjectChapterMediaUpdateCommand;
	
	@Autowired
	protected FishsixSubjectChapterMediaRemoveCommand subjectChapterMediaRemoveCommand;
	
	
	// ****************************** buildSubjectProfile ****************************** //
	public FishsixSubjectBuildProfileCommand.Response buildSubjectProfile(Consumer<FishsixSubjectBuildProfileCommand.Request> consumer)
	{
		FishsixSubjectBuildProfileCommand.Request request = new FishsixSubjectBuildProfileCommand.Request();
		consumer.accept(request);
		return buildSubjectProfile(request);
	}
	
	public FishsixSubjectBuildProfileCommand.Response buildSubjectProfile(FishsixSubjectBuildProfileCommand.Request request)
	{
		logger.info("### {}.buildSubjectProfile ###", getClass().getSimpleName());
		return subjectBuildProfileCommand.buildSubjectProfile(request);
	}
	// ****************************** buildSubjectProfile ****************************** //
	
	
	// ****************************** fetchSubject ****************************** //
	public FishsixSubjectFetchCommand.Response fetchSubject(Consumer<FishsixSubjectFetchCommand.Request> consumer)
	{
		FishsixSubjectFetchCommand.Request request = new FishsixSubjectFetchCommand.Request();
		consumer.accept(request);
		return fetchSubject(request);
	}
	
	public FishsixSubjectFetchCommand.Response fetchSubject(FishsixSubjectFetchCommand.Request request)
	{
		logger.info("### {}.fetchSubject ###", getClass().getSimpleName());
		return subjectFetchCommand.fetchSubject(request);
	}
	// ****************************** fetchSubject ****************************** //
	
	
	// ****************************** createSubject ****************************** //
	@Transactional
	public FishsixSubjectCreateCommand.Response createSubject(Consumer<FishsixSubjectCreateCommand.Request> consumer)
	{
		FishsixSubjectCreateCommand.Request request = new FishsixSubjectCreateCommand.Request();
		consumer.accept(request);
		return createSubject(request);
	}
	
	@Transactional
	public FishsixSubjectCreateCommand.Response createSubject(FishsixSubjectCreateCommand.Request request)
	{
		logger.info("### {}.createSubject ###", getClass().getSimpleName());
		return subjectCreateCommand.createSubject(request);
	}
	// ****************************** createSubject ****************************** //
	
	
	// ****************************** updateSubject ****************************** //
	@Transactional
	public FishsixSubjectUpdateCommand.Response updateSubject(Consumer<FishsixSubjectUpdateCommand.Request> consumer)
	{
		FishsixSubjectUpdateCommand.Request request = new FishsixSubjectUpdateCommand.Request();
		consumer.accept(request);
		return updateSubject(request);
	}
	
	@Transactional
	public FishsixSubjectUpdateCommand.Response updateSubject(FishsixSubjectUpdateCommand.Request request)
	{
		logger.info("### {}.updateSubject ###", getClass().getSimpleName());
		return subjectUpdateCommand.updateSubject(request);
	}
	// ****************************** updateSubject ****************************** //
	
	
	// ****************************** removeSubject ****************************** //
	@Transactional
	public FishsixSubjectRemoveCommand.Response removeSubject(Consumer<FishsixSubjectRemoveCommand.Request> consumer)
	{
		FishsixSubjectRemoveCommand.Request request = new FishsixSubjectRemoveCommand.Request();
		consumer.accept(request);
		return removeSubject(request);
	}
	
	@Transactional
	public FishsixSubjectRemoveCommand.Response removeSubject(FishsixSubjectRemoveCommand.Request request)
	{
		logger.info("### {}.removeSubject ###", getClass().getSimpleName());
		return subjectRemoveCommand.removeSubject(request);
	}
	// ****************************** removeSubject ****************************** //
	
	
	// ****************************** createSubjectChapter ****************************** //
	@Transactional
	public FishsixSubjectChapterCreateCommand.Response createSubjectChapter(Consumer<FishsixSubjectChapterCreateCommand.Request> consumer)
	{
		FishsixSubjectChapterCreateCommand.Request request = new FishsixSubjectChapterCreateCommand.Request();
		consumer.accept(request);
		return createSubjectChapter(request);
	}
	
	@Transactional
	public FishsixSubjectChapterCreateCommand.Response createSubjectChapter(FishsixSubjectChapterCreateCommand.Request request)
	{
		logger.info("### {}.createSubjectChapter ###", getClass().getSimpleName());
		return subjectChapterCreateCommand.createSubjectChapter(request);
	}
	// ****************************** createSubjectChapter ****************************** //
	
	
	// ****************************** updateSubjectChapter ****************************** //
	@Transactional
	public FishsixSubjectChapterUpdateCommand.Response updateSubjectChapter(Consumer<FishsixSubjectChapterUpdateCommand.Request> consumer)
	{
		FishsixSubjectChapterUpdateCommand.Request request = new FishsixSubjectChapterUpdateCommand.Request();
		consumer.accept(request);
		return updateSubjectChapter(request);
	}
	
	@Transactional
	public FishsixSubjectChapterUpdateCommand.Response updateSubjectChapter(FishsixSubjectChapterUpdateCommand.Request request)
	{
		logger.info("### {}.updateSubjectChapter ###", getClass().getSimpleName());
		return subjectChapterUpdateCommand.updateSubjectChapter(request);
	}
	// ****************************** updateSubjectChapter ****************************** //
	
	
	// ****************************** removeSubjectChapter ****************************** //
	@Transactional
	public FishsixSubjectChapterRemoveCommand.Response removeSubjectChapter(Consumer<FishsixSubjectChapterRemoveCommand.Request> consumer)
	{
		FishsixSubjectChapterRemoveCommand.Request request = new FishsixSubjectChapterRemoveCommand.Request();
		consumer.accept(request);
		return removeSubjectChapter(request);
	}
	
	@Transactional
	public FishsixSubjectChapterRemoveCommand.Response removeSubjectChapter(FishsixSubjectChapterRemoveCommand.Request request)
	{
		logger.info("### {}.removeSubjectChapter ###", getClass().getSimpleName());
		return subjectChapterRemoveCommand.removeSubjectChapter(request);
	}
	// ****************************** removeSubjectChapter ****************************** //
	
	
	
	
	// ****************************** createSubjectChapterMedia ****************************** //
	@Transactional
	public FishsixSubjectChapterMediaCreateCommand.Response createSubjectChapterMedia(Consumer<FishsixSubjectChapterMediaCreateCommand.Request> consumer)
	{
		FishsixSubjectChapterMediaCreateCommand.Request request = new FishsixSubjectChapterMediaCreateCommand.Request();
		consumer.accept(request);
		return createSubjectChapterMedia(request);
	}
	
	@Transactional
	public FishsixSubjectChapterMediaCreateCommand.Response createSubjectChapterMedia(FishsixSubjectChapterMediaCreateCommand.Request request)
	{
		logger.info("### {}.createSubjectChapterMedia ###", getClass().getSimpleName());
		return subjectChapterMediaCreateCommand.createSubjectChapterMedia(request);
	}
	// ****************************** createSubjectChapterMedia ****************************** //
	
	
	// ****************************** updateSubjectChapterMedia ****************************** //
	@Transactional
	public FishsixSubjectChapterMediaUpdateCommand.Response updateSubjectChapterMedia(Consumer<FishsixSubjectChapterMediaUpdateCommand.Request> consumer)
	{
		FishsixSubjectChapterMediaUpdateCommand.Request request = new FishsixSubjectChapterMediaUpdateCommand.Request();
		consumer.accept(request);
		return updateSubjectChapterMedia(request);
	}
	
	@Transactional
	public FishsixSubjectChapterMediaUpdateCommand.Response updateSubjectChapterMedia(FishsixSubjectChapterMediaUpdateCommand.Request request)
	{
		logger.info("### {}.updateSubjectChapterMedia ###", getClass().getSimpleName());
		return subjectChapterMediaUpdateCommand.updateSubjectChapterMedia(request);
	}
	// ****************************** updateSubjectChapterMedia ****************************** //
	
	
	// ****************************** removeSubjectChapterMedia ****************************** //
	@Transactional
	public FishsixSubjectChapterMediaRemoveCommand.Response removeSubjectChapterMedia(Consumer<FishsixSubjectChapterMediaRemoveCommand.Request> consumer)
	{
		FishsixSubjectChapterMediaRemoveCommand.Request request = new FishsixSubjectChapterMediaRemoveCommand.Request();
		consumer.accept(request);
		return removeSubjectChapterMedia(request);
	}
	
	@Transactional
	public FishsixSubjectChapterMediaRemoveCommand.Response removeSubjectChapterMedia(FishsixSubjectChapterMediaRemoveCommand.Request request)
	{
		logger.info("### {}.removeSubjectChapterMedia ###", getClass().getSimpleName());
		return subjectChapterMediaRemoveCommand.removeSubjectChapterMedia(request);
	}
	// ****************************** removeSubjectChapterMedia ****************************** //
}