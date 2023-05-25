package com.otc.kws.core.event.login;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.otc.kws.core.domain.ApplicationConfigProvider;
import com.otc.kws.core.domain.constant.KwsConst;
import com.otc.kws.core.domain.service.notification.LineNotifyChannelService;
import com.otc.kws.core.domain.service.notification.line.LineNotifySender;

@Component
public class LoginEventListener 
{
	protected final SimpleDateFormat sdf = new SimpleDateFormat(KwsConst.DEFAULT_DATETIME_FORMAT, Locale.ENGLISH);
	
	@Autowired
	protected LineNotifySender lineNotifyService;
	
	@Autowired
	protected ApplicationConfigProvider applicationConfigProvider;
	
	@Autowired
	protected LineNotifyChannelService lineNotifyChannelService;
	
	
	@EventListener(LoginEvent.class)
	@Async
	public void handleLoginEvent(LoginEvent event)
	{
		String username = event.getUserProfile().getUser().getUsername();
		String userFullName = event.getUserProfile().getUser().getFirstName() + " " + event.getUserProfile().getUser().getLastName();
		
		String msg = "";
		msg += "\nService: " + applicationConfigProvider.getApplicationName();
		msg += "\nWeb ENV: " + applicationConfigProvider.getWebEnvirontment();
		msg += "\nDB ENV: " + applicationConfigProvider.getDatabaseEnvirontment();
		msg += "\nAction: Login";
		msg += "\nPerformed By: " + username + " ["+userFullName+"]";
		msg += "\nPerformed At: " + sdf.format(new Date());
		msg += "\n";
		
		LineNotifySender.Message message = new LineNotifySender.Message();
		message.setText(msg);
		
		String accessToken = lineNotifyChannelService.getKwsAdminChannel().getAccessToken();
		
		lineNotifyService.sendNotify(accessToken, message);
	}
}