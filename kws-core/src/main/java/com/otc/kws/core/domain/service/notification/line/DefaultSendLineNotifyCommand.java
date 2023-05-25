package com.otc.kws.core.domain.service.notification.line;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.otc.kws.core.domain.constant.LineNotifyTemplateConst;
import com.otc.kws.core.domain.constant.value.MasterStatusValue;
import com.otc.kws.core.domain.entity.LineNotifyChannel;
import com.otc.kws.core.domain.entity.LineNotifyTemplate;
import com.otc.kws.core.domain.entity.NotificationConfig;

@Component
public class DefaultSendLineNotifyCommand extends SendLineNotifyCommand
{
	@Override
	public Response sendLineNotify(Request request) 
	{
		Response response = new Response();
		
		NotificationConfig notificationConfig = request.getNotificationConfig();
		
		if(notificationConfig == null && request.getNotificationConfigId() != null) {
			notificationConfig = notificationConfigService.getById(request.getNotificationConfigId());
		}
		
		if(notificationConfig != null) {
			response.setNotificationConfig(notificationConfig);
			
			List<LineNotifyTemplate> lineNotifyTemplates = lineNotifyTemplateService.findAllActiveByNotificationConfigId(notificationConfig.getId());
			
			if(lineNotifyTemplates != null) {
				String text;
				String imageThumbnailURL;
				String imageFullSizeURL;
				String stickerPackageId;
				String stickerId;
				
				List<Response.ResponseItem> responseItems = new ArrayList<>();
				
				for(LineNotifyTemplate lineNotifyTemplate : lineNotifyTemplates) {
					text = null;
					imageThumbnailURL = null;
					imageFullSizeURL = null;
					stickerPackageId = null;
					stickerId = null;
					
					LineNotifyChannel lineNotifyChannel = lineNotifyChannelService.getById(lineNotifyTemplate.getChannelId());
					
					if(lineNotifyChannel == null || lineNotifyChannel.getStatus() == MasterStatusValue.Inactive) {
						continue;
					}
					
					if(lineNotifyTemplate.getTextTemplate() != null) {
						text = lineNotifyTemplate.getTextTemplate();
						if(request.getVariables() != null && !request.getVariables().isEmpty()) {
							for(String key : request.getVariables().keySet()) {
								text = text.replace(key, request.getVariables().get(key));
							}
						}
					}
					
					if(lineNotifyTemplate.getImageThumbnailURL() != null) {
						imageThumbnailURL = lineNotifyTemplate.getImageThumbnailURL();
						if(imageThumbnailURL != null && imageThumbnailURL.contains(LineNotifyTemplateConst.VAR_IMAGE_THUMBNAIL_URL)) {
							imageThumbnailURL = imageThumbnailURL.replace(LineNotifyTemplateConst.VAR_IMAGE_THUMBNAIL_URL, request.getVariables().get(LineNotifyTemplateConst.VAR_IMAGE_THUMBNAIL_URL));
						}
					}
					
					if(lineNotifyTemplate.getImageFullSizeURL() != null) {
						imageFullSizeURL = lineNotifyTemplate.getImageFullSizeURL();
						if(imageFullSizeURL != null && imageFullSizeURL.contains(LineNotifyTemplateConst.VAR_IMAGE_FULL_SIZE_URL)) {
							imageFullSizeURL = imageFullSizeURL.replace(LineNotifyTemplateConst.VAR_IMAGE_FULL_SIZE_URL, request.getVariables().get(LineNotifyTemplateConst.VAR_IMAGE_FULL_SIZE_URL));
						}
					}
					
					if(lineNotifyTemplate.getStickerPackageId() != null) {
						stickerPackageId = lineNotifyTemplate.getStickerPackageId();
						if(stickerPackageId != null && stickerPackageId.contains(LineNotifyTemplateConst.VAR_STICKER_PACKAGE_ID)) {
							stickerPackageId = stickerPackageId.replace(LineNotifyTemplateConst.VAR_STICKER_PACKAGE_ID, request.getVariables().get(LineNotifyTemplateConst.VAR_STICKER_PACKAGE_ID));
						}
					}
					
					if(lineNotifyTemplate.getStickerId() != null) {
						stickerId = lineNotifyTemplate.getStickerId();
						if(stickerId != null && stickerId.contains(LineNotifyTemplateConst.VAR_STICKER_ID)) {
							stickerId = stickerId.replace(LineNotifyTemplateConst.VAR_STICKER_ID, request.getVariables().get(LineNotifyTemplateConst.VAR_STICKER_ID));
						}
					}
					
					LineNotifySender.Message message = new LineNotifySender.Message();
					if(text != null) {
						message.setText(text);
					}
					if(imageThumbnailURL != null && imageFullSizeURL != null) {
						message.setImageThumbnailURL(imageThumbnailURL);
						message.setImageFullSizeURL(imageFullSizeURL);
					}
					if(stickerPackageId != null && stickerId != null) {
						message.setStickerPackageId(stickerPackageId);
						message.setStickerId(stickerId);
					}
					
					String rawItem = lineNotifySender.sendNotify(lineNotifyChannel.getAccessToken(), message);
					logger.info("Send Line Notify to channel ["+lineNotifyChannel.getChannelName()+"] with message ["+message+"]");
					
					Response.ResponseItem responseItem = new Response.ResponseItem();
					responseItem.setLineNotifyTemplate(lineNotifyTemplate);
					responseItem.setRawResponse(rawItem);
					
					responseItems.add(responseItem);
				}
				
				response.setResponseItems(responseItems);
			}
		}
		
		return response;
	}
}