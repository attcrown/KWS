package com.otc.kws.console.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.transaction.ChainedTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//@Configuration
//@EnableTransactionManagement
public class KwsConsoleTransactionManagerConfig 
{
	@Autowired
	@Qualifier("kwsCoreTransactionManager")
	protected PlatformTransactionManager coreTxnManager;
	
	@Autowired
	@Qualifier("kwsFirstJobberTransactionManager")
	protected PlatformTransactionManager fjbTxnManager;
	
	@Autowired
	@Qualifier("kwsMarketPetTransactionManager")
	protected PlatformTransactionManager mkpTxnManager;
	
	
	@Primary
	@Bean(name = "transactionManager")
    public ChainedTransactionManager transactionManager() throws Exception
    {
        return new ChainedTransactionManager(coreTxnManager, fjbTxnManager, mkpTxnManager);
    }
}