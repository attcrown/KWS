package com.otc.kws.core.domain;

import org.springframework.beans.factory.annotation.Value;

import com.otc.kws.core.domain.constant.KwsConst;

public abstract class ApplicationConfigProvider 
{
	@Value("${"+KwsConst.CONFIG_APPLICATION_NAME+"}")
	protected String applicationName;
	
	@Value("${"+KwsConst.CONFIG_WEB_SESSION_TIMEOUT+"}")
	protected long webSessionTimeout;
	
	// ****************************** email ****************************** //
	@Value("${spring.mail.host}")
    protected String smtpServerName;
    
    @Value("${spring.mail.port}")
    protected int smtpServerPort;
    
    @Value("${email.smtp.defaultFromEmail}")
    protected String smtpDefaultFromEmail;
    
    @Value("${email.smtp.defaultFromName}")
    protected String smtpDefaultFromName;
    
    @Value("${email.smtp.defaultCharset}")
    protected String smtpDefaultCharset;
    
    @Value("${spring.mail.properties.mail.smtp.auth}")
    protected boolean smtpAuthenticate;
    
    @Value("${spring.mail.username}")
    protected String smtpUsername;
    
    @Value("${spring.mail.password}")
    protected String smtpPassword;
    
    @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
    protected boolean smtpUseSSL;
    // ****************************** email ****************************** //
	
	
	public String getApplicationName()
	{
		return applicationName;
	}
	
	public abstract String getWebEnvirontment();
	public abstract String getDatabaseEnvirontment();
	
	public long getWebSessionTimeout()
	{
		return webSessionTimeout;
	}
	
	// ****************************** email ****************************** //
	public String getSmtpServerName()
	{
		return smtpServerName;
	}
	
	public int getSmtpServerPort()
	{
		return smtpServerPort;
	}
	
	public String getSmtpDefaultFromEmail()
	{
		return smtpDefaultFromEmail;
	}
	
	public String getSmtpDefaultFromName()
	{
		return smtpDefaultFromName;
	}
	
	public String getSmtpDefaultCharset()
	{
		return smtpDefaultCharset;
	}
	
	public boolean isSmtpAuthenticate()
	{
		return smtpAuthenticate;
	}
	
	public String getSmtpUsername()
	{
		return smtpUsername;
	}
	
	public String getSmtpPassword()
	{
		return smtpPassword;
	}
	
	public boolean isSmtpUseSSL()
	{
		return smtpUseSSL;
	}
	// ****************************** email ****************************** //
}