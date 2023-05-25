package com.otc.kws.fishsix.lib.domain.repository.jpa;

import java.io.Serializable;

import org.springframework.data.repository.NoRepositoryBean;

import com.otc.kws.core.domain.repository.jpa.KwsJpaRepository;
import com.otc.kws.fishsix.lib.domain.entity.BaseKwsFishsixEntity;

@NoRepositoryBean
public interface KwsFishsixJpaRepository <E extends BaseKwsFishsixEntity, ID extends Serializable> extends KwsJpaRepository<E, ID>
{

}