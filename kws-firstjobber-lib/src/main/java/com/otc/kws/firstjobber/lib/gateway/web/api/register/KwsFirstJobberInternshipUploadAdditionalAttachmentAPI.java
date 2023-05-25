package com.otc.kws.firstjobber.lib.gateway.web.api.register;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.otc.kws.core.gateway.web.api.ResponseMessage;
import com.otc.kws.firstjobber.lib.domain.entity.InternshipRegister;
import com.otc.kws.firstjobber.lib.gateway.web.api.BaseKwsFirstJobberAPI;
import com.otc.kws.firstjobber.lib.gateway.web.controller.KwsFirstJobberBaseController;

import lombok.Data;

@Controller
@RequestMapping(KwsFirstJobberBaseController.PREFIX_PATH + "/registration/internship")
public class KwsFirstJobberInternshipUploadAdditionalAttachmentAPI extends BaseKwsFirstJobberAPI
{
	@PostMapping("/upload-additional-attachment")
	public ResponseEntity<?> uploadAdditionalAttachment(HttpServletRequest request, @ModelAttribute RequestMessage requestMessage)
	{
		logger.info("### KwsFirstJobberInternshipUploadAdditionalAttachmentAPI.uploadAdditionalAttachment ###");
		
		BodyResponseMessage bodyResponseMessage = new BodyResponseMessage();
		
		return replySuccess(request, bodyResponseMessage);
	}
	
	
	@Data
	public static class RequestMessage
	{
		protected MultipartFile idcardFile;
		protected MultipartFile houseParticularFile;
		protected MultipartFile educationalQualificationFile;
		protected MultipartFile bookbankFile;
	}
	
	
	@Data
	public static class BodyResponseMessage extends ResponseMessage.BaseBody
	{
		protected InternshipRegister createdInternshipRegister;
	}
}