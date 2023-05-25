package com.otc.kws.core.domain.service.filestore;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.otc.kws.core.domain.constant.FileStoreConst;
import com.otc.kws.core.domain.constant.value.FileStorageTypeValue;
import com.otc.kws.core.domain.entity.FileStoreConfig;
import com.otc.kws.core.domain.entity.FileStoreLog;
import com.otc.kws.core.domain.exception.KwsRuntimeException;
import com.otc.kws.core.domain.service.filestore.filesystem.FileSystemStorePutFileCommand;

@Component
public class FileStorePutFileCommandV1 extends FileStorePutFileCommand
{
	@Override
	public Response putFile(Request request) 
	{
		Response response = new Response();
		
		if(request.getFileStorageType() == null) {
			request.setFileStorageType(FileStorageTypeValue.FileSystem);
		}
		
		if(request.getFileStorageType() == FileStorageTypeValue.FileSystem) {
			FileStoreConfig fileStoreConfig = fileStoreConfigService.getByModuleAndFileCategoryAndFileGroup(request.getModule(), request.getFileCategory(), request.getFileGroup());
			
			if(fileStoreConfig != null) {
				String fileURI = fileStoreConfig.getFileURI().replace(FileStoreConst.SCHEME_FS, "");
				fileURI = fileURI.replace("${seq_no}", String.valueOf(request.getSeqNo()));
				
				if(request.getFileData().getFileExtension() != null) {
					fileURI = fileURI.replace("${extension}", String.valueOf(request.getFileData().getFileExtension()));
				} else {
					fileURI = fileURI.replace(".${extension}", "");
				}
				
				if(request.getVariables() != null) {
					for(String key : request.getVariables().keySet()) {
						fileURI = fileURI.replace("${"+key+"}", request.getVariables().get(key));
					}
				}
				
				String fileName = fileURI;
				
				FileSystemStorePutFileCommand.Response putFileResponse = fileSystemStoreService.putFile(req -> {
					req.copyFrom(request);
					req.setFileName(fileName);
					req.setFileData(request.getFileData());
				});
				
				if(putFileResponse.getUri() != null && putFileResponse.getUrl() != null) {
					response.setUri(putFileResponse.getUri());
					response.setUrl(putFileResponse.getUrl());
					
					FileStoreLog fileStoreLog = fileStoreLogService.findByFileURI(putFileResponse.getUri());
					boolean exist = false;
					
					if(fileStoreLog != null) {
						exist = true;
						fileStoreLog.setLastModifiedBy(request.getPerformedBy());
						fileStoreLog.setLastModifiedAt(request.getPerformedAt());
					} else {
						fileStoreLog = new FileStoreLog();
						fileStoreLog.setId(UUID.randomUUID().toString());
						fileStoreLog.setCreatedBy(request.getPerformedBy());
						fileStoreLog.setCreatedAt(request.getPerformedAt());
					}
					
					fileStoreLog.setModule(fileStoreConfig.getModule());
					fileStoreLog.setFileCategory(fileStoreConfig.getFileCategory());
					fileStoreLog.setFileGroup(fileStoreConfig.getFileGroup());
					fileStoreLog.setSeqNo(request.getSeqNo());
					fileStoreLog.setStorageType(fileStoreConfig.getStorageType());
					fileStoreLog.setScheme(fileStoreConfig.getScheme());
					fileStoreLog.setFileURI(putFileResponse.getUri());
					fileStoreLog.setOriginalFileName(request.getFileData().getOriginalFileName());
					fileStoreLog.setFileContentType(request.getFileData().getContentType());
					fileStoreLog.setFileSize((int) (request.getFileData().getSize() / 1_000));
					
					try {
						if(exist) {
							fileStoreLog = fileStoreLogService.update(fileStoreLog);
							logger.info("update fileStoreLog => " + fileStoreLog);
						} else {
							fileStoreLog = fileStoreLogService.create(fileStoreLog);
							logger.info("create fileStoreLog => " + fileStoreLog);
						}
						
						response.setFileStoreLog(fileStoreLog);
					} catch(Exception ex) {
						throw new KwsRuntimeException(ex);
					}
				}
			}
		}
		
		return response;
	}
}