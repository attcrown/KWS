package com.otc.kws.core.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.otc.kws.core.domain.entity.Api;
import com.otc.kws.core.domain.repository.jpa.JpaApiRepository;

@Service
public class ApiService extends CacheKwsService<Api, String>
{
	@Autowired
	protected JpaApiRepository apiRepository;
	
	
	@Override
	public List<Api> loadDatas() 
	{
		return apiRepository.findAll();
	}
	
	@Override
	protected String extractId(Api entity) 
	{
		return entity.getId();
	}
	
	public Api getByMethodAndPath(String method, String path)
	{
		return getAll().
				stream().
				filter(api -> api.getMethod().equals(method) && api.getPath().equals(path)).
				findFirst().
				orElse(null);
	}
}