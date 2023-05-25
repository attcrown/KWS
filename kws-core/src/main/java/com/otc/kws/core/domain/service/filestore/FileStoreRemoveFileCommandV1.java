package com.otc.kws.core.domain.service.filestore;

import org.springframework.stereotype.Component;

import com.otc.kws.core.domain.constant.FileStoreConst;
import com.otc.kws.core.domain.entity.FileStoreLog;

@Component
public class FileStoreRemoveFileCommandV1 extends FileStoreRemoveFileCommand
{
	@Override
	public Response removeFile(Request request) 
	{
		Response response = new Response();
		
		if(request.getFileURI().startsWith(FileStoreConst.SCHEME_FS)) {
			boolean deleted = fileSystemStoreService.removeFile(req -> {
				req.copyFrom(request);
				req.setFileURI(request.getFileURI());
			}).isDeleted();
			
			response.setDeleted(deleted);
			
			if(deleted) {
				FileStoreLog removedFileStoreLog = fileStoreLogService.removeByFileURI(request.getFileURI());
				if(removedFileStoreLog != null) {
					response.setRemovedFileStoreLog(removedFileStoreLog);
					logger.info("remove removedFileStoreLog => [{}]", removedFileStoreLog);
				}
			}
		}
		
		return response;
	}
}