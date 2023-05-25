package com.otc.kws.core.domain.service.filestore;

import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.otc.kws.core.domain.constant.value.FileStorageTypeValue;
import com.otc.kws.core.domain.service.BaseKwsService;
import com.otc.kws.core.domain.service.ConfigService;

@Service
public class FileStoreService extends BaseKwsService
{
	@Autowired
	protected ConfigService configService;
	
	@Autowired
	protected FileStoreGetURLCommand fileStoreGetURLCommand;
	
	@Autowired
	protected FileStoreGetFileCommand fileStoreGetFileCommand;
	
	@Autowired
	protected FileStorePutFileCommand fileStorePutFileCommand;
	
	@Autowired
	protected FileStoreRemoveFileCommand fileStoreRemoveFileCommand;
	
	
	public FileStorageTypeValue getDefaultStorageType()
	{
		return configService.getFileStorageType();
	}
	
	
	// ****************************** getURL ****************************** //
	public FileStoreGetURLCommand.Response getURL(Consumer<FileStoreGetURLCommand.Request> consumer)
	{
		FileStoreGetURLCommand.Request request = new FileStoreGetURLCommand.Request();
		consumer.accept(request);
		return getURL(request);
	}
	
	public FileStoreGetURLCommand.Response getURL(FileStoreGetURLCommand.Request request)
	{
		return fileStoreGetURLCommand.getURL(request);
	}
	// ****************************** getURL ****************************** //
	
	
	// ****************************** getFile ****************************** //
	public FileStoreGetFileCommand.Response getFile(Consumer<FileStoreGetFileCommand.Request> consumer)
	{
		FileStoreGetFileCommand.Request request = new FileStoreGetFileCommand.Request();
		consumer.accept(request);
		return getFile(request);
	}
	
	public FileStoreGetFileCommand.Response getFile(FileStoreGetFileCommand.Request request)
	{
		return fileStoreGetFileCommand.getFile(request);
	}
	// ****************************** getFile ****************************** //
	
	
	// ****************************** putFile ****************************** //
	@Transactional
	public FileStorePutFileCommand.Response putFile(Consumer<FileStorePutFileCommand.Request> consumer)
	{
		FileStorePutFileCommand.Request request = new FileStorePutFileCommand.Request();
		consumer.accept(request);
		return putFile(request);
	}
	
	@Transactional
	public FileStorePutFileCommand.Response putFile(FileStorePutFileCommand.Request request)
	{
		return fileStorePutFileCommand.putFile(request);
	}
	// ****************************** putFile ****************************** //
	
	
	// ****************************** removeFile ****************************** //
	@Transactional
	public FileStoreRemoveFileCommand.Response removeFile(Consumer<FileStoreRemoveFileCommand.Request> consumer)
	{
		FileStoreRemoveFileCommand.Request request = new FileStoreRemoveFileCommand.Request();
		consumer.accept(request);
		return removeFile(request);
	}
	
	@Transactional
	public FileStoreRemoveFileCommand.Response removeFile(FileStoreRemoveFileCommand.Request request)
	{
		return fileStoreRemoveFileCommand.removeFile(request);
	}
	// ****************************** removeFile ****************************** //
}