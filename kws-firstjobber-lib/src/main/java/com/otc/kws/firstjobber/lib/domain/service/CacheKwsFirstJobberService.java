package com.otc.kws.firstjobber.lib.domain.service;

import java.io.Serializable;

import com.otc.kws.core.domain.service.CacheKwsService;
import com.otc.kws.firstjobber.lib.domain.entity.BaseKwsFirstJobberEntity;

public abstract class CacheKwsFirstJobberService <E extends BaseKwsFirstJobberEntity, ID extends Serializable> extends CacheKwsService<E, ID>
{

}