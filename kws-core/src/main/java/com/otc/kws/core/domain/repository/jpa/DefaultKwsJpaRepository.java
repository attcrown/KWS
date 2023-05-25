package com.otc.kws.core.domain.repository.jpa;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import com.otc.kws.core.domain.entity.BaseKwsEntity;

public class DefaultKwsJpaRepository <E extends BaseKwsEntity, ID extends Serializable> extends SimpleJpaRepository<E, ID> implements KwsJpaRepository<E, ID>
{
	public DefaultKwsJpaRepository(Class<E> domainClass, EntityManager em) 
	{
		super(domainClass, em);
	}

	public DefaultKwsJpaRepository(JpaEntityInformation<E, ?> entityInformation, EntityManager entityManager) 
	{
		super(entityInformation, entityManager);
	}
}