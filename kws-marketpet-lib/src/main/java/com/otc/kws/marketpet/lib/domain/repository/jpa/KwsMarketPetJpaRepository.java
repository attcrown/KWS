package com.otc.kws.marketpet.lib.domain.repository.jpa;

import java.io.Serializable;

import org.springframework.data.repository.NoRepositoryBean;

import com.otc.kws.core.domain.repository.jpa.KwsJpaRepository;
import com.otc.kws.marketpet.lib.domain.entity.BaseKwsMarketPetEntity;

@NoRepositoryBean
public interface KwsMarketPetJpaRepository <E extends BaseKwsMarketPetEntity, ID extends Serializable> extends KwsJpaRepository<E, ID>
{

}