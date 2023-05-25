package com.otc.kws.core.domain.repository.jpa;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.otc.kws.core.domain.entity.FileStoreLog;

@Repository
public interface JpaFileStoreLogRepository extends KwsJpaRepository<FileStoreLog, String>
{
	public FileStoreLog findByFileURI(String fileURI);
	
	public List<FileStoreLog> removeByFileURI(String fileURI);
}