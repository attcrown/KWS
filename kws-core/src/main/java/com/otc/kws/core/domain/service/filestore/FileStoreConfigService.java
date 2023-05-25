package com.otc.kws.core.domain.service.filestore;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.otc.kws.core.domain.entity.FileStoreConfig;
import com.otc.kws.core.domain.repository.jpa.JpaFileStoreConfigRepository;
import com.otc.kws.core.domain.service.CacheKwsService;

@Service
public class FileStoreConfigService extends CacheKwsService<FileStoreConfig, String>
{
	@Autowired
	protected JpaFileStoreConfigRepository fileStoreConfigRepository;

	
	@Override
	public List<FileStoreConfig> loadDatas() 
	{
		return fileStoreConfigRepository.findAll();
	}

	@Override
	protected String extractId(FileStoreConfig entity) 
	{
		return entity.getId();
	}
	
	
	public FileStoreConfig getByModuleAndFileCategoryAndFileGroup(String module, String fileCategory, String fileGroup)
	{
		return getAll().
				stream().
				filter(e -> e.getModule().equals(module)).
				filter(e -> e.getFileCategory().equals(fileCategory)).
				filter(e -> e.getFileGroup().equals(fileGroup)).
				findFirst().
				orElse(null);
	}
	
	public FileStoreConfig getByFileURI(String fileURI)
	{
		return getAll().
				stream().
				filter(e -> e.getFileURI().equals(fileURI)).
				findFirst().
				orElse(null);
	}
}