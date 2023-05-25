package com.otc.kws.fishsix.lib.domain.service.student;

import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.otc.kws.core.domain.service.BaseKwsService;
import com.otc.kws.fishsix.lib.domain.service.student.course_quota.StudentCourseQuotaCreateCommand;
import com.otc.kws.fishsix.lib.domain.service.student.course_quota.StudentCourseQuotaInfoLoadCommand;
import com.otc.kws.fishsix.lib.domain.service.student.course_quota.StudentCourseQuotaInitialCommand;
import com.otc.kws.fishsix.lib.domain.service.student.course_quota.StudentCourseQuotaRemoveCommand;
import com.otc.kws.fishsix.lib.domain.service.student.course_quota.StudentCourseQuotaUpdateCommand;

@Service
public class FishsixStudentService extends BaseKwsService
{
	@Lazy
	@Autowired
	protected FishsixStudentFetchCommand studentFetchCommand;
	
	@Lazy
	@Autowired
	protected FishsixStudentCreateCommand studentCreateCommand;
	
	@Lazy
	@Autowired
	protected FishsixStudentUpdateCommand studentUpdateCommand;
	
	@Lazy
	@Autowired
	protected FishsixStudentRemoveCommand studentRemoveCommand;
	
	@Lazy
	@Autowired
	protected StudentCourseQuotaInitialCommand studentCourseQuotaInitialCommand;
	
	@Lazy
	@Autowired
	protected StudentCourseQuotaCreateCommand studentCourseQuotaCreateCommand;
	
	@Lazy
	@Autowired
	protected StudentCourseQuotaUpdateCommand studentCourseQuotaUpdateCommand;
	
	@Lazy
	@Autowired
	protected StudentCourseQuotaRemoveCommand studentCourseQuotaRemoveCommand;
	
	@Lazy
	@Autowired
	protected StudentCourseQuotaInfoLoadCommand studentCourseQuotaInfoLoadCommand;
	
	@Lazy
	@Autowired
	protected FishsixStudentSubjectQuotaLoadCommand studentSubjectQuotaLoadCommand;
	
	
	// ****************************** fetchStudent ****************************** //
	public FishsixStudentFetchCommand.Response fetchStudent(Consumer<FishsixStudentFetchCommand.Request> consumer)
	{
		FishsixStudentFetchCommand.Request request = new FishsixStudentFetchCommand.Request();
		consumer.accept(request);
		return fetchStudent(request);
	}
	
	public FishsixStudentFetchCommand.Response fetchStudent(FishsixStudentFetchCommand.Request request)
	{
		logger.info("### {}.fetchStudent ###", getClass().getSimpleName());
		return studentFetchCommand.fetchStudent(request);
	}
	// ****************************** fetchStudent ****************************** //
	
	
	// ****************************** createStudent ****************************** //
	@Transactional
	public FishsixStudentCreateCommand.Response createStudent(Consumer<FishsixStudentCreateCommand.Request> consumer)
	{
		FishsixStudentCreateCommand.Request request = new FishsixStudentCreateCommand.Request();
		consumer.accept(request);
		return createStudent(request);
	}
	
	@Transactional
	public FishsixStudentCreateCommand.Response createStudent(FishsixStudentCreateCommand.Request request)
	{
		logger.info("### {}.createStudent ###", getClass().getSimpleName());
		return studentCreateCommand.createStudent(request);
	}
	// ****************************** createStudent ****************************** //
	
	
	// ****************************** updateStudent ****************************** //
	@Transactional
	public FishsixStudentUpdateCommand.Response updateStudent(Consumer<FishsixStudentUpdateCommand.Request> consumer)
	{
		FishsixStudentUpdateCommand.Request request = new FishsixStudentUpdateCommand.Request();
		consumer.accept(request);
		return updateStudent(request);
	}
	
	@Transactional
	public FishsixStudentUpdateCommand.Response updateStudent(FishsixStudentUpdateCommand.Request request)
	{
		logger.info("### {}.updateStudent ###", getClass().getSimpleName());
		return studentUpdateCommand.updateStudent(request);
	}
	// ****************************** updateStudent ****************************** //
	
	
	// ****************************** removeStudent ****************************** //
	@Transactional
	public FishsixStudentRemoveCommand.Response removeStudent(Consumer<FishsixStudentRemoveCommand.Request> consumer)
	{
		FishsixStudentRemoveCommand.Request request = new FishsixStudentRemoveCommand.Request();
		consumer.accept(request);
		return removeStudent(request);
	}
	
	@Transactional
	public FishsixStudentRemoveCommand.Response removeStudent(FishsixStudentRemoveCommand.Request request)
	{
		logger.info("### {}.removeStudent ###", getClass().getSimpleName());
		return studentRemoveCommand.removeStudent(request);
	}
	// ****************************** removeStudent ****************************** //
	
	
	// ****************************** initialStudentCourseQuota ****************************** //
	@Transactional
	public StudentCourseQuotaInitialCommand.Response initialStudentCourseQuota(Consumer<StudentCourseQuotaInitialCommand.Request> consumer)
	{
		StudentCourseQuotaInitialCommand.Request request = new StudentCourseQuotaInitialCommand.Request();
		consumer.accept(request);
		return initialStudentCourseQuota(request);
	}
	
	@Transactional
	public StudentCourseQuotaInitialCommand.Response initialStudentCourseQuota(StudentCourseQuotaInitialCommand.Request request)
	{
		logger.info("### {}.initialStudentCourseQuota ###", getClass().getSimpleName());
		return studentCourseQuotaInitialCommand.initialStudentCourseQuota(request);
	}
	// ****************************** initialStudentCourseQuota ****************************** //
	
	
	// ****************************** createStudentCourseQuota ****************************** //
	@Transactional
	public StudentCourseQuotaCreateCommand.Response createStudentCourseQuota(Consumer<StudentCourseQuotaCreateCommand.Request> consumer)
	{
		StudentCourseQuotaCreateCommand.Request request = new StudentCourseQuotaCreateCommand.Request();
		consumer.accept(request);
		return createStudentCourseQuota(request);
	}
	
	@Transactional
	public StudentCourseQuotaCreateCommand.Response createStudentCourseQuota(StudentCourseQuotaCreateCommand.Request request)
	{
		logger.info("### {}.createStudentCourseQuota ###", getClass().getSimpleName());
		return studentCourseQuotaCreateCommand.createStudentCourseQuota(request);
	}
	// ****************************** createStudentCourseQuota ****************************** //
	
	
	// ****************************** updateStudentCourseQuota ****************************** //
	@Transactional
	public StudentCourseQuotaUpdateCommand.Response updateStudentCourseQuota(Consumer<StudentCourseQuotaUpdateCommand.Request> consumer)
	{
		StudentCourseQuotaUpdateCommand.Request request = new StudentCourseQuotaUpdateCommand.Request();
		consumer.accept(request);
		return updateStudentCourseQuota(request);
	}
	
	@Transactional
	public StudentCourseQuotaUpdateCommand.Response updateStudentCourseQuota(StudentCourseQuotaUpdateCommand.Request request)
	{
		logger.info("### {}.updateStudentCourseQuota ###", getClass().getSimpleName());
		return studentCourseQuotaUpdateCommand.updateStudentCourseQuota(request);
	}
	// ****************************** updateStudentCourseQuota ****************************** //
	
	
	// ****************************** removeStudentCourseQuota ****************************** //
	@Transactional
	public StudentCourseQuotaRemoveCommand.Response removeStudentCourseQuota(Consumer<StudentCourseQuotaRemoveCommand.Request> consumer)
	{
		StudentCourseQuotaRemoveCommand.Request request = new StudentCourseQuotaRemoveCommand.Request();
		consumer.accept(request);
		return removeStudentCourseQuota(request);
	}
	
	@Transactional
	public StudentCourseQuotaRemoveCommand.Response removeStudentCourseQuota(StudentCourseQuotaRemoveCommand.Request request)
	{
		logger.info("### {}.removeStudentCourseQuota ###", getClass().getSimpleName());
		return studentCourseQuotaRemoveCommand.removeStudentCourseQuota(request);
	}
	// ****************************** removeStudentCourseQuota ****************************** //
	
	
	// ****************************** loadStudentCourseQuotaInfo ****************************** //
	public StudentCourseQuotaInfoLoadCommand.Response loadStudentCourseQuotaInfo(Consumer<StudentCourseQuotaInfoLoadCommand.Request> consumer)
	{
		StudentCourseQuotaInfoLoadCommand.Request request = new StudentCourseQuotaInfoLoadCommand.Request();
		consumer.accept(request);
		return loadStudentCourseQuotaInfo(request);
	}
	
	public StudentCourseQuotaInfoLoadCommand.Response loadStudentCourseQuotaInfo(StudentCourseQuotaInfoLoadCommand.Request request)
	{
		logger.info("### {}.loadStudentCourseQuotaInfo ###", getClass().getSimpleName());
		return studentCourseQuotaInfoLoadCommand.loadStudentCourseQuotaInfo(request);
	}
	// ****************************** loadStudentCourseQuotaInfo ****************************** //
	
	
	// ****************************** loadStudentSubjectQuota ****************************** //
	public FishsixStudentSubjectQuotaLoadCommand.Response loadStudentSubjectQuota(Consumer<FishsixStudentSubjectQuotaLoadCommand.Request> consumer)
	{
		FishsixStudentSubjectQuotaLoadCommand.Request request = new FishsixStudentSubjectQuotaLoadCommand.Request();
		consumer.accept(request);
		return loadStudentSubjectQuota(request);
	}
	
	public FishsixStudentSubjectQuotaLoadCommand.Response loadStudentSubjectQuota(FishsixStudentSubjectQuotaLoadCommand.Request request)
	{
		logger.info("### {}.loadStudentSubjectQuota ###", getClass().getSimpleName());
		return studentSubjectQuotaLoadCommand.loadStudentSubjectQuota(request);
	}
	// ****************************** loadStudentSubjectQuota ****************************** //
}