package com.otc.kws.firstjobber.lib.domain.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.otc.kws.core.domain.exception.EntityNotFoundException;
import com.otc.kws.core.domain.service.filestore.FileStoreService;
import com.otc.kws.firstjobber.lib.domain.constant.value.InternshipRegisterStatusValue;
import com.otc.kws.firstjobber.lib.domain.entity.InternshipRegister;
import com.otc.kws.firstjobber.lib.domain.repository.jpa.JpaInternshipRegisterRepository;
import com.otc.kws.firstjobber.lib.domain.service.internship.FjbInternshipUpload16PersonalityTestImageCommand;
import com.otc.kws.firstjobber.lib.domain.service.internship.FjbInternshipUploadHigh5TestImageCommand;
import com.otc.kws.firstjobber.lib.domain.service.internship.FjbInternshipUploadIQTestImageCommand;
import com.otc.kws.firstjobber.lib.domain.service.internship.FjbInternshipUploadTestCommand;
import com.otc.kws.firstjobber.lib.domain.service.internship.InternshipRegisterCommand;
import com.otc.kws.firstjobber.lib.domain.service.internship.InternshipRegisterNotifyCommand;
import com.otc.kws.firstjobber.lib.domain.service.internship.InternshipUpdateRegisterStatusCommand;
import com.otc.kws.firstjobber.lib.domain.service.internship.InternshipUploadFormalPhotoCommand;
import com.otc.kws.firstjobber.lib.domain.service.internship.InternshipUploadInformalPhotoCommand;
import com.otc.kws.firstjobber.lib.domain.service.internship.InternshipUploadInternLetterFileCommand;
import com.otc.kws.firstjobber.lib.domain.service.internship.InternshipUploadPortfolioFileCommand;
import com.otc.kws.firstjobber.lib.domain.service.internship.InternshipUploadResumeFileCommand;
import com.otc.kws.firstjobber.lib.domain.service.internship.InternshipUploadTestNotifyCommand;

@Service
public class InternshipRegisterService extends RepositoryKwsFirstJobberService<JpaInternshipRegisterRepository, InternshipRegister, String>
{
	@Autowired
	protected JpaInternshipRegisterRepository internshipRegisterRepository;
	
	@Autowired
	protected FileStoreService fileStoreService;
	
	@Autowired
	@Lazy
	protected InternshipRegisterCommand internshipRegisterCommand;
	
	@Autowired
	@Lazy
	protected FjbInternshipUploadTestCommand fjbInternshipUploadTestCommand;
	
	@Autowired
	protected InternshipUpdateRegisterStatusCommand internshipUpdateRegisterStatusCommand;
	
	@Autowired
	protected InternshipUploadFormalPhotoCommand internshipUploadFormalPhotoCommand;
	
	@Autowired
	protected InternshipUploadInformalPhotoCommand internshipUploadInformalPhotoCommand;
	
	@Autowired
	protected InternshipUploadResumeFileCommand internshipUploadResumeFileCommand;
	
	@Autowired
	protected InternshipUploadPortfolioFileCommand internshipUploadPortfolioFileCommand;
	
	@Autowired
	protected InternshipUploadInternLetterFileCommand internshipUploadInternLetterFileCommand;
	
	@Autowired
	protected FjbInternshipUpload16PersonalityTestImageCommand fjbInternshipUpload16PersonalityTestImageCommand;
	
	@Autowired
	protected FjbInternshipUploadHigh5TestImageCommand fjbInternshipUploadHigh5TestImageCommand;
	
	@Autowired
	protected FjbInternshipUploadIQTestImageCommand fjbInternshipUploadIQTestImageCommand;
	
	@Autowired
	protected InternshipRegisterNotifyCommand internshipRegisterNotifyCommand;
	
	@Autowired
	protected InternshipUploadTestNotifyCommand internshipUploadTestNotifyCommand;
	
	
	@Override
	protected JpaInternshipRegisterRepository getRepository() 
	{
		return internshipRegisterRepository;
	}

	@Override
	protected String getId(InternshipRegister entity) 
	{
		return entity.getId();
	}
	
	
	public List<InternshipRegister> findByRegisterStatusId(InternshipRegisterStatusValue registerStatusId)
	{
		return internshipRegisterRepository.findByRegisterStatusId(registerStatusId);
	}
	
	@Async
	public CompletableFuture<List<InternshipRegister>> findByRegisterStatusIdAsync(InternshipRegisterStatusValue registerStatusId)
	{
		return CompletableFuture.completedFuture(findByRegisterStatusId(registerStatusId));
	}
	
	
	public List<InternshipRegister> findByRegisterStatusIds(List<InternshipRegisterStatusValue> registerStatusIds)
	{
		return internshipRegisterRepository.findByRegisterStatusIds(registerStatusIds);
	}
	
	@Async
	public CompletableFuture<List<InternshipRegister>> findByRegisterStatusIdsAsync(List<InternshipRegisterStatusValue> registerStatusIds)
	{
		return CompletableFuture.completedFuture(findByRegisterStatusIds(registerStatusIds));
	}
	
	
	/*
	public List<InternshipRegister> findAllPendingRegisterStatus()
	{
		return internshipRegisterRepository.findAllPendingRegisterStatus();
	}
	
	@Async
	public CompletableFuture<List<InternshipRegister>> findAllPendingRegisterStatusAsync()
	{
		return CompletableFuture.completedFuture(findAllPendingRegisterStatus());
	}
	*/
	
	
	public List<InternshipRegister> findAllReviewingRegisterStatus()
	{
		return internshipRegisterRepository.findAllReviewingRegisterStatus();
	}
	
	@Async
	public CompletableFuture<List<InternshipRegister>> findAllReviewingRegisterStatusAsync()
	{
		return CompletableFuture.completedFuture(findAllReviewingRegisterStatus());
	}
	
	
	/*
	public List<InternshipRegister> findAllPendingOrReviewingRegisterStatus()
	{
		return internshipRegisterRepository.findAllPendingOrReviewingRegisterStatus();
	}
	
	@Async
	public CompletableFuture<List<InternshipRegister>> findAllPendingOrReviewingRegisterStatusAsync()
	{
		return CompletableFuture.completedFuture(findAllPendingOrReviewingRegisterStatus());
	}
	*/
	
	
	public List<InternshipRegister> findAllAcceptedRegisterStatus()
	{
		return internshipRegisterRepository.findAllAcceptedRegisterStatus();
	}
	
	@Async
	public CompletableFuture<List<InternshipRegister>> findAllAcceptedRegisterStatusAsync()
	{
		return CompletableFuture.completedFuture(findAllAcceptedRegisterStatus());
	}
	
	
	public List<InternshipRegister> findAllRejectedRegisterStatus()
	{
		return internshipRegisterRepository.findAllRejectedRegisterStatus();
	}
	
	@Async
	public CompletableFuture<List<InternshipRegister>> findAllRejectedRegisterStatusAsync()
	{
		return CompletableFuture.completedFuture(findAllRejectedRegisterStatus());
	}
	
	
	// ****************************** register ****************************** //
	@Transactional
	public InternshipRegisterCommand.Response register(Consumer<InternshipRegisterCommand.Request> consumer)
	{
		InternshipRegisterCommand.Request request = new InternshipRegisterCommand.Request();
		consumer.accept(request);
		return register(request);
	}
	
	@Transactional
	public InternshipRegisterCommand.Response register(InternshipRegisterCommand.Request request)
	{
		return internshipRegisterCommand.register(request);
	}
	// ****************************** register ****************************** //
	
	
	// ****************************** uploadFormalPhoto ****************************** //
	@Transactional
	public InternshipUploadFormalPhotoCommand.Response uploadFormalPhoto(Consumer<InternshipUploadFormalPhotoCommand.Request> consumer)
	{
		InternshipUploadFormalPhotoCommand.Request request = new InternshipUploadFormalPhotoCommand.Request();
		consumer.accept(request);
		return uploadFormalPhoto(request);
	}
	
	@Transactional
	public InternshipUploadFormalPhotoCommand.Response uploadFormalPhoto(InternshipUploadFormalPhotoCommand.Request request)
	{
		return internshipUploadFormalPhotoCommand.uploadFormalPhoto(request);
	}
	// ****************************** uploadFormalPhoto ****************************** //
	
	
	// ****************************** uploadInformalPhoto ****************************** //
	@Transactional
	public InternshipUploadInformalPhotoCommand.Response uploadInformalPhoto(Consumer<InternshipUploadInformalPhotoCommand.Request> consumer)
	{
		InternshipUploadInformalPhotoCommand.Request request = new InternshipUploadInformalPhotoCommand.Request();
		consumer.accept(request);
		return uploadInformalPhoto(request);
	}
	
	@Transactional
	public InternshipUploadInformalPhotoCommand.Response uploadInformalPhoto(InternshipUploadInformalPhotoCommand.Request request)
	{
		return internshipUploadInformalPhotoCommand.uploadInformalPhoto(request);
	}
	// ****************************** uploadInformalPhoto ****************************** //
	
	
	// ****************************** uploadResumeFile ****************************** //
	@Transactional
	public InternshipUploadResumeFileCommand.Response uploadResumeFile(Consumer<InternshipUploadResumeFileCommand.Request> consumer)
	{
		InternshipUploadResumeFileCommand.Request request = new InternshipUploadResumeFileCommand.Request();
		consumer.accept(request);
		return uploadResumeFile(request);
	}
	
	@Transactional
	public InternshipUploadResumeFileCommand.Response uploadResumeFile(InternshipUploadResumeFileCommand.Request request)
	{
		return internshipUploadResumeFileCommand.uploadResumeFile(request);
	}
	// ****************************** uploadResumeFile ****************************** //
	
	
	// ****************************** uploadPortfolioFile ****************************** //
	@Transactional
	public InternshipUploadPortfolioFileCommand.Response uploadPortfolioFile(Consumer<InternshipUploadPortfolioFileCommand.Request> consumer)
	{
		InternshipUploadPortfolioFileCommand.Request request = new InternshipUploadPortfolioFileCommand.Request();
		consumer.accept(request);
		return uploadPortfolioFile(request);
	}
	
	@Transactional
	public InternshipUploadPortfolioFileCommand.Response uploadPortfolioFile(InternshipUploadPortfolioFileCommand.Request request)
	{
		return internshipUploadPortfolioFileCommand.uploadPortfolioFile(request);
	}
	// ****************************** uploadPortfolioFile ****************************** //
	
	
	// ****************************** uploadInternLetterFile ****************************** //
	@Transactional
	public InternshipUploadInternLetterFileCommand.Response uploadInternLetterFile(Consumer<InternshipUploadInternLetterFileCommand.Request> consumer)
	{
		InternshipUploadInternLetterFileCommand.Request request = new InternshipUploadInternLetterFileCommand.Request();
		consumer.accept(request);
		return uploadInternLetterFile(request);
	}
	
	@Transactional
	public InternshipUploadInternLetterFileCommand.Response uploadInternLetterFile(InternshipUploadInternLetterFileCommand.Request request)
	{
		return internshipUploadInternLetterFileCommand.uploadInternLetterFile(request);
	}
	// ****************************** uploadInternLetterFile ****************************** //
	
	
	// ****************************** uploadTest ****************************** //
	@Transactional
	public FjbInternshipUploadTestCommand.Response uploadTest(Consumer<FjbInternshipUploadTestCommand.Request> consumer)
	{
		FjbInternshipUploadTestCommand.Request request = new FjbInternshipUploadTestCommand.Request();
		consumer.accept(request);
		return uploadTest(request);
	}
	
	@Transactional
	public FjbInternshipUploadTestCommand.Response uploadTest(FjbInternshipUploadTestCommand.Request request)
	{
		return fjbInternshipUploadTestCommand.uploadTest(request);
	}
	// ****************************** uploadTest ****************************** //
	
	
	// ****************************** upload16PersonalityTestImage ****************************** //
	@Transactional
	public FjbInternshipUpload16PersonalityTestImageCommand.Response upload16PersonalityTestImage(Consumer<FjbInternshipUpload16PersonalityTestImageCommand.Request> consumer)
	{
		FjbInternshipUpload16PersonalityTestImageCommand.Request request = new FjbInternshipUpload16PersonalityTestImageCommand.Request();
		consumer.accept(request);
		return upload16PersonalityTestImage(request);
	}
	
	@Transactional
	public FjbInternshipUpload16PersonalityTestImageCommand.Response upload16PersonalityTestImage(FjbInternshipUpload16PersonalityTestImageCommand.Request request)
	{
		return fjbInternshipUpload16PersonalityTestImageCommand.upload16PersonalityTestImage(request);
	}
	// ****************************** upload16PersonalityTestImage ****************************** //
	
	
	// ****************************** uploadHigh5TestImage ****************************** //
	@Transactional
	public FjbInternshipUploadHigh5TestImageCommand.Response uploadHigh5TestImage(Consumer<FjbInternshipUploadHigh5TestImageCommand.Request> consumer)
	{
		FjbInternshipUploadHigh5TestImageCommand.Request request = new FjbInternshipUploadHigh5TestImageCommand.Request();
		consumer.accept(request);
		return uploadHigh5TestImage(request);
	}
	
	@Transactional
	public FjbInternshipUploadHigh5TestImageCommand.Response uploadHigh5TestImage(FjbInternshipUploadHigh5TestImageCommand.Request request)
	{
		return fjbInternshipUploadHigh5TestImageCommand.uploadHigh5TestImage(request);
	}
	// ****************************** uploadHigh5TestImage ****************************** //
	
	
	// ****************************** uploadIQTestImage ****************************** //
	@Transactional
	public FjbInternshipUploadIQTestImageCommand.Response uploadIQTestImage(Consumer<FjbInternshipUploadIQTestImageCommand.Request> consumer)
	{
		FjbInternshipUploadIQTestImageCommand.Request request = new FjbInternshipUploadIQTestImageCommand.Request();
		consumer.accept(request);
		return uploadIQTestImage(request);
	}
	
	@Transactional
	public FjbInternshipUploadIQTestImageCommand.Response uploadIQTestImage(FjbInternshipUploadIQTestImageCommand.Request request)
	{
		return fjbInternshipUploadIQTestImageCommand.uploadIQTestImage(request);
	}
	// ****************************** uploadIQTestImage ****************************** //
	
	
	// ****************************** updateRegisterStatus ****************************** //
	@Transactional
	public InternshipUpdateRegisterStatusCommand.Response updateRegisterStatus(Consumer<InternshipUpdateRegisterStatusCommand.Request> consumer) throws EntityNotFoundException
	{
		InternshipUpdateRegisterStatusCommand.Request request = new InternshipUpdateRegisterStatusCommand.Request();
		consumer.accept(request);
		return updateRegisterStatus(request);
	}
	
	@Transactional
	public InternshipUpdateRegisterStatusCommand.Response updateRegisterStatus(InternshipUpdateRegisterStatusCommand.Request request) throws EntityNotFoundException
	{
		return internshipUpdateRegisterStatusCommand.updateRegisterStatus(request);
	}
	// ****************************** updateRegisterStatus ****************************** //
	
	
	// ****************************** notifyInternshipRegister ****************************** //
	public InternshipRegisterNotifyCommand.Response notifyInternshipRegister(Consumer<InternshipRegisterNotifyCommand.Request> consumer)
	{
		InternshipRegisterNotifyCommand.Request request = new InternshipRegisterNotifyCommand.Request();
		consumer.accept(request);
		return notifyInternshipRegister(request);
	}
	
	public InternshipRegisterNotifyCommand.Response notifyInternshipRegister(InternshipRegisterNotifyCommand.Request request)
	{
		return internshipRegisterNotifyCommand.notifyInternshipRegister(request);
	}
	// ****************************** notifyInternshipRegister ****************************** //
	
	
	// ****************************** notifyInternshipUploadTest ****************************** //
	public InternshipUploadTestNotifyCommand.Response notifyInternshipUploadTest(Consumer<InternshipUploadTestNotifyCommand.Request> consumer)
	{
		InternshipUploadTestNotifyCommand.Request request = new InternshipUploadTestNotifyCommand.Request();
		consumer.accept(request);
		return notifyInternshipUploadTest(request);
	}
	
	public InternshipUploadTestNotifyCommand.Response notifyInternshipUploadTest(InternshipUploadTestNotifyCommand.Request request)
	{
		return internshipUploadTestNotifyCommand.notifyInternshipUploadTest(request);
	}
	// ****************************** notifyInternshipUploadTest ****************************** //
}