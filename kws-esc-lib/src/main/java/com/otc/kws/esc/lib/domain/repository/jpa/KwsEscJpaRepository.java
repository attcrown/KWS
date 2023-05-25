package com.otc.kws.esc.lib.domain.repository.jpa;

import java.io.Serializable;

import org.springframework.data.repository.NoRepositoryBean;

import com.otc.kws.core.domain.repository.jpa.KwsJpaRepository;
import com.otc.kws.esc.lib.domain.entity.BaseKwsEscEntity;

@NoRepositoryBean
public interface KwsEscJpaRepository <E extends BaseKwsEscEntity, ID extends Serializable> extends KwsJpaRepository<E, ID>
{

}