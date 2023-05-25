package com.otc.kws.esc.lib.config;

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

import com.otc.kws.esc.lib.domain.constant.KwsEscConst;
import com.otc.kws.esc.lib.domain.repository.jpa.DefaultKwsEscJpaRepository;
import com.zaxxer.hikari.HikariDataSource;

@EnableTransactionManagement
@EntityScan(basePackages = KwsEscConst.ESC_BASE_PACKAGE_NAME)
@EnableJpaRepositories(
    basePackages = KwsEscConst.ESC_BASE_PACKAGE_NAME,
	repositoryBaseClass = DefaultKwsEscJpaRepository.class,
	entityManagerFactoryRef = "kwsEscEntityManagerFactory",
    transactionManagerRef= "kwsEscTransactionManager"
)
public abstract class EscDatasourceConfig 
{
	@Bean
    @ConfigurationProperties("kws.esc.datasource")
    public DataSourceProperties kwsEscDataSourceProperties() 
	{
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("kws.esc.datasource.configuration")
    public DataSource kwsEscDataSource() 
    {
    	HikariDataSource dataSource = kwsEscDataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
        dataSource.setPoolName("HikariPool.kws-" + KwsEscConst.MODULE_NAME);
    	return dataSource;
    }

    @Bean(name = "kwsEscEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean kwsEscEntityManagerFactory(EntityManagerFactoryBuilder builder) 
    {
        return builder.
        		dataSource(kwsEscDataSource()).
        		persistenceUnit("Persistence.kws-" + KwsEscConst.MODULE_NAME).
        		packages(KwsEscConst.ESC_BASE_PACKAGE_NAME).
        		build();
    }

    @Bean
    public PlatformTransactionManager kwsEscTransactionManager(final @Qualifier("kwsEscEntityManagerFactory") LocalContainerEntityManagerFactoryBean kwsEscEntityManagerFactory) 
    {
        return new JpaTransactionManager(kwsEscEntityManagerFactory.getObject());
    }
}