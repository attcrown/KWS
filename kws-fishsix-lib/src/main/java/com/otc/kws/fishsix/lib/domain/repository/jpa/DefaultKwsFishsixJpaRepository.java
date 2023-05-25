package com.otc.kws.fishsix.lib.domain.repository.jpa;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import com.otc.kws.fishsix.lib.domain.entity.BaseKwsFishsixEntity;

public class DefaultKwsFishsixJpaRepository <E extends BaseKwsFishsixEntity, ID extends Serializable> extends SimpleJpaRepository<E, ID> implements KwsFishsixJpaRepository<E, ID>
{
	public DefaultKwsFishsixJpaRepository(Class<E> domainClass, EntityManager em) 
	{
		super(domainClass, em);
	}

	public DefaultKwsFishsixJpaRepository(JpaEntityInformation<E, ?> entityInformation, EntityManager entityManager) 
	{
		super(entityInformation, entityManager);
	}
}