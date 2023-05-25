package com.otc.kws.firstjobber.lib.domain.repository.jpa;

import java.io.Serializable;

import org.springframework.data.repository.NoRepositoryBean;

import com.otc.kws.core.domain.repository.jpa.KwsJpaRepository;
import com.otc.kws.firstjobber.lib.domain.entity.BaseKwsFirstJobberEntity;

@NoRepositoryBean
public interface KwsFirstJobberJpaRepository <E extends BaseKwsFirstJobberEntity, ID extends Serializable> extends KwsJpaRepository<E, ID>
{

}