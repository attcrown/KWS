package com.otc.kws.fishsix.lib.config;

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
import com.otc.kws.fishsix.lib.domain.constant.KwsFishsixConst;
import com.otc.kws.fishsix.lib.domain.repository.jpa.DefaultKwsFishsixJpaRepository;
import com.zaxxer.hikari.HikariDataSource;

@EnableTransactionManagement
@EntityScan(basePackages = KwsFishsixConst.FISHSIX_BASE_PACKAGE_NAME)
@EnableJpaRepositories(
    basePackages = KwsFishsixConst.FISHSIX_BASE_PACKAGE_NAME,
	repositoryBaseClass = DefaultKwsFishsixJpaRepository.class,
	entityManagerFactoryRef = "kwsFishsixEntityManagerFactory",
    transactionManagerRef= "kwsFishsixTransactionManager"
)
public class FishsixDatasourceConfig extends AbstractDataSourceConfig
{
	/*
	@Bean
    @ConfigurationProperties("kws.fishsix.datasource")
    public DataSourceProperties kwsFishsixDataSourceProperties() 
	{
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("kws.fishsix.datasource.configuration")
    public DataSource kwsFishsixDataSource() 
    {
    	HikariDataSource dataSource = kwsFishsixDataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
        dataSource.setPoolName("HikariPool.kws-" + KwsFishsixConst.MODULE_NAME);
    	return dataSource;
    }
    */
	
	@Bean
    public DataSource kwsFishsixDataSource() 
    {
    	return getDataSource(KwsFishsixConst.MODULE_NAME);
    }

	/*
    @Bean(name = "kwsFishsixEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean kwsFishsixEntityManagerFactory(EntityManagerFactoryBuilder builder) 
    {
        return builder.
        		dataSource(kwsFishsixDataSource()).
        		persistenceUnit("Persistence.kws-" + KwsFishsixConst.MODULE_NAME).
        		packages(KwsFishsixConst.FISHSIX_BASE_PACKAGE_NAME).
        		build();
    }
    */
	
	@Bean(name = "kwsFishsixEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean kwsFishsixEntityManagerFactory(EntityManagerFactoryBuilder builder) 
    {
        return getEntityManager(KwsFishsixConst.MODULE_NAME, builder);
    }

	/*
    @Bean(name = "kwsFishsixTransactionManager")
    public PlatformTransactionManager kwsFishsixTransactionManager(final @Qualifier("kwsFishsixEntityManagerFactory") LocalContainerEntityManagerFactoryBean kwsFishsixEntityManagerFactory) 
    {
        return new JpaTransactionManager(kwsFishsixEntityManagerFactory.getObject());
    }
    */
	
	@Bean(name = "kwsFishsixTransactionManager")
    public PlatformTransactionManager kwsFishsixTransactionManager(final @Qualifier("kwsFishsixEntityManagerFactory") LocalContainerEntityManagerFactoryBean kwsFishsixEntityManagerFactory) 
    {
        return getTransactionManager(KwsFishsixConst.MODULE_NAME, kwsFishsixEntityManagerFactory);
    }
}