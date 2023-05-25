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
import org.springframework.data.repository.config.BootstrapMode;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.otc.kws.core.domain.constant.KwsConst;
import com.otc.kws.core.domain.repository.jpa.DefaultKwsJpaRepository;
import com.zaxxer.hikari.HikariDataSource;

@EnableTransactionManagement
@EntityScan(basePackages = KwsConst.BASE_PACKAGE_NAME)
@EnableJpaRepositories(
    basePackages = KwsConst.BASE_PACKAGE_NAME,
	repositoryBaseClass = DefaultKwsJpaRepository.class,
	entityManagerFactoryRef = "kwsEntityManagerFactory",
    transactionManagerRef= "kwsTransactionManager",
    bootstrapMode = BootstrapMode.DEFAULT
)
public class SingleDataSourceConfig 
{
	@Bean
    @Primary
    @ConfigurationProperties("kws.single.datasource")
    public DataSourceProperties kwsDataSourceProperties() 
	{
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    @ConfigurationProperties("kws.single.datasource.configuration")
    public DataSource kwsDataSource() 
    {
    	HikariDataSource dataSource = kwsDataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
        dataSource.setPoolName("HikariPool-kws");
    	return dataSource;
    }
    
    @Primary
    @Bean(name = "kwsEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean kwsCoreEntityManagerFactory(EntityManagerFactoryBuilder builder) 
    {
        return builder.
        		dataSource(kwsDataSource()).
        		packages(KwsConst.BASE_PACKAGE_NAME).
        		persistenceUnit("Persistence-kws").
        		build();
    }
    
    @Primary
    @Bean(name = "kwsTransactionManager")
    public PlatformTransactionManager kwsTransactionManager(final @Qualifier("kwsEntityManagerFactory") LocalContainerEntityManagerFactoryBean kwsEntityManagerFactory) 
    {
        return new JpaTransactionManager(kwsEntityManagerFactory.getObject());
    }
}