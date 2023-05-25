package com.otc.kws.firstjobber.lib.domain.service;

import java.io.Serializable;

import com.otc.kws.core.domain.service.RepositoryKwsService;
import com.otc.kws.firstjobber.lib.domain.entity.BaseKwsFirstJobberEntity;
import com.otc.kws.firstjobber.lib.domain.repository.jpa.KwsFirstJobberJpaRepository;

public abstract class RepositoryKwsFirstJobberService <R extends KwsFirstJobberJpaRepository<E, ID>, E extends BaseKwsFirstJobberEntity, ID extends Serializable> extends RepositoryKwsService<R, E, ID>
{

}