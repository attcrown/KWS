package com.otc.kws.marketpet.lib.config;

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
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.otc.kws.core.config.AbstractDataSourceConfig;
import com.otc.kws.marketpet.lib.domain.constant.KwsMarketPetConst;
import com.otc.kws.marketpet.lib.domain.repository.jpa.DefaultKwsMarketPetJpaRepository;
import com.zaxxer.hikari.HikariDataSource;

@EnableTransactionManagement
@EntityScan(basePackages = KwsMarketPetConst.MARKETPET_BASE_PACKAGE_NAME)
@EnableJpaRepositories(
    basePackages = KwsMarketPetConst.MARKETPET_BASE_PACKAGE_NAME,
	repositoryBaseClass = DefaultKwsMarketPetJpaRepository.class,
	entityManagerFactoryRef = "kwsMarketPetEntityManagerFactory",
    transactionManagerRef= "kwsMarketPetTransactionManager"
)
public class MarketPetDatasourceConfig extends AbstractDataSourceConfig
{
	/*
	@Bean
    @ConfigurationProperties("kws.marketpet.datasource")
    public DataSourceProperties kwsMarketPetDataSourceProperties() 
	{
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("kws.marketpet.datasource.configuration")
    public DataSource kwsMarketPetDataSource() 
    {
    	HikariDataSource dataSource = kwsMarketPetDataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
        dataSource.setPoolName("HikariPool.kws-" + KwsMarketPetConst.MODULE_NAME);
    	return dataSource;
    }
    */
	
	@Bean
    public DataSource kwsMarketPetDataSource() 
    {
    	return getDataSource(KwsMarketPetConst.MODULE_NAME);
    }

	/*
    @Bean(name = "kwsMarketPetEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean kwsMarketPetEntityManagerFactory(EntityManagerFactoryBuilder builder) 
    {
        return builder.
        		dataSource(kwsMarketPetDataSource()).
        		persistenceUnit("Persistence.kws-" + KwsMarketPetConst.MODULE_NAME).
        		packages(KwsMarketPetConst.MARKETPET_BASE_PACKAGE_NAME).
        		build();
    }
    */
	
	@Bean(name = "kwsMarketPetEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean kwsMarketPetEntityManagerFactory(EntityManagerFactoryBuilder builder) 
    {
        return getEntityManager(KwsMarketPetConst.MODULE_NAME, builder);
    }

	/*
    @Bean(name = "kwsMarketPetTransactionManager")
    public PlatformTransactionManager kwsMarketPetTransactionManager(final @Qualifier("kwsMarketPetEntityManagerFactory") LocalContainerEntityManagerFactoryBean kwsMarketPetEntityManagerFactory) 
    {
        return new JpaTransactionManager(kwsMarketPetEntityManagerFactory.getObject());
    }
    */
	
	@Bean(name = "kwsMarketPetTransactionManager")
    public PlatformTransactionManager kwsMarketPetTransactionManager(final @Qualifier("kwsMarketPetEntityManagerFactory") LocalContainerEntityManagerFactoryBean kwsMarketPetEntityManagerFactory) 
    {
        return getTransactionManager(KwsMarketPetConst.MODULE_NAME, kwsMarketPetEntityManagerFactory);
    }
}