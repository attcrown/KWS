package com.otc.kws.marketpet.lib.domain.repository.jpa;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import com.otc.kws.marketpet.lib.domain.entity.BaseKwsMarketPetEntity;

public class DefaultKwsMarketPetJpaRepository <E extends BaseKwsMarketPetEntity, ID extends Serializable> extends SimpleJpaRepository<E, ID> implements KwsMarketPetJpaRepository<E, ID>
{
	public DefaultKwsMarketPetJpaRepository(Class<E> domainClass, EntityManager em) 
	{
		super(domainClass, em);
	}

	public DefaultKwsMarketPetJpaRepository(JpaEntityInformation<E, ?> entityInformation, EntityManager entityManager) 
	{
		super(entityInformation, entityManager);
	}
}