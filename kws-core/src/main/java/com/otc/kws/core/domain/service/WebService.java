package com.otc.kws.core.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.otc.kws.core.domain.ModuleProvider;
import com.otc.kws.core.domain.entity.Web;
import com.otc.kws.core.domain.repository.jpa.JpaWebRepository;

@Service
public class WebService extends CacheKwsService<Web, String>
{
	@Autowired
	protected JpaWebRepository webRepository;
	
	@Autowired
	protected ModuleProvider moduleProvider;
	
	
	@Override
	public List<Web> loadDatas() 
	{
		return webRepository.findAll();
	}
	
	@Override
	protected String extractId(Web entity) 
	{
		return entity.getId();
	}
	
	public Web getByMethodAndPath(String method, String path)
	{
		return getAll().
				stream().
				filter(web -> web.getMethod().equals(method) && web.getPath().equals(path)).
				findFirst().
				orElse(null);
	}
	
	public Web getByModuleAndMethodAndPath(String module, String method, String path)
	{
		return getAll().
				stream().
				filter(web -> web.getModule().equals(module) && web.getMethod().equals(method) && web.getPath().equals(path)).
				findFirst().
				orElse(null);
	}
	
	public Web getByRuntimeModuleAndMethodAndPath(String method, String path)
	{
		return getAll().
				stream().
				filter(web -> web.getModule().equals(moduleProvider.getRuntimeModuleName()) && web.getMethod().equals(method) && web.getPath().equals(path)).
				findFirst().
				orElse(null);
	}
}