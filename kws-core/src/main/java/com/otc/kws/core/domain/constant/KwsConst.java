package com.otc.kws.core.domain.constant;

public interface KwsConst 
{
	public static final String BASE_PACKAGE_NAME = "com.otc.kws";
	public static final String CORE_BASE_PACKAGE_NAME = "com.otc.kws.core";
	
	public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
	public static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";
	public static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String DEFAULT_TIMEZONE = "Asia/Bangkok";
	
	public static final String MODULE_NAME = "core";
	
	public static final String CONFIG_APPLICATION_NAME = "spring.application.name";
	public static final String CONFIG_SERVER_URL = "kws-console.server.base_url";
	public static final String CONFIG_WEB_SESSION_TIMEOUT = "server.servlet.session.timeout";
	
	public static final String HTTP_HEADER_ACCESS_TOKEN = "kws-access-token";
	public static final String HTTP_PARAM_ACCESS_TOKEN = "kws-access-token";
	public static final String HTTP_PARAM_RESOURCE_ACCESS_TOKEN = "kws-resource-access-token";
	
	public static final String HTTP_REQ_ATTR_USER_PROFILE = "http.request.attribute.userProfile";
	public static final String HTTP_REQ_ATTR_AUTH_SESSION = "http.request.attribute.authSession";
    public static final String HTTP_REQ_ATTR_CURRENT_API = "http.request.attribute.currentAPI";
    public static final String HTTP_REQ_ATTR_CURRENT_WEB = "http.request.attribute.currentWeb";
    public static final String HTTP_REQ_ATTR_ALL_GRANTED_AUTHORITY = "http.request.attribute.allGrantedAuthority";
    public static final String HTTP_REQ_ATTR_CURRENT_GRANTED_AUTHORITY = "http.request.attribute.currentGrantedAuthority";
}