package com.otc.kws.core.domain.service;

import java.util.List;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.otc.kws.core.domain.ApplicationConfigProvider;
import com.otc.kws.core.domain.exception.KwsRuntimeException;

@Service
public class EmailService 
{
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	protected ApplicationConfigProvider applicationConfigProvider;
	
	@Autowired
    private JavaMailSender mailSender;
	
	
	public void sendMail(List<String> toList, String subject, String content) 
    {
        sendMail(applicationConfigProvider.getSmtpDefaultFromEmail(), applicationConfigProvider.getSmtpDefaultFromName(), toList, subject, content);
    }

    public void sendMail(String fromEmail, String fromName, List<String> toList, String subject, String content) 
    {
        logger.debug("### EmailService.sendMail ###");
        
        if(fromEmail == null) {
        	fromEmail = applicationConfigProvider.getSmtpDefaultFromEmail();
        }
        
        if(fromName == null) {
        	fromName = applicationConfigProvider.getSmtpDefaultFromName();
        }
        
        logger.debug("fromEmail => "+fromEmail);
        logger.debug("toList => "+toList);
        logger.debug("subject => "+subject);
        logger.debug("content => "+content);
        
        try {
            MimeMessage mail = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mail, true);
            helper.setFrom(fromEmail, fromName);
            for(String to : toList) {
                helper.addTo(to);
            }
            helper.setSubject(subject);
            helper.setText(content, true);
            mailSender.send(mail);
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
            throw new KwsRuntimeException(ex);
        }
    }
}