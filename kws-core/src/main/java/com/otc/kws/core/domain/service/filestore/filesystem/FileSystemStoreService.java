package com.otc.kws.core.domain.service.filestore.filesystem;

import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.otc.kws.core.domain.ServerURLProvider;
import com.otc.kws.core.domain.service.BaseKwsService;
import com.otc.kws.core.domain.service.ConfigService;

@Service
public class FileSystemStoreService extends BaseKwsService
{
	@Autowired
	protected ServerURLProvider serverURLProvider;
	
	@Autowired
	protected ConfigService configService;
	
	@Autowired
	protected FileSystemStoreGetURLCommand fileSystemStoreGetURLCommand;
	
	@Autowired
	protected FileSystemStoreGetFileCommand fileSystemStoreGetFileCommand;
	
	@Autowired
	protected FileSystemStorePutFileCommand fileSystemStorePutFileCommand;
	
	@Autowired
	protected FileSystemStoreRemoveFileCommand fileSystemStoreRemoveFileCommand;
	
	
	public String getFileSystemBaseResourcePath()
	{
		return configService.getFileSystemBaseResourcePath();
	}
	
	public String getServerURL()
	{
		return serverURLProvider.getServerURL();
	}
	
	public String getResourceWebPrefixPath()
	{
		return configService.getResourceWebPrefixPath();
	}
	
	
	// ****************************** getURL ****************************** //
	public FileSystemStoreGetURLCommand.Response getURL(Consumer<FileSystemStoreGetURLCommand.Request> consumer)
	{
		FileSystemStoreGetURLCommand.Request request = new FileSystemStoreGetURLCommand.Request();
		consumer.accept(request);
		return getURL(request);
	}
	
	public FileSystemStoreGetURLCommand.Response getURL(FileSystemStoreGetURLCommand.Request request)
	{
		return fileSystemStoreGetURLCommand.getURL(request);
	}
	// ****************************** getURL ****************************** //
	
	
	// ****************************** getFile ****************************** //
	public FileSystemStoreGetFileCommand.Response getFile(Consumer<FileSystemStoreGetFileCommand.Request> consumer)
	{
		FileSystemStoreGetFileCommand.Request request = new FileSystemStoreGetFileCommand.Request();
		consumer.accept(request);
		return getFile(request);
	}
	
	public FileSystemStoreGetFileCommand.Response getFile(FileSystemStoreGetFileCommand.Request request)
	{
		return fileSystemStoreGetFileCommand.getFile(request);
	}
	// ****************************** getFile ****************************** //
	
	
	// ****************************** putFile ****************************** //
	public FileSystemStorePutFileCommand.Response putFile(Consumer<FileSystemStorePutFileCommand.Request> consumer)
	{
		FileSystemStorePutFileCommand.Request request = new FileSystemStorePutFileCommand.Request();
		consumer.accept(request);
		return putFile(request);
	}
	
	public FileSystemStorePutFileCommand.Response putFile(FileSystemStorePutFileCommand.Request request)
	{
		return fileSystemStorePutFileCommand.putFile(request);
	}
	// ****************************** putFile ****************************** //
	
	
	// ****************************** removeFile ****************************** //
	public FileSystemStoreRemoveFileCommand.Response removeFile(Consumer<FileSystemStoreRemoveFileCommand.Request> consumer)
	{
		FileSystemStoreRemoveFileCommand.Request request = new FileSystemStoreRemoveFileCommand.Request();
		consumer.accept(request);
		return removeFile(request);
	}
	
	public FileSystemStoreRemoveFileCommand.Response removeFile(FileSystemStoreRemoveFileCommand.Request request)
	{
		return fileSystemStoreRemoveFileCommand.removeFile(request);
	}
	// ****************************** removeFile ****************************** //
}