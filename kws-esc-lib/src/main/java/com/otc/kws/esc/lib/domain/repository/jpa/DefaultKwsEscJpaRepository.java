package com.otc.kws.esc.lib.domain.repository.jpa;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import com.otc.kws.esc.lib.domain.entity.BaseKwsEscEntity;

public class DefaultKwsEscJpaRepository <E extends BaseKwsEscEntity, ID extends Serializable> extends SimpleJpaRepository<E, ID> implements KwsEscJpaRepository<E, ID>
{
	public DefaultKwsEscJpaRepository(Class<E> domainClass, EntityManager em) 
	{
		super(domainClass, em);
	}

	public DefaultKwsEscJpaRepository(JpaEntityInformation<E, ?> entityInformation, EntityManager entityManager) 
	{
		super(entityInformation, entityManager);
	}
}