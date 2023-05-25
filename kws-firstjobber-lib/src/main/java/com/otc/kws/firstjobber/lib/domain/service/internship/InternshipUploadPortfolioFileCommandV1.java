package com.otc.kws.firstjobber.lib.domain.service.internship;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.otc.kws.core.domain.constant.FileStoreConst;
import com.otc.kws.core.domain.constant.value.ModuleValue;
import com.otc.kws.core.domain.model.FileData;
import com.otc.kws.core.domain.service.filestore.FileStorePutFileCommand;

@Component
public class InternshipUploadPortfolioFileCommandV1 extends InternshipUploadPortfolioFileCommand
{
	@Override
	public Response uploadPortfolioFile(Request request) 
	{
		Response response = new Response();
		
		if(request.getPortfolioFiles() != null && !request.getPortfolioFiles().isEmpty()) {
			int count = 0;
			List<Response.ReplyItem> replyItems = new ArrayList<>();
			
			for(FileData portfolio : request.getPortfolioFiles()) {
				if(count >= 5) {
					break;
				}
				
				int seqNo = ++count;
				
				FileStorePutFileCommand.Response putFileResponse = fileStoreService.putFile(req -> {
					Map<String, String> variables = new HashMap<>();
					variables.put(FileStoreConst.VAR_INTERNSHIP_INTERSHIP_ID, request.getInternshipId());
					
					req.copyFrom(request);
					req.setModule(ModuleValue.first_jobber.name());
					req.setFileCategory(FileStoreConst.FILE_CATEGORY_INTERNSHIP);
					req.setFileGroup(FileStoreConst.FILE_GROUP_INTERNSHIP_PORTFOLIOS);
					req.setVariables(variables);
					req.setSeqNo(seqNo);
					req.setFileData(portfolio);
				});
				
				replyItems.add(new Response.ReplyItem(putFileResponse.getUri(), putFileResponse.getUrl(), putFileResponse.getFileStoreLog()));
			}
			
			response.setReplyItems(replyItems);
		}
		
		return response;
	}
}