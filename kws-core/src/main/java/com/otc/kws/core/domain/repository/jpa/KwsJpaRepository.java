package com.otc.kws.core.domain.repository.jpa;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.otc.kws.core.domain.entity.BaseKwsEntity;

@NoRepositoryBean
public interface KwsJpaRepository <E extends BaseKwsEntity, ID extends Serializable> extends JpaRepository<E, ID>
{

}