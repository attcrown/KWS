package com.otc.kws.marketpet.lib.domain.service;

import java.io.Serializable;

import com.otc.kws.core.domain.service.RepositoryKwsService;
import com.otc.kws.marketpet.lib.domain.entity.BaseKwsMarketPetEntity;
import com.otc.kws.marketpet.lib.domain.repository.jpa.KwsMarketPetJpaRepository;

public abstract class RepositoryKwsMarketPetService <R extends KwsMarketPetJpaRepository<E, ID>, E extends BaseKwsMarketPetEntity, ID extends Serializable> extends RepositoryKwsService<R, E, ID>
{

}