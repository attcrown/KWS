package com.otc.kws.firstjobber.lib.domain.repository.jpa;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import com.otc.kws.firstjobber.lib.domain.entity.BaseKwsFirstJobberEntity;

public class DefaultKwsFirstJobberJpaRepository <E extends BaseKwsFirstJobberEntity, ID extends Serializable> extends SimpleJpaRepository<E, ID> implements KwsFirstJobberJpaRepository<E, ID>
{
	public DefaultKwsFirstJobberJpaRepository(Class<E> domainClass, EntityManager em) 
	{
		super(domainClass, em);
	}

	public DefaultKwsFirstJobberJpaRepository(JpaEntityInformation<E, ?> entityInformation, EntityManager entityManager) 
	{
		super(entityInformation, entityManager);
	}
}