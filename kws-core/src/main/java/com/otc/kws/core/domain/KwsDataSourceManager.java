package com.otc.kws.core.domain;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

import com.otc.kws.core.domain.constant.KwsConst;
import com.zaxxer.hikari.HikariDataSource;

@Component
public class KwsDataSourceManager 
{
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	protected Environment environment;
	
	protected Map<String, HikariDataSource> dataSourceMap;
	protected Map<String, LocalContainerEntityManagerFactoryBean> entityManagerMap;
	protected Map<String, PlatformTransactionManager> transactionManagerMap;
	
	
	public KwsDataSourceManager()
	{
		dataSourceMap = new HashMap<>();
		entityManagerMap = new HashMap<>();
		transactionManagerMap = new HashMap<>();
	}
	
	
	public HikariDataSource getDataSource(String moduleName)
	{
		logger.info("### {}.getDataSource ###", getClass().getSimpleName());
		logger.info("moduleName => {}", moduleName);
		
		String dsName = environment.getProperty("kws."+moduleName+".datasource.name");
		logger.info("dsName => {}", dsName);
		
		HikariDataSource dataSource = dataSourceMap.get(dsName);
		
		if(dataSource == null) {
			logger.info("Create new datasource [{}]", dsName);
			
			String url = environment.getProperty("kws."+moduleName+".datasource.url");
			String username = environment.getProperty("kws."+moduleName+".datasource.username");
			String password = environment.getProperty("kws."+moduleName+".datasource.password");
			
			dataSource = new HikariDataSource();
			dataSource.setJdbcUrl(url);
			dataSource.setUsername(username);
			dataSource.setPassword(password);
			dataSource.setPoolName("HikariPool-" + dsName);
			
			dataSourceMap.put(dsName, dataSource);
		}
		
		return dataSource;
	}
	
	
	public LocalContainerEntityManagerFactoryBean getEntityManager(String moduleName, EntityManagerFactoryBuilder builder)
	{
		logger.info("### {}.getEntityManager ###", getClass().getSimpleName());
		logger.info("moduleName => {}", moduleName);
		
		String dsName = environment.getProperty("kws."+moduleName+".datasource.name");
		logger.info("dsName => {}", dsName);
		
		LocalContainerEntityManagerFactoryBean entityManager = entityManagerMap.get(dsName);
		
		if(entityManager == null) {
			logger.info("Create new entityManager [{}]", dsName);
			
			entityManager = builder.
	        		dataSource(getDataSource(moduleName)).
	        		packages(KwsConst.CORE_BASE_PACKAGE_NAME).
	        		persistenceUnit("Persistence-" + dsName).
	        		build();
			
			entityManagerMap.put(dsName, entityManager);
		}
		
		return entityManager;
	}
	
	
	public PlatformTransactionManager getTransactionManager(String moduleName, LocalContainerEntityManagerFactoryBean entityManager)
	{
		logger.info("### {}.getTransactionManager ###", getClass().getSimpleName());
		logger.info("moduleName => {}", moduleName);
		
		String dsName = environment.getProperty("kws."+moduleName+".datasource.name");
		logger.info("dsName => {}", dsName);
		
		PlatformTransactionManager transactionManager = transactionManagerMap.get(dsName);
		
		if(transactionManager == null) {
			logger.info("Create new transactionManager [{}]", dsName);
			
			transactionManager = new JpaTransactionManager(entityManager.getObject());
			
			transactionManagerMap.put(dsName, transactionManager);
		}
		
		return transactionManager;
	}
}