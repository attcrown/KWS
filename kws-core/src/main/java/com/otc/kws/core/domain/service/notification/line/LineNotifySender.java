package com.otc.kws.core.domain.service.notification.line;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.otc.kws.core.domain.service.ConfigService;

import lombok.Data;

@Service
public class LineNotifySender 
{
	@Autowired
	protected ConfigService configService;
	
	
	public String sendNotify(String accessToken, Message message)
	{
		RestTemplate restTemplate = new RestTemplate();
		
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("message", message.getText());
        params.add("stickerPackageId", message.getStickerPackageId());
		params.add("stickerId", message.getStickerId());
		params.add("imageThumbnail", message.getImageThumbnailURL());
		params.add("imageFullsize", message.getImageFullSizeURL());
		
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(params, headers);
		
		String endPoint = configService.getLineNotifyEndpoint();
        ResponseEntity<String> response = restTemplate.postForEntity(endPoint, request, String.class);
        System.out.println("response => " + response);
        return response.getBody();
	}
	
	
	@Data
	public static class Message
	{
		protected String text;
		protected String stickerPackageId;
		protected String stickerId;
		protected String imageThumbnailURL;
		protected String imageFullSizeURL;
	}
}