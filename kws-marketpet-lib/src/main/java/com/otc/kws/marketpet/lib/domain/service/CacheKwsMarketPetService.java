package com.otc.kws.marketpet.lib.domain.service;

import java.io.Serializable;

import com.otc.kws.core.domain.service.CacheKwsService;
import com.otc.kws.marketpet.lib.domain.entity.BaseKwsMarketPetEntity;

public abstract class CacheKwsMarketPetService <E extends BaseKwsMarketPetEntity, ID extends Serializable> extends CacheKwsService<E, ID>
{

}