package com.otc.kws.core.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.otc.kws.core.domain.constant.KwsConst;
import com.otc.kws.core.domain.repository.jpa.DefaultKwsJpaRepository;
import com.zaxxer.hikari.HikariDataSource;

@EnableTransactionManagement
@EntityScan(basePackages = KwsConst.CORE_BASE_PACKAGE_NAME)
@EnableJpaRepositories(
    basePackages = KwsConst.CORE_BASE_PACKAGE_NAME,
	repositoryBaseClass = DefaultKwsJpaRepository.class,
	entityManagerFactoryRef = "kwsCoreEntityManagerFactory",
    transactionManagerRef= "kwsCoreTransactionManager"
)
public abstract class CoreDatasourceConfig extends AbstractDataSourceConfig
{
	/*
	@Bean
    @Primary
    @ConfigurationProperties("kws.core.datasource")
    public DataSourceProperties kwsCoreDataSourceProperties() 
	{
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    @ConfigurationProperties("kws.core.datasource.configuration")
    public DataSource kwsCoreDataSource() 
    {
    	HikariDataSource dataSource = kwsCoreDataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
        dataSource.setPoolName("HikariPool.kws-" + KwsConst.MODULE_NAME);
    	return dataSource;
    }
    */
	
	@Bean
    @Primary
    public DataSource kwsCoreDataSource() 
    {
    	return getDataSource(KwsConst.MODULE_NAME);
    }

	/*
    @Primary
    @Bean(name = "kwsCoreEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean kwsCoreEntityManagerFactory(EntityManagerFactoryBuilder builder) 
    {
        return builder.
        		dataSource(kwsCoreDataSource()).
        		packages(KwsConst.CORE_BASE_PACKAGE_NAME).
        		persistenceUnit("Persistence.kws-" + KwsConst.MODULE_NAME).
        		build();
    }
    */
	
	@Primary
    @Bean(name = "kwsCoreEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean kwsCoreEntityManagerFactory(EntityManagerFactoryBuilder builder) 
    {
        return getEntityManager(KwsConst.MODULE_NAME, builder);
    }

	/*
    @Bean(name = "kwsCoreTransactionManager")
    public PlatformTransactionManager kwsCoreTransactionManager(final @Qualifier("kwsCoreEntityManagerFactory") LocalContainerEntityManagerFactoryBean kwsCoreEntityManagerFactory) 
    {
        return new JpaTransactionManager(kwsCoreEntityManagerFactory.getObject());
    }
    */
	
	@Bean(name = "kwsCoreTransactionManager")
    public PlatformTransactionManager kwsCoreTransactionManager(final @Qualifier("kwsCoreEntityManagerFactory") LocalContainerEntityManagerFactoryBean kwsCoreEntityManagerFactory) 
    {
        return getTransactionManager(KwsConst.MODULE_NAME, kwsCoreEntityManagerFactory);
    }
}