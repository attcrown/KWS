package com.otc.kws.firstjobber.lib.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.otc.kws.core.config.AbstractDataSourceConfig;
import com.otc.kws.firstjobber.lib.domain.constant.KwsFirstJobberConst;
import com.otc.kws.firstjobber.lib.domain.repository.jpa.DefaultKwsFirstJobberJpaRepository;
import com.zaxxer.hikari.HikariDataSource;

@EnableTransactionManagement
@EntityScan(basePackages = KwsFirstJobberConst.FIRSTJOBBER_BASE_PACKAGE_NAME)
@EnableJpaRepositories(
    basePackages = KwsFirstJobberConst.FIRSTJOBBER_BASE_PACKAGE_NAME,
	repositoryBaseClass = DefaultKwsFirstJobberJpaRepository.class,
	entityManagerFactoryRef = "kwsFirstJobberEntityManagerFactory",
    transactionManagerRef= "kwsFirstJobberTransactionManager"
)
public abstract class FirstJobberDatasourceConfig extends AbstractDataSourceConfig
{
	/*
	@Bean
    @ConfigurationProperties("kws.firstjobber.datasource")
    public DataSourceProperties kwsFirstJobberDataSourceProperties() 
	{
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("kws.firstjobber.datasource.configuration")
    public DataSource kwsFirstJobberDataSource() 
    {
    	HikariDataSource dataSource = kwsFirstJobberDataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
        dataSource.setPoolName("HikariPool.kws-" + KwsFirstJobberConst.MODULE_NAME);
    	return dataSource;
    }
    */
	
	@Bean
    public DataSource kwsFirstJobberDataSource() 
    {
    	return getDataSource(KwsFirstJobberConst.MODULE_NAME);
    }

	/*
    @Bean(name = "kwsFirstJobberEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean kwsFirstJobberEntityManagerFactory(EntityManagerFactoryBuilder builder) 
    {
        return builder.
        		dataSource(kwsFirstJobberDataSource()).
        		persistenceUnit("Persistence.kws-" + KwsFirstJobberConst.MODULE_NAME).
        		packages(KwsFirstJobberConst.FIRSTJOBBER_BASE_PACKAGE_NAME).
        		build();
    }
    */
	
	@Bean(name = "kwsFirstJobberEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean kwsFirstJobberEntityManagerFactory(EntityManagerFactoryBuilder builder) 
    {
        return getEntityManager(KwsFirstJobberConst.MODULE_NAME, builder);
    }

	/*
    @Bean(name = "kwsFirstJobberTransactionManager")
    public PlatformTransactionManager kwsFirstJobberTransactionManager(final @Qualifier("kwsFirstJobberEntityManagerFactory") LocalContainerEntityManagerFactoryBean kwsFirstJobberEntityManagerFactory) 
    {
        return new JpaTransactionManager(kwsFirstJobberEntityManagerFactory.getObject());
    }
    */
	
	@Bean(name = "kwsFirstJobberTransactionManager")
    public PlatformTransactionManager kwsFirstJobberTransactionManager(final @Qualifier("kwsFirstJobberEntityManagerFactory") LocalContainerEntityManagerFactoryBean kwsFirstJobberEntityManagerFactory) 
    {
        return getTransactionManager(KwsFirstJobberConst.MODULE_NAME, kwsFirstJobberEntityManagerFactory);
    }
}