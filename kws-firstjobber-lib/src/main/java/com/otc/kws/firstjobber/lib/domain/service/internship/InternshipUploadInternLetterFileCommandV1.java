package com.otc.kws.firstjobber.lib.domain.service.internship;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.otc.kws.core.domain.constant.FileStoreConst;
import com.otc.kws.core.domain.constant.value.ModuleValue;
import com.otc.kws.core.domain.service.filestore.FileStorePutFileCommand;

@Component
public class InternshipUploadInternLetterFileCommandV1 extends InternshipUploadInternLetterFileCommand
{
	@Override
	public Response uploadInternLetterFile(Request request) 
	{
		FileStorePutFileCommand.Response putFileResponse = fileStoreService.putFile(req -> {
			Map<String, String> variables = new HashMap<>();
			variables.put(FileStoreConst.VAR_INTERNSHIP_INTERSHIP_ID, request.getInternshipId());
			
			req.copyFrom(request);
			req.setModule(ModuleValue.first_jobber.name());
			req.setFileCategory(FileStoreConst.FILE_CATEGORY_INTERNSHIP);
			req.setFileGroup(FileStoreConst.FILE_GROUP_INTERNSHIP_INTERN_LETTER);
			req.setVariables(variables);
			req.setFileData(request.getInternLetterFile());
		 });
		 
		 Response response = new Response();
		 response.setUri(putFileResponse.getUri());
		 response.setUrl(putFileResponse.getUrl());
		 response.setFileStoreLog(putFileResponse.getFileStoreLog());
		
		return response;
	}
}