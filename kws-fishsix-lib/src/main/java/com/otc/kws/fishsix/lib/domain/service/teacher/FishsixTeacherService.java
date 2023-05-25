package com.otc.kws.fishsix.lib.domain.service.teacher;

import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.otc.kws.core.domain.service.BaseKwsService;
import com.otc.kws.fishsix.lib.domain.service.teacher.file.FishsixTeacherIdCardFileRemoveCommand;
import com.otc.kws.fishsix.lib.domain.service.teacher.file.FishsixTeacherIdCardFileUploadCommand;

@Service
public class FishsixTeacherService extends BaseKwsService
{
	@Autowired
	protected FishsixTeacherProfileBuildCommand teacherProfileBuildCommand;
	
	@Autowired
	protected FishsixTeacherFetchCommand teacherFetchCommand;
	
	@Autowired
	protected FishsixTeacherCreateCommand teacherCreateCommand;
	
	@Autowired
	protected FishsixTeacherUpdateCommand teacherUpdateCommand;
	
	@Autowired
	protected FishsixTeacherRemoveCommand teacherRemoveCommand;
	
	@Autowired
	protected FishsixTeacherIdCardFileUploadCommand teacherIdCardFileUploadCommand;
	
	@Autowired
	protected FishsixTeacherIdCardFileRemoveCommand teacherIdCardFileRemoveCommand;
	
	
	// ****************************** buildTeacherProfile ****************************** //
	public FishsixTeacherProfileBuildCommand.Response buildTeacherProfile(Consumer<FishsixTeacherProfileBuildCommand.Request> consumer)
	{
		FishsixTeacherProfileBuildCommand.Request request = new FishsixTeacherProfileBuildCommand.Request();
		consumer.accept(request);
		return buildTeacherProfile(request);
	}
	
	public FishsixTeacherProfileBuildCommand.Response buildTeacherProfile(FishsixTeacherProfileBuildCommand.Request request)
	{
		logger.info("### {}.buildTeacherProfile ###", getClass().getSimpleName());
		return teacherProfileBuildCommand.buildTeacherProfile(request);
	}
	// ****************************** buildTeacherProfile ****************************** //
	
	
	// ****************************** fetchTeacher ****************************** //
	public FishsixTeacherFetchCommand.Response fetchTeacher(Consumer<FishsixTeacherFetchCommand.Request> consumer)
	{
		FishsixTeacherFetchCommand.Request request = new FishsixTeacherFetchCommand.Request();
		consumer.accept(request);
		return fetchTeacher(request);
	}
	
	public FishsixTeacherFetchCommand.Response fetchTeacher(FishsixTeacherFetchCommand.Request request)
	{
		logger.info("### {}.fetchTeacher ###", getClass().getSimpleName());
		return teacherFetchCommand.fetchTeacher(request);
	}
	// ****************************** fetchTeacher ****************************** //
	
	
	// ****************************** createTeacher ****************************** //
	@Transactional
	public FishsixTeacherCreateCommand.Response createTeacher(Consumer<FishsixTeacherCreateCommand.Request> consumer)
	{
		FishsixTeacherCreateCommand.Request request = new FishsixTeacherCreateCommand.Request();
		consumer.accept(request);
		return createTeacher(request);
	}
	
	@Transactional
	public FishsixTeacherCreateCommand.Response createTeacher(FishsixTeacherCreateCommand.Request request)
	{
		logger.info("### {}.createTeacher ###", getClass().getSimpleName());
		return teacherCreateCommand.createTeacher(request);
	}
	// ****************************** createTeacher ****************************** //
	
	
	// ****************************** updateTeacher ****************************** //
	@Transactional
	public FishsixTeacherUpdateCommand.Response updateTeacher(Consumer<FishsixTeacherUpdateCommand.Request> consumer)
	{
		FishsixTeacherUpdateCommand.Request request = new FishsixTeacherUpdateCommand.Request();
		consumer.accept(request);
		return updateTeacher(request);
	}
	
	@Transactional
	public FishsixTeacherUpdateCommand.Response updateTeacher(FishsixTeacherUpdateCommand.Request request)
	{
		logger.info("### {}.updateTeacher ###", getClass().getSimpleName());
		return teacherUpdateCommand.updateTeacher(request);
	}
	// ****************************** updateTeacher ****************************** //
	
	
	// ****************************** removeTeacher ****************************** //
	@Transactional
	public FishsixTeacherRemoveCommand.Response removeTeacher(Consumer<FishsixTeacherRemoveCommand.Request> consumer)
	{
		FishsixTeacherRemoveCommand.Request request = new FishsixTeacherRemoveCommand.Request();
		consumer.accept(request);
		return removeTeacher(request);
	}
	
	@Transactional
	public FishsixTeacherRemoveCommand.Response removeTeacher(FishsixTeacherRemoveCommand.Request request)
	{
		logger.info("### {}.removeTeacher ###", getClass().getSimpleName());
		return teacherRemoveCommand.removeTeacher(request);
	}
	// ****************************** removeTeacher ****************************** //
	
	
	// ****************************** uploadIdCardFile ****************************** //
	@Transactional
	public FishsixTeacherIdCardFileUploadCommand.Response uploadIdCardFile(Consumer<FishsixTeacherIdCardFileUploadCommand.Request> consumer)
	{
		FishsixTeacherIdCardFileUploadCommand.Request request = new FishsixTeacherIdCardFileUploadCommand.Request();
		consumer.accept(request);
		return uploadIdCardFile(request);
	}
	
	@Transactional
	public FishsixTeacherIdCardFileUploadCommand.Response uploadIdCardFile(FishsixTeacherIdCardFileUploadCommand.Request request)
	{
		logger.info("### {}.uploadIdCardFile ###", getClass().getSimpleName());
		return teacherIdCardFileUploadCommand.uploadIdCardFile(request);
	}
	// ****************************** uploadIdCardFile ****************************** //
	
	
	// ****************************** removeIdCardFile ****************************** //
	@Transactional
	public FishsixTeacherIdCardFileRemoveCommand.Response removeIdCardFile(Consumer<FishsixTeacherIdCardFileRemoveCommand.Request> consumer)
	{
		FishsixTeacherIdCardFileRemoveCommand.Request request = new FishsixTeacherIdCardFileRemoveCommand.Request();
		consumer.accept(request);
		return removeIdCardFile(request);
	}
	
	@Transactional
	public FishsixTeacherIdCardFileRemoveCommand.Response removeIdCardFile(FishsixTeacherIdCardFileRemoveCommand.Request request)
	{
		logger.info("### {}.removeIdCardFile ###", getClass().getSimpleName());
		return teacherIdCardFileRemoveCommand.removeIdCardFile(request);
	}
	// ****************************** removeIdCardFile ****************************** //
}