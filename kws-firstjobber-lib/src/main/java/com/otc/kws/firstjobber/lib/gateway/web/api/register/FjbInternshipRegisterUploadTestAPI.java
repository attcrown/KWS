package com.otc.kws.firstjobber.lib.gateway.web.api.register;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.otc.kws.core.domain.model.FileData;
import com.otc.kws.core.gateway.web.api.ResponseMessage;
import com.otc.kws.firstjobber.lib.domain.entity.FjbInternshipRegisterTest;
import com.otc.kws.firstjobber.lib.domain.service.InternshipRegisterService;
import com.otc.kws.firstjobber.lib.domain.service.internship.FjbInternshipUploadTestCommand;
import com.otc.kws.firstjobber.lib.gateway.web.api.BaseKwsFirstJobberAPI;

import lombok.Data;

@RestController
@RequestMapping(BaseKwsFirstJobberAPI.PREFIX_PATH + "/registration/internship")
public class FjbInternshipRegisterUploadTestAPI extends BaseKwsFirstJobberAPI
{
	@Autowired
	protected InternshipRegisterService internshipRegisterService;
	
	
	@PostMapping("/upload-test")
	public ResponseEntity<?> uploadTest(HttpServletRequest request, @ModelAttribute RequestMessage requestMessage) throws Exception
	{
		logger.info("### FjbInternshipRegisterUploadTestAPI.uploadTest ###");
		logger.info("requestMessage => " + requestMessage);
		
		FileData sixteenPersonalityImageFileData = buildFileData(requestMessage.getSixteenPersonalityImageFile());
		FileData high5ImageFileData = buildFileData(requestMessage.getHigh5ImageFile());
		FileData iqImageFileData = buildFileData(requestMessage.getIqImageFile());
		
		FjbInternshipUploadTestCommand.Response uploadTestResponse = internshipRegisterService.uploadTest(req -> {
			setupServiceRequest(request, req);
			
			FjbInternshipRegisterTest internshipRegisterTest = new FjbInternshipRegisterTest();
			internshipRegisterTest.setId(requestMessage.getId());
			internshipRegisterTest.setInternshipRegisterId(requestMessage.getInternshipRegisterId());
			internshipRegisterTest.setSixteenPersonalityCategoryId(requestMessage.getSixteenPersonalityCategoryId());
			internshipRegisterTest.setHigh5CategoryId1(requestMessage.getHigh5CategoryId1());
			internshipRegisterTest.setHigh5CategoryId2(requestMessage.getHigh5CategoryId2());
			internshipRegisterTest.setHigh5CategoryId3(requestMessage.getHigh5CategoryId3());
			internshipRegisterTest.setHigh5CategoryId4(requestMessage.getHigh5CategoryId4());
			internshipRegisterTest.setHigh5CategoryId5(requestMessage.getHigh5CategoryId5());
			internshipRegisterTest.setIqScore(requestMessage.getIqScore());
			
			req.setInternshipRegisterTest(internshipRegisterTest);
			req.setSixteenPersonalityImageFile(sixteenPersonalityImageFileData);
			req.setHigh5ImageFile(high5ImageFileData);
			req.setIqImageFile(iqImageFileData);
		});
		
		BodyResponseMessage bodyResponseMessage = new BodyResponseMessage();
		bodyResponseMessage.setUploadedInternshipRegisterTest(uploadTestResponse.getUploadedInternshipRegisterTest());
		bodyResponseMessage.setSixteenPersonalityImageURL(uploadTestResponse.getSixteenPersonalityImageURL());
		uploadTestResponse.setHigh5ImageURL(uploadTestResponse.getHigh5ImageURL());
		uploadTestResponse.setIqImageURL(uploadTestResponse.getIqImageURL());
		
		return replySuccess(request, bodyResponseMessage);
	}
	
	
	@Data
	public static class RequestMessage
	{
		protected String id;
		protected String internshipRegisterId;
		protected String sixteenPersonalityCategoryId;
		protected String high5CategoryId1;
		protected String high5CategoryId2;
		protected String high5CategoryId3;
		protected String high5CategoryId4;
		protected String high5CategoryId5;
		protected int iqScore;

		protected MultipartFile sixteenPersonalityImageFile;
		protected MultipartFile high5ImageFile;
		protected MultipartFile iqImageFile;
	}
	
	
	@Data
	public static class BodyResponseMessage extends ResponseMessage.BaseBody
	{
		protected FjbInternshipRegisterTest uploadedInternshipRegisterTest;
		protected String sixteenPersonalityImageURL;
		protected String high5ImageURL;
		protected String iqImageURL;
	}
}