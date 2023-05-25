package com.otc.kws.core.domain.service.filestore;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.otc.kws.core.domain.entity.FileStoreLog;
import com.otc.kws.core.domain.repository.jpa.JpaFileStoreLogRepository;
import com.otc.kws.core.domain.service.RepositoryKwsService;

@Service
public class FileStoreLogService extends RepositoryKwsService<JpaFileStoreLogRepository, FileStoreLog, String>
{
	@Autowired
	protected JpaFileStoreLogRepository fileStoreLogRepository;

	
	@Override
	protected JpaFileStoreLogRepository getRepository() 
	{
		return fileStoreLogRepository;
	}

	@Override
	protected String getId(FileStoreLog entity) 
	{
		return entity.getId();
	}
	
	
	public FileStoreLog findByFileURI(String fileURI)
	{
		return fileStoreLogRepository.findByFileURI(fileURI);
	}
	
	@Transactional
	public FileStoreLog removeByFileURI(String fileURI)
	{
		List<FileStoreLog> fileStoreLogs = fileStoreLogRepository.removeByFileURI(fileURI);
		if(fileStoreLogs != null && !fileStoreLogs.isEmpty()) {
			fileStoreLogRepository.flush();
			return fileStoreLogs.get(0);
		}
		return null;
	}
	
	@Transactional
	public FileStoreLog removeByFileURIAndFlush(String fileURI)
	{
		FileStoreLog fileStoreLog = removeByFileURI(fileURI);
		if(fileStoreLog != null) {
			fileStoreLogRepository.flush();
		}
		return fileStoreLog;
	}
}