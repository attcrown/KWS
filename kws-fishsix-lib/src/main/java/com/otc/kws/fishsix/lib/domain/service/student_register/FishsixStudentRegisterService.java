package com.otc.kws.fishsix.lib.domain.service.student_register;

import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.otc.kws.core.domain.service.BaseKwsService;

@Service
public class FishsixStudentRegisterService extends BaseKwsService
{
	@Autowired
	protected FishsixStudentRegisterSearchCommand studentRegisterSearchCommand;
	
	@Autowired
	protected FishsixStudentRegisterCreateCommand studentRegisterCreateCommand;
	
	@Autowired
	protected FishsixStudentRegisterApproveCommand studentRegisterApproveCommand;
	
	@Autowired
	protected FishsixStudentRegisterRejectCommand studentRegisterRejectCommand;
	
	
	// ****************************** search ****************************** //
	public FishsixStudentRegisterSearchCommand.Response search(Consumer<FishsixStudentRegisterSearchCommand.Request> consumer)
	{
		FishsixStudentRegisterSearchCommand.Request request = new FishsixStudentRegisterSearchCommand.Request();
		consumer.accept(request);
		return search(request);
	}
	
	public FishsixStudentRegisterSearchCommand.Response search(FishsixStudentRegisterSearchCommand.Request request)
	{
		logger.info("### {}.search ###", getClass().getSimpleName());
		return studentRegisterSearchCommand.search(request);
	}
	// ****************************** search ****************************** //
	
	
	// ****************************** create ****************************** //
	public FishsixStudentRegisterCreateCommand.Response create(Consumer<FishsixStudentRegisterCreateCommand.Request> consumer)
	{
		FishsixStudentRegisterCreateCommand.Request request = new FishsixStudentRegisterCreateCommand.Request();
		consumer.accept(request);
		return create(request);
	}
	
	public FishsixStudentRegisterCreateCommand.Response create(FishsixStudentRegisterCreateCommand.Request request)
	{
		logger.info("### {}.create ###", getClass().getSimpleName());
		return studentRegisterCreateCommand.create(request);
	}
	// ****************************** create ****************************** //
	
	
	// ****************************** approve ****************************** //
	public FishsixStudentRegisterApproveCommand.Response approve(Consumer<FishsixStudentRegisterApproveCommand.Request> consumer)
	{
		FishsixStudentRegisterApproveCommand.Request request = new FishsixStudentRegisterApproveCommand.Request();
		consumer.accept(request);
		return approve(request);
	}
	
	public FishsixStudentRegisterApproveCommand.Response approve(FishsixStudentRegisterApproveCommand.Request request)
	{
		logger.info("### {}.approve ###", getClass().getSimpleName());
		return studentRegisterApproveCommand.approve(request);
	}
	// ****************************** approve ****************************** //
	
	
	// ****************************** reject ****************************** //
	public FishsixStudentRegisterRejectCommand.Response reject(Consumer<FishsixStudentRegisterRejectCommand.Request> consumer)
	{
		FishsixStudentRegisterRejectCommand.Request request = new FishsixStudentRegisterRejectCommand.Request();
		consumer.accept(request);
		return reject(request);
	}
	
	public FishsixStudentRegisterRejectCommand.Response reject(FishsixStudentRegisterRejectCommand.Request request)
	{
		logger.info("### {}.reject ###", getClass().getSimpleName());
		return studentRegisterRejectCommand.reject(request);
	}
	// ****************************** reject ****************************** //
}