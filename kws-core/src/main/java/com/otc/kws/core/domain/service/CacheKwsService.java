package com.otc.kws.core.domain.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import com.otc.kws.core.domain.entity.BaseKwsEntity;

public abstract class CacheKwsService <E extends BaseKwsEntity, ID extends Serializable> extends BaseKwsService
{
	protected List<E> datas;

	
	public abstract List<E> loadDatas();
	protected abstract ID extractId(E entity);
	
	
	@PostConstruct
	public void init()
	{
		logger.info("### " + getClass().getSimpleName() + ".init ###");
		datas = new ArrayList<>();
		CompletableFuture.runAsync(() -> {
			loadCache();
		});
	}
	
	@PreDestroy
	public void destroy()
	{
		logger.info("### " + getClass().getSimpleName() + ".destroy ###");
		clearCache();
	}
	
	public void loadCache()
	{
		datas = loadDatas();
	}
	
	public void clearCache()
	{
		datas.clear();
	}
	
	public void reloadCache()
	{
		clearCache();
		loadCache();
	}
	
	public List<E> getAll()
	{
		if(datas != null && !datas.isEmpty()) {
			return datas.stream().collect(Collectors.toList());
		}
		return new ArrayList<>();
	}
	
	public E getById(ID id)
	{
		return getAll().
				stream().
				filter(entity -> extractId(entity).equals(id)).
				findFirst().
				orElse(null);
	}
}