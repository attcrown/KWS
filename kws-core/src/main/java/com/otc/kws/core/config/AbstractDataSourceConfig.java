package com.otc.kws.core.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import com.otc.kws.core.domain.KwsDataSourceManager;
import com.zaxxer.hikari.HikariDataSource;

public abstract class AbstractDataSourceConfig 
{
	@Autowired
	protected KwsDataSourceManager kwsDataSourceManager;
	
	
	public HikariDataSource getDataSource(String moduleName)
	{
		return kwsDataSourceManager.getDataSource(moduleName);
	}
	
	
	public LocalContainerEntityManagerFactoryBean getEntityManager(String moduleName, EntityManagerFactoryBuilder builder)
	{
		return kwsDataSourceManager.getEntityManager(moduleName, builder);
	}
	
	
	public PlatformTransactionManager getTransactionManager(String moduleName, LocalContainerEntityManagerFactoryBean entityManager)
	{
		return kwsDataSourceManager.getTransactionManager(moduleName, entityManager);
	}
}