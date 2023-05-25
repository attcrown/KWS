package com.otc.kws.core.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.session.jdbc.config.annotation.SpringSessionDataSource;
import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;
import org.springframework.transaction.PlatformTransactionManager;

import com.otc.kws.core.domain.KwsDataSourceManager;
import com.zaxxer.hikari.HikariDataSource;

public class SpringSessionConfig extends AbstractHttpSessionApplicationInitializer
{
	@Value("${kws.session.datasource.url}")
	protected String datasourceURL;
	
	@Value("${kws.session.datasource.username}")
	protected String datasourceUsername;
	
	@Value("${kws.session.datasource.password}")
	protected String datasourcePassword;
	
	@Autowired
	protected KwsDataSourceManager kwsDataSourceManager;
	
	@Autowired
	protected DataSource dataSource;
	
	/*
	@Bean
	@SpringSessionDataSource
	public HikariDataSource springSessionDataSource()
	{
		HikariDataSource dataSource = new HikariDataSource();
		dataSource.setJdbcUrl(datasourceURL);
		dataSource.setUsername(datasourceUsername);
		dataSource.setPassword(datasourcePassword);
		dataSource.setPoolName("HikariPool.kws-session");
		return dataSource;
	}
	*/
	
	@Bean
	@SpringSessionDataSource
	public DataSource springSessionDataSource()
	{
		//return kwsDataSourceManager.getDataSource("session");
		return dataSource;
	}
    
    @Bean
	public PlatformTransactionManager springSessionTransactionManager() 
    {
		return new DataSourceTransactionManager(springSessionDataSource()); 
	}
}