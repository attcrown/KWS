package com.otc.kws.fishsix.lib.domain.service.teacher_register;

import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.otc.kws.core.domain.service.BaseKwsService;

@Service
public class FishsixTeacherRegisterService extends BaseKwsService
{
	@Autowired
	protected FishsixTeacherRegisterSearchCommand teacherRegisterSearchCommand;
	
	@Autowired
	protected FishsixTeacherRegisterCreateCommand teacherRegisterCreateCommand;
	
	@Autowired
	protected FishsixTeacherRegisterApproveCommand teacherRegisterApproveCommand;
	
	@Autowired
	protected FishsixTeacherRegisterRejectCommand teacherRegisterRejectCommand;
	
	
	// ****************************** search ****************************** //
	public FishsixTeacherRegisterSearchCommand.Response search(Consumer<FishsixTeacherRegisterSearchCommand.Request> consumer)
	{
		FishsixTeacherRegisterSearchCommand.Request request = new FishsixTeacherRegisterSearchCommand.Request();
		consumer.accept(request);
		return search(request);
	}
	
	public FishsixTeacherRegisterSearchCommand.Response search(FishsixTeacherRegisterSearchCommand.Request request)
	{
		logger.info("### {}.search ###", getClass().getSimpleName());
		return teacherRegisterSearchCommand.search(request);
	}
	// ****************************** search ****************************** //
	
	
	// ****************************** create ****************************** //
	@Transactional
	public FishsixTeacherRegisterCreateCommand.Response create(Consumer<FishsixTeacherRegisterCreateCommand.Request> consumer)
	{
		FishsixTeacherRegisterCreateCommand.Request request = new FishsixTeacherRegisterCreateCommand.Request();
		consumer.accept(request);
		return create(request);
	}
	
	@Transactional
	public FishsixTeacherRegisterCreateCommand.Response create(FishsixTeacherRegisterCreateCommand.Request request)
	{
		logger.info("### {}.create ###", getClass().getSimpleName());
		return teacherRegisterCreateCommand.create(request);
	}
	// ****************************** create ****************************** //
	
	
	// ****************************** approve ****************************** //
	@Transactional
	public FishsixTeacherRegisterApproveCommand.Response approve(Consumer<FishsixTeacherRegisterApproveCommand.Request> consumer)
	{
		FishsixTeacherRegisterApproveCommand.Request request = new FishsixTeacherRegisterApproveCommand.Request();
		consumer.accept(request);
		return approve(request);
	}
	
	@Transactional
	public FishsixTeacherRegisterApproveCommand.Response approve(FishsixTeacherRegisterApproveCommand.Request request)
	{
		logger.info("### {}.approve ###", getClass().getSimpleName());
		return teacherRegisterApproveCommand.approve(request);
	}
	// ****************************** approve ****************************** //
	
	
	// ****************************** reject ****************************** //
	@Transactional
	public FishsixTeacherRegisterRejectCommand.Response reject(Consumer<FishsixTeacherRegisterRejectCommand.Request> consumer)
	{
		FishsixTeacherRegisterRejectCommand.Request request = new FishsixTeacherRegisterRejectCommand.Request();
		consumer.accept(request);
		return reject(request);
	}
	
	@Transactional
	public FishsixTeacherRegisterRejectCommand.Response reject(FishsixTeacherRegisterRejectCommand.Request request)
	{
		logger.info("### {}.reject ###", getClass().getSimpleName());
		return teacherRegisterRejectCommand.reject(request);
	}
	// ****************************** reject ****************************** //
}