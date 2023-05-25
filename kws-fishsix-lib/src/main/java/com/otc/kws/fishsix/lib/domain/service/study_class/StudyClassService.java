package com.otc.kws.fishsix.lib.domain.service.study_class;

import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.otc.kws.core.domain.service.BaseKwsService;
import com.otc.kws.fishsix.lib.domain.service.study_class.evaluation.StudyClassEvaluationApproveCommand;
import com.otc.kws.fishsix.lib.domain.service.study_class.evaluation.StudyClassEvaluationCreateCommand;
import com.otc.kws.fishsix.lib.domain.service.study_class.evaluation.StudyClassEvaluationDeleteCommand;
import com.otc.kws.fishsix.lib.domain.service.study_class.evaluation.StudyClassEvaluationLoadCommand;
import com.otc.kws.fishsix.lib.domain.service.study_class.evaluation.StudyClassEvaluationRejectCommand;
import com.otc.kws.fishsix.lib.domain.service.study_class.evaluation.StudyClassEvaluationUpdateCommand;
import com.otc.kws.fishsix.lib.domain.service.study_class.schedule.StudyClassScheduleCheckinCommand;
import com.otc.kws.fishsix.lib.domain.service.study_class.schedule.StudyClassScheduleConfirmCommand;
import com.otc.kws.fishsix.lib.domain.service.study_class.schedule.StudyClassScheduleCreateCommand;
import com.otc.kws.fishsix.lib.domain.service.study_class.schedule.StudyClassScheduleProfileLoadCommand;
import com.otc.kws.fishsix.lib.domain.service.study_class.schedule.StudyClassScheduleRemoveCommand;

@Service
public class StudyClassService extends BaseKwsService
{
	@Autowired
	protected StudyClassCreateCommand studyClassCreateCommand;
	
	@Autowired
	protected StudyClassScheduleProfileLoadCommand studyClassScheduleProfileLoadCommand;
	
	@Autowired
	protected StudyClassScheduleCreateCommand studyClassScheduleCreateCommand;
	
	@Autowired
	protected StudyClassScheduleRemoveCommand studyClassScheduleRemoveCommand;
	
	@Autowired
	protected StudyClassScheduleCheckinCommand studyClassScheduleCheckinCommand;
	
	@Autowired
	protected StudyClassScheduleConfirmCommand studyClassScheduleConfirmCommand;
	
	@Autowired
	protected StudyClassEvaluationLoadCommand studyClassEvaluationLoadCommand;
	
	@Autowired
	protected StudyClassEvaluationCreateCommand studyClassEvaluationCreateCommand;
	
	@Autowired
	protected StudyClassEvaluationUpdateCommand studyClassEvaluationUpdateCommand;
	
	@Autowired
	protected StudyClassEvaluationDeleteCommand studyClassEvaluationDeleteCommand;
	
	@Autowired
	protected StudyClassEvaluationApproveCommand studyClassEvaluationApproveCommand;
	
	@Autowired
	protected StudyClassEvaluationRejectCommand studyClassEvaluationRejectCommand;
	
	
	// ****************************** createStudyClass ****************************** //
	@Transactional
	public StudyClassCreateCommand.Response createStudyClass(Consumer<StudyClassCreateCommand.Request> consumer)
	{
		StudyClassCreateCommand.Request request = new StudyClassCreateCommand.Request();
		consumer.accept(request);
		return createStudyClass(request);
	}
	
	@Transactional
	public StudyClassCreateCommand.Response createStudyClass(StudyClassCreateCommand.Request request)
	{
		logger.info("### {}.createStudyClass ###", getClass().getSimpleName());
		return studyClassCreateCommand.createStudyClass(request);
	}
	// ****************************** createStudyClass ****************************** //
	
	
	// ****************************** loadStudyClassScheduleProfile ****************************** //
	public StudyClassScheduleProfileLoadCommand.Response loadStudyClassScheduleProfile(Consumer<StudyClassScheduleProfileLoadCommand.Request> consumer)
	{
		StudyClassScheduleProfileLoadCommand.Request request = new StudyClassScheduleProfileLoadCommand.Request();
		consumer.accept(request);
		return loadStudyClassScheduleProfile(request);
	}
	
	public StudyClassScheduleProfileLoadCommand.Response loadStudyClassScheduleProfile(StudyClassScheduleProfileLoadCommand.Request request)
	{
		logger.info("### {}.loadStudyClassScheduleProfile ###", getClass().getSimpleName());
		return studyClassScheduleProfileLoadCommand.loadStudyClassScheduleProfile(request);
	}
	// ****************************** loadStudyClassScheduleProfile ****************************** //
	
	
	// ****************************** createStudyClassSchedule ****************************** //
	@Transactional
	public StudyClassScheduleCreateCommand.Response createStudyClassSchedule(Consumer<StudyClassScheduleCreateCommand.Request> consumer)
	{
		StudyClassScheduleCreateCommand.Request request = new StudyClassScheduleCreateCommand.Request();
		consumer.accept(request);
		return createStudyClassSchedule(request);
	}
	
	@Transactional
	public StudyClassScheduleCreateCommand.Response createStudyClassSchedule(StudyClassScheduleCreateCommand.Request request)
	{
		logger.info("### {}.createStudyClassSchedule ###", getClass().getSimpleName());
		return studyClassScheduleCreateCommand.createStudyClassSchedule(request);
	}
	// ****************************** createStudyClassSchedule ****************************** //
	
	
	// ****************************** removeStudyClassSchedule ****************************** //
	@Transactional
	public StudyClassScheduleRemoveCommand.Response removeStudyClassSchedule(Consumer<StudyClassScheduleRemoveCommand.Request> consumer)
	{
		StudyClassScheduleRemoveCommand.Request request = new StudyClassScheduleRemoveCommand.Request();
		consumer.accept(request);
		return removeStudyClassSchedule(request);
	}
	
	@Transactional
	public StudyClassScheduleRemoveCommand.Response removeStudyClassSchedule(StudyClassScheduleRemoveCommand.Request request)
	{
		logger.info("### {}.removeStudyClassSchedule ###", getClass().getSimpleName());
		return studyClassScheduleRemoveCommand.removeStudyClassSchedule(request);
	}
	// ****************************** removeStudyClassSchedule ****************************** //
	
	
	// ****************************** checkinStudyClassSchedule ****************************** //
	@Transactional
	public StudyClassScheduleCheckinCommand.Response checkinStudyClassSchedule(Consumer<StudyClassScheduleCheckinCommand.Request> consumer)
	{
		StudyClassScheduleCheckinCommand.Request request = new StudyClassScheduleCheckinCommand.Request();
		consumer.accept(request);
		return checkinStudyClassSchedule(request);
	}
	
	@Transactional
	public StudyClassScheduleCheckinCommand.Response checkinStudyClassSchedule(StudyClassScheduleCheckinCommand.Request request)
	{
		logger.info("### {}.removeStudyClassSchedule ###", getClass().getSimpleName());
		return studyClassScheduleCheckinCommand.checkinStudyClassSchedule(request);
	}
	// ****************************** checkinStudyClassSchedule ****************************** //
	
	
	// ****************************** confirmStudyClassSchedule ****************************** //
	@Transactional
	public StudyClassScheduleConfirmCommand.Response confirmStudyClassSchedule(Consumer<StudyClassScheduleConfirmCommand.Request> consumer)
	{
		StudyClassScheduleConfirmCommand.Request request = new StudyClassScheduleConfirmCommand.Request();
		consumer.accept(request);
		return confirmStudyClassSchedule(request);
	}
	
	@Transactional
	public StudyClassScheduleConfirmCommand.Response confirmStudyClassSchedule(StudyClassScheduleConfirmCommand.Request request)
	{
		logger.info("### {}.confirmStudyClassSchedule ###", getClass().getSimpleName());
		return studyClassScheduleConfirmCommand.confirmStudyClassSchedule(request);
	}
	// ****************************** confirmStudyClassSchedule ****************************** //
	
	
	// ****************************** loadStudyClassEvaluation ****************************** //
	public StudyClassEvaluationLoadCommand.Response loadStudyClassEvaluation(Consumer<StudyClassEvaluationLoadCommand.Request> consumer)
	{
		StudyClassEvaluationLoadCommand.Request request = new StudyClassEvaluationLoadCommand.Request();
		consumer.accept(request);
		return loadStudyClassEvaluation(request);
	}
	
	public StudyClassEvaluationLoadCommand.Response loadStudyClassEvaluation(StudyClassEvaluationLoadCommand.Request request)
	{
		logger.info("### {}.loadStudyClassEvaluation ###", getClass().getSimpleName());
		return studyClassEvaluationLoadCommand.loadStudyClassEvaluation(request);
	}
	// ****************************** loadStudyClassEvaluation ****************************** //
	
	
	// ****************************** createStudyClassEvaluation ****************************** //
	@Transactional
	public StudyClassEvaluationCreateCommand.Response createStudyClassEvaluation(Consumer<StudyClassEvaluationCreateCommand.Request> consumer)
	{
		StudyClassEvaluationCreateCommand.Request request = new StudyClassEvaluationCreateCommand.Request();
		consumer.accept(request);
		return createStudyClassEvaluation(request);
	}
	
	@Transactional
	public StudyClassEvaluationCreateCommand.Response createStudyClassEvaluation(StudyClassEvaluationCreateCommand.Request request)
	{
		logger.info("### {}.createStudyClassEvaluation ###", getClass().getSimpleName());
		return studyClassEvaluationCreateCommand.createStudyClassEvaluation(request);
	}
	// ****************************** createStudyClassEvaluation ****************************** //
	
	
	// ****************************** updateStudyClassEvaluation ****************************** //
	@Transactional
	public StudyClassEvaluationUpdateCommand.Response updateStudyClassEvaluation(Consumer<StudyClassEvaluationUpdateCommand.Request> consumer)
	{
		StudyClassEvaluationUpdateCommand.Request request = new StudyClassEvaluationUpdateCommand.Request();
		consumer.accept(request);
		return updateStudyClassEvaluation(request);
	}
	
	@Transactional
	public StudyClassEvaluationUpdateCommand.Response updateStudyClassEvaluation(StudyClassEvaluationUpdateCommand.Request request)
	{
		logger.info("### {}.updateStudyClassEvaluation ###", getClass().getSimpleName());
		return studyClassEvaluationUpdateCommand.updateStudyClassEvaluation(request);
	}
	// ****************************** updateStudyClassEvaluation ****************************** //
	
	
	// ****************************** deleteStudyClassEvaluation ****************************** //
	@Transactional
	public StudyClassEvaluationDeleteCommand.Response deleteStudyClassEvaluation(Consumer<StudyClassEvaluationDeleteCommand.Request> consumer)
	{
		StudyClassEvaluationDeleteCommand.Request request = new StudyClassEvaluationDeleteCommand.Request();
		consumer.accept(request);
		return deleteStudyClassEvaluation(request);
	}
	
	@Transactional
	public StudyClassEvaluationDeleteCommand.Response deleteStudyClassEvaluation(StudyClassEvaluationDeleteCommand.Request request)
	{
		logger.info("### {}.deleteStudyClassEvaluation ###", getClass().getSimpleName());
		return studyClassEvaluationDeleteCommand.deleteStudyClassEvaluation(request);
	}
	// ****************************** deleteStudyClassEvaluation ****************************** //
	
	
	// ****************************** approveStudyClassEvaluation ****************************** //
	@Transactional
	public StudyClassEvaluationApproveCommand.Response approveStudyClassEvaluation(Consumer<StudyClassEvaluationApproveCommand.Request> consumer)
	{
		StudyClassEvaluationApproveCommand.Request request = new StudyClassEvaluationApproveCommand.Request();
		consumer.accept(request);
		return approveStudyClassEvaluation(request);
	}
	
	@Transactional
	public StudyClassEvaluationApproveCommand.Response approveStudyClassEvaluation(StudyClassEvaluationApproveCommand.Request request)
	{
		logger.info("### {}.approveStudyClassEvaluation ###", getClass().getSimpleName());
		return studyClassEvaluationApproveCommand.approveStudyClassEvaluation(request);
	}
	// ****************************** approveStudyClassEvaluation ****************************** //
	
	
	// ****************************** rejectStudyClassEvaluation ****************************** //
	@Transactional
	public StudyClassEvaluationRejectCommand.Response rejectStudyClassEvaluation(Consumer<StudyClassEvaluationRejectCommand.Request> consumer)
	{
		StudyClassEvaluationRejectCommand.Request request = new StudyClassEvaluationRejectCommand.Request();
		consumer.accept(request);
		return rejectStudyClassEvaluation(request);
	}
	
	@Transactional
	public StudyClassEvaluationRejectCommand.Response rejectStudyClassEvaluation(StudyClassEvaluationRejectCommand.Request request)
	{
		logger.info("### {}.rejectStudyClassEvaluation ###", getClass().getSimpleName());
		return studyClassEvaluationRejectCommand.rejectStudyClassEvaluation(request);
	}
	// ****************************** rejectStudyClassEvaluation ****************************** //
}